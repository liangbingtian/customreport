package com.liang.customreport.job;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.liang.customreport.Main;
import com.liang.customreport.enums.JdApiEnum;
import com.liang.customreport.jdapicall.JdApiV2Service;
import com.liang.customreport.jdapicall.bo.ParamInfo;
import com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgCustomQueryV1ReqBO;
import com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgCustomQueryV1ResBO;
import com.liang.customreport.mapstructs.JingdongAdsIbgCustomQueryV1ReqMappering;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liangbingtian
 * @date 2023/11/02 下午4:34
 */
@Slf4j
public class CustomReportDataJob {

  public void runJob() {

    //创建有四个线程的线程池
    final ThreadPoolExecutor threadPoolExecutor = ThreadUtil.newExecutor(4, 4);

    //1. 自定义报表查询调用
    final ParamInfo info = ParamInfo.builder()
        .accessToken("1fb2353257524bf88d3a067195bdba82mzi1")
        .appKey("A1D3C721A3E382FF4915BE266B4294F6")
        .appSecret("8d08db8de0ec468ebe234dcfdc1c3dca")
        .api(JdApiEnum.CUSTOM_REPORT_QUERY.getApi())
        .build();

    try (InputStream inputStream = CustomReportDataJob.class.getClassLoader()
        .getResourceAsStream("queryparam/customreport_queryparam.json")) {
      final JingdongAdsIbgCustomQueryV1ReqBO reqBO = JSON.parseObject(
          Optional.ofNullable(inputStream)
              .orElseThrow(() -> new RuntimeException("读取请求参数文件，读取到的文件为null")
              ),
          JingdongAdsIbgCustomQueryV1ReqBO.class);

      final List<String> srcAndTarget = processDate(reqBO);
      for (String item : srcAndTarget) {
        final String[] subDates = item.split("-");
        log.info("调用{}接口，日期区间为:{}", JdApiEnum.CUSTOM_REPORT_QUERY.getApi(), subDates);
        String startDayStr = subDates[0];
        String endDayStr = subDates[1];
        final JingdongAdsIbgCustomQueryV1ReqBO reqBO1 = JingdongAdsIbgCustomQueryV1ReqMappering.INSTANCE
            .copyOne(reqBO);
        reqBO1.setStartDay(startDayStr).setEndDay(endDayStr);
//        CompletableFuture.supplyAsync()
      }



      JdApiV2Service<JingdongAdsIbgCustomQueryV1ReqBO, JingdongAdsIbgCustomQueryV1ResBO> service = new JdApiV2Service<>();
      final JingdongAdsIbgCustomQueryV1ResBO result = service
          .doRequest(reqBO, JingdongAdsIbgCustomQueryV1ResBO.class, info);
      System.out.println();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }


  private List<String> processDate(JingdongAdsIbgCustomQueryV1ReqBO o) {
    String startDayStr = o.getStartDay();
    String endDayStr = o.getEndDay();
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate startDay = LocalDate.parse(startDayStr, formatter);
    final LocalDate endDay = LocalDate.parse(endDayStr, formatter);

    //如果大于31天则抛出异常
    final long between = ChronoUnit.DAYS.between(startDay, endDay);
    final String format = String.format("开始时间:%s,结束时间:%s,的时间间隔大于31天", startDay, endDay);
    Preconditions.checkArgument(between > 31, format);

    //如果大于7天，则拆成7天为间隔的开始时间和结束时间的间隔组合去进行并发调用
    List<String> dateRanges = new ArrayList<>();
    while (startDay.isBefore(endDay)) {
      LocalDate nextDate = startDay.plusDays(7);
      if (nextDate.isAfter(endDay)) {
        nextDate = endDay;
      }

      String dateRange = startDay.format(formatter) + " - " + nextDate.format(formatter);
      dateRanges.add(dateRange);
      startDay = nextDate.plusDays(1);
    }
    return dateRanges;
  }


}
