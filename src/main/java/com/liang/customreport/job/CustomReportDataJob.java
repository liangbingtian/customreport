package com.liang.customreport.job;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.liang.customreport.bo.ReportInfoBO;
import com.liang.customreport.common.Constants;
import com.liang.customreport.enums.JdApiEnum;
import com.liang.customreport.files.ExtractFileUtils;
import com.liang.customreport.files.customreport.CustomReportCsvVisitor;
import com.liang.customreport.jdapicall.JdApiV2Service;
import com.liang.customreport.jdapicall.bo.ParamInfo;
import com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgCustomQueryV1ReqBO;
import com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgCustomQueryV1ResBO;
import com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgDownloadReqBO;
import com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgDownloadResBO;
import com.liang.customreport.jdapicall.po.JdShopAuthorizeInfoPO;
import com.liang.customreport.job.random.JingdongAdsIbgCustomQueryV1ReqFieldCombine;
import com.liang.customreport.job.random.ParamInfoFieldCombine;
import com.liang.customreport.mapstructs.JingdongAdsIbgCustomQueryV1ReqMappering;
import com.liang.customreport.tools.WebUrlUtils;
import java.io.IOException;
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

  private static final String saveDirectory = "/Users/liangbingtian/Downloads/整体流程测试1";

  public void runMainJob() {
    //创建有四个线程的线程池
    final ThreadPoolExecutor threadPoolExecutor = ThreadUtil.newExecutor(8, 8);
    final List<JdShopAuthorizeInfoPO> infos = JSON
        .parseArray(Constants.INFO1, JdShopAuthorizeInfoPO.class);
    List<CompletableFuture<Void>> allUsersResult = new ArrayList<>();
    for (JdShopAuthorizeInfoPO infoPo : infos) {
      final ParamInfo info = ParamInfo.builder()
          .accessToken(infoPo.getAccessToken())
          .appKey(infoPo.getAppkey())
          .appSecret(infoPo.getAppSecret())
          .api(JdApiEnum.CUSTOM_REPORT_QUERY.getApi())
          .username(infoPo.getUsername())
          .build();
      final List<CompletableFuture<Void>> thisUserFutureResult = runJob(threadPoolExecutor, info);
      allUsersResult.addAll(thisUserFutureResult);
    }
    CompletableFuture.allOf(allUsersResult.toArray(new CompletableFuture[0])).join();
    log.info("所有用户的任务已经执行完成");
    threadPoolExecutor.shutdown();
  }

  public List<CompletableFuture<Void>> runJob(ThreadPoolExecutor threadPoolExecutor,
      ParamInfo info) {
    //1. 自定义报表查询调用

    ParamInfoFieldCombine paramInfoFieldCombine = new ParamInfoFieldCombine(info);
    paramInfoFieldCombine.randomFieldCombine();
    final List<ParamInfo> paramInfoList = paramInfoFieldCombine.getResult();

    JdApiV2Service<JingdongAdsIbgCustomQueryV1ReqBO, JingdongAdsIbgCustomQueryV1ResBO> service = new JdApiV2Service<>();

    JdApiV2Service<JingdongAdsIbgDownloadReqBO, JingdongAdsIbgDownloadResBO> downloadService = new JdApiV2Service<>();

    List<CompletableFuture<Void>> thisUserAllFutureResult = new ArrayList<>();

    try (InputStream inputStream = CustomReportDataJob.class.getClassLoader()
        .getResourceAsStream("queryparam/customreport_queryparam.json")) {
      final JingdongAdsIbgCustomQueryV1ReqBO reqBO = JSON.parseObject(
          Optional.ofNullable(inputStream)
              .orElseThrow(() -> new RuntimeException("读取请求参数文件，读取到的文件为null")
              ),
          JingdongAdsIbgCustomQueryV1ReqBO.class);

      final List<String> srcAndTarget = processDate(reqBO);

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
        List<JingdongAdsIbgCustomQueryV1ReqBO> combineResult = randomCombine.getResult();

        final List<CompletableFuture<Void>> thisTimeFutureResult = combineResult.stream().map(
            req -> CompletableFuture
                .runAsync(() -> {
                  try {
                    JingdongAdsIbgCustomQueryV1ResBO queryResult = service
                        .doRequest(req, JingdongAdsIbgCustomQueryV1ResBO.class,
                            paramInfoList.get(0));
                    Integer downloadId = queryResult
                        .getJingdongAdsIbgUniversalJosServiceCustomQueryV1Responce()
                        .getReturnType().getData().getDownloadId();
                    Preconditions.checkArgument(downloadId != null,
                        "返回的downloadId为空,传递的参数为:" + JSON.toJSONString(req));

                    //开始文件生成下载
                    JingdongAdsIbgDownloadReqBO downloadBO = new JingdongAdsIbgDownloadReqBO();
                    downloadBO.setDownloadId(downloadId);
                    JingdongAdsIbgDownloadResBO downloadResult = downloadService
                        .doRequest(downloadBO, JingdongAdsIbgDownloadResBO.class,
                            paramInfoList.get(1));
                    Integer status = Optional
                        .ofNullable(downloadResult.getResponse().getReturnType().getData()).
                            orElseThrow(() -> {
                              log.error("接口:{},返回的data为空,请求参数[1]:{},[2]:{}",
                                  JdApiEnum.REPORT_DOWNLOAD_QUERY.getApi(),
                                  JSON.toJSONString(paramInfoList.get(1)), downloadId);
                              return new RuntimeException("获取文件返回值的data为空，详情请见日志");
                            })
                        .getStatus();
                    while (status == 1) {
                      log.info("downloadId:{},未获取到文件链接,继续获取...",
                          downloadResult.getResponse().getReturnType().getData().getId());
                      //status 为1 的话继续调用
                      Thread.sleep(5000);
                      downloadResult = downloadService
                          .doRequest(downloadBO, JingdongAdsIbgDownloadResBO.class,
                              paramInfoList.get(1));
                      status = Optional
                          .ofNullable(downloadResult.getResponse().getReturnType().getData()).
                              orElseThrow(() -> {
                                log.error("接口:{},返回的data为空,请求参数[1]:{},[2]:{}",
                                    JdApiEnum.REPORT_DOWNLOAD_QUERY.getApi(),
                                    JSON.toJSONString(paramInfoList.get(1)), downloadId);
                                return new RuntimeException("获取文件返回值的data为空，详情请见日志");
                              })
                          .getStatus();
                    }
                    Preconditions.checkArgument(status == 2,
                        "调用异步下载api返回出错，返回报文为:" + JSON.toJSONString(downloadResult));
                    final String downloadUrl = downloadResult.getResponse().getReturnType()
                        .getData()
                        .getDownloadUrl();
                    Preconditions.checkArgument(StringUtils.isNotBlank(downloadUrl),
                        "status正常但是downloadUrl为空，返回报文为:" + JSON.toJSONString(downloadResult));
                    //获取成功，创建文件目录，往目录里下载
                    Path currentPath = Paths.get(saveDirectory);
                    // 拼接要创建的目录路径
                    Path subDirectory = Paths.get(info.getUsername());
                    Path subDirectory1 = subDirectory
                        .resolve(Paths.get(startDayStr + "->" + endDayStr));
                    Path directoryPath = currentPath.resolve(subDirectory1)
                        .resolve(String.valueOf(downloadBO.getDownloadId()));
                    if (Files.notExists(directoryPath)) {
                      Files.createDirectories(directoryPath);
                      log.info("目录创建成功，目录路径为:{}", directoryPath);
                    }
                    final ReportInfoBO infoBO = ReportInfoBO.builder()
                        .username(paramInfoList.get(1).getUsername())
                        .startDate(req.getStartDay())
                        .endDate(req.getEndDay())
                        .clickOrOrderCaliber(req.getClickOrOrderCaliber())
                        .clickOrOrderDay(req.getClickOrOrderDay())
                        .orderStatusCategory(req.getOrderStatusCategory())
                        .giftFlag(req.getGiftFlag()).build();
                    processDownload(downloadId, downloadUrl, directoryPath, infoBO);
                  } catch (Exception e) {
                    log.error(e.getMessage(), e);
                  }
                }, threadPoolExecutor)).collect(Collectors.toList());
        thisUserAllFutureResult.addAll(thisTimeFutureResult);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return thisUserAllFutureResult;
  }

  private void processDownload(Integer downloadId, String downloadUrl,
      Path directoryPath, ReportInfoBO infoBO) throws IOException {
    log.info("downloadId:{},获取文件链接成功,开始下载...", downloadId);
    WebUrlUtils.getFileFromUrl(downloadUrl, directoryPath);
    log.info("downloadId:{},下载完成,开始解析...", downloadId);
    ExtractFileUtils.extractZipFiles(directoryPath.toString(), null);
    CustomReportCsvVisitor visitor = new CustomReportCsvVisitor(null, infoBO, Constants.CSV_HEADER1,
        Constants.CSV_HEADER_MAP1);
    Files.walkFileTree(directoryPath, visitor);
    log.info("目录:{}里的csv文件转换为json文件完毕", directoryPath);
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
