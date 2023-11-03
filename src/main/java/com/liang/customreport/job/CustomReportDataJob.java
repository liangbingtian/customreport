package com.liang.customreport.job;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.liang.customreport.Main;
import com.liang.customreport.enums.JdApiEnum;
import com.liang.customreport.files.ExtractFileUtils;
import com.liang.customreport.jdapicall.JdApiV2Service;
import com.liang.customreport.jdapicall.bo.ParamInfo;
import com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgCustomQueryV1ReqBO;
import com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgCustomQueryV1ResBO;
import com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgDownloadReqBO;
import com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgDownloadResBO;
import com.liang.customreport.job.random.JingdongAdsIbgCustomQueryV1ReqFieldCombine;
import com.liang.customreport.mapstructs.JingdongAdsIbgCustomQueryV1ReqMappering;
import com.liang.customreport.tools.CsvUtils;
import com.liang.customreport.tools.WebUrlUtils;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liangbingtian
 * @date 2023/11/02 下午4:34
 */
@Slf4j
public class CustomReportDataJob {

  private static final String saveDirectory = "/Users/liangbingtian/Downloads/整体流程测试";

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

      JdApiV2Service<JingdongAdsIbgCustomQueryV1ReqBO, JingdongAdsIbgCustomQueryV1ResBO> service = new JdApiV2Service<>();

      JdApiV2Service<JingdongAdsIbgDownloadReqBO, JingdongAdsIbgDownloadResBO> downloadService = new JdApiV2Service<>();

      for (String item : srcAndTarget) {
        final String[] subDates = item.split("->");
        log.info("调用{}接口，日期区间为:{}", JdApiEnum.CUSTOM_REPORT_QUERY.getApi(), subDates);
        String startDayStr = subDates[0];
        String endDayStr = subDates[1];
        JingdongAdsIbgCustomQueryV1ReqBO reqBo1 = JingdongAdsIbgCustomQueryV1ReqMappering.INSTANCE
            .copyOne(reqBO);
        reqBo1.setStartDay(startDayStr).setEndDay(endDayStr);
        JingdongAdsIbgCustomQueryV1ReqFieldCombine randomCombine = new JingdongAdsIbgCustomQueryV1ReqFieldCombine(
            reqBo1);
        randomCombine.randomFieldCombine();
        final List<JingdongAdsIbgCustomQueryV1ReqBO> combineResult = randomCombine.getResult();

        CompletableFuture
            .allOf(combineResult.stream().map(req -> CompletableFuture.supplyAsync(() -> {
              log.info("开始时间:{}，结束时间:{}，口径:{}，订单状态:{}，转换周期:{}，调用传参:{}", startDayStr, endDayStr,
                  req.getClickOrOrderCaliber(), req.getOrderStatusCategory(),
                  req.getClickOrOrderDay(), JSON.toJSONString(req));
              JingdongAdsIbgCustomQueryV1ResBO result = service
                  .doRequest(req, JingdongAdsIbgCustomQueryV1ResBO.class, info);
              log.info("开始时间:{}，结束时间:{}，口径:{}，订单状态:{}，转换周期:{},返回结果:{}", startDayStr, endDayStr,
                  req.getClickOrOrderCaliber(), req.getOrderStatusCategory(),
                  req.getClickOrOrderDay(), JSON.toJSONString(result));
              return result.getJingdongAdsIbgUniversalJosServiceCustomQueryV1Responce()
                  .getReturnType().getData().getDownloadId();
            }, threadPoolExecutor).exceptionally(e -> {
              log.error(e.getMessage());
              return null;
            }).thenAccept(downloadId -> {
              if (downloadId == null) {
                log.error("download为空");
                return;
              }
              try {
                JingdongAdsIbgDownloadReqBO downloadBO = new JingdongAdsIbgDownloadReqBO();
                downloadBO.setDownloadId(downloadId);
                info.setApi(JdApiEnum.REPORT_DOWNLOAD_QUERY.getApi());

                JingdongAdsIbgDownloadResBO result = downloadService
                    .doRequest(downloadBO, JingdongAdsIbgDownloadResBO.class, info);
                Integer status = result.getResponse().getReturnType().getData().getStatus();
                while (status == 1) {
                  log.info("downloadId:{},未获取到文件链接,继续获取...",
                      result.getResponse().getReturnType().getData().getId());
                  //status 为1 的话继续调用
                  Thread.sleep(5000);
                  result = downloadService
                      .doRequest(downloadBO, JingdongAdsIbgDownloadResBO.class, info);
                  status = result.getResponse().getReturnType().getData().getStatus();
                }
                Preconditions
                    .checkArgument(status == 2, "调用异步下载api返回出错，返回报文为:" + JSON.toJSONString(result));
                final String downloadUrl = result.getResponse().getReturnType().getData()
                    .getDownloadUrl();
                Preconditions.checkArgument(StringUtils.isNotBlank(downloadUrl),
                    "status正常但是downloadUrl为空，返回报文为:" + JSON.toJSONString(result));
                //获取成功，创建文件目录，往目录里下载
                Path currentPath = Paths.get(saveDirectory);
                // 拼接要创建的目录路径
                Path subDirectory = Paths.get("FZ");
                Path subDirectory1 = subDirectory
                    .resolve(Paths.get(startDayStr + "->" + endDayStr));
                Path directoryPath = currentPath.resolve(subDirectory1)
                    .resolve(String.valueOf(downloadBO.getDownloadId()));
                if (Files.notExists(directoryPath)) {
                  Files.createDirectories(directoryPath);
                  log.info("目录创建成功，目录路径为:{}", directoryPath);
                }
                log.info("downloadId:{},获取文件链接成功,开始下载...", downloadBO.getDownloadId());
                WebUrlUtils.getFileFromUrl(downloadUrl, directoryPath);
                log.info("downloadId:{},下载完成,开始解析...", downloadBO.getDownloadId());
                ExtractFileUtils.extractZipFiles(directoryPath.toString(), null);
                CsvUtils.processCsvToJson(directoryPath.toString(), null);
              } catch (Exception e) {
                log.error(e.getMessage(), e);
                e.printStackTrace();
              }
            })).toArray(CompletableFuture[]::new)).join();
//          JingdongAdsIbgCustomQueryV1ResBO result = service
//                .doRequest(req, JingdongAdsIbgCustomQueryV1ResBO.class, info);
//          result.getJingdongAdsIbgUniversalJosServiceCustomQueryV1Responce().getReturnType().getData().getDownloadId();
        log.info("所有任务已经提交到线程池");
      }
      threadPoolExecutor.shutdown();
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
    Preconditions.checkArgument(between < 31, format);

    //如果大于7天，则拆成7天为间隔的开始时间和结束时间的间隔组合去进行并发调用
    List<String> dateRanges = new ArrayList<>();
    while (startDay.isBefore(endDay)) {
      LocalDate nextDate = startDay.plusDays(18);
      if (nextDate.isAfter(endDay)) {
        nextDate = endDay;
      }

      String dateRange = startDay.format(formatter) + "->" + nextDate.format(formatter);
      dateRanges.add(dateRange);
      startDay = nextDate.plusDays(1);
    }
    return dateRanges;
  }


}
