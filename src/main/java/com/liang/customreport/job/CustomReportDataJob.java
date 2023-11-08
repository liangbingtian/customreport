package com.liang.customreport.job;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Preconditions;
import com.liang.customreport.bo.ReportInfoBO;
import com.liang.customreport.common.Constants;
import com.liang.customreport.enums.JdApiEnum;
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liangbingtian
 * @date 2023/11/02 下午4:34
 */
@Slf4j
public class CustomReportDataJob {

  private static final String saveDirectory = "/Users/liangbingtian/Downloads/整体流程测试";

  public void runMainJob() {
    //创建有四个线程的线程池
    final long startTime = System.currentTimeMillis();
    final ThreadPoolExecutor threadPoolExecutorCallDownloadId = ThreadUtil.newExecutor(8, 8);
    final ThreadPoolExecutor threadPoolExecutorDownloadFile = ThreadUtil.newExecutor(8, 8);
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
      final List<CompletableFuture<Void>> thisUserFutureResult = runJob(
          threadPoolExecutorCallDownloadId
          , threadPoolExecutorDownloadFile
          , info);
      allUsersResult.addAll(thisUserFutureResult);
    }
    CompletableFuture.allOf(allUsersResult.toArray(new CompletableFuture[0])).join();
    final long endTime = System.currentTimeMillis();
    log.info("所有用户的任务已经执行完成，执行时间为:{}ms", (endTime - startTime));
    threadPoolExecutorDownloadFile.shutdown();
    threadPoolExecutorCallDownloadId.shutdown();
  }

  public List<CompletableFuture<Void>> runJob(ThreadPoolExecutor threadPoolExecutorOne,
      ThreadPoolExecutor threadPoolExecutorTwo,
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
                .supplyAsync(() -> {
                  try {
                    JingdongAdsIbgCustomQueryV1ResBO queryResult;
                    Long code;
                    do {
                      Thread.sleep(500);
                      queryResult = service
                          .doRequest(req, JingdongAdsIbgCustomQueryV1ResBO.class,
                              paramInfoList.get(0));
                      code = Optional.ofNullable(queryResult
                          .getJingdongAdsIbgUniversalJosServiceCustomQueryV1Responce()
                          .getReturnType()).
                          orElseThrow(() -> {
                            log.error("接口:{},返回的data为空,请求参数[1]:{},[2]:{}",
                                JdApiEnum.CUSTOM_REPORT_QUERY.getApi(),
                                JSON.toJSONString(paramInfoList.get(0)), JSON.toJSONString(req));
                            return new RuntimeException("获取文件返回值的data为空，详情请见日志");
                          }).getCode();
                    } while (code.equals(401L));
                    Integer downloadId = queryResult
                        .getJingdongAdsIbgUniversalJosServiceCustomQueryV1Responce()
                        .getReturnType().getData().getDownloadId();
                    Preconditions.checkArgument(downloadId != null,
                        "返回的downloadId为空,传递的参数为:" + JSON.toJSONString(req));
                    return downloadId;
                  } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    return null;
                  }
                }, threadPoolExecutorOne).thenAcceptAsync(downloadId -> {
                  if (downloadId == null) {
                    return;
                  }
                  try {
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
                    log.info("downloadId:{},获取到文件链接,链接为:{}", downloadId,
                        downloadResult.getResponse().getReturnType().getData().getDownloadUrl());
                    Preconditions.checkArgument(status == 2,
                        "调用异步下载api返回出错，返回报文为:" + JSON.toJSONString(downloadResult));
                    final String downloadUrl = downloadResult.getResponse().getReturnType()
                        .getData()
                        .getDownloadUrl();
                    Preconditions.checkArgument(StringUtils.isNotBlank(downloadUrl),
                        "status正常但是downloadUrl为空，返回报文为:" + JSON.toJSONString(downloadResult));
                    //获取成功，创建文件目录，往目录里下载
                    Path currentPath = Paths
                        .get(saveDirectory, info.getUsername(), startDayStr + "->" + endDayStr, downloadId.toString());
                    if (Files.notExists(currentPath)) {
                      Files.createDirectories(currentPath);
                      log.info("目录创建成功，目录路径为:{}", currentPath);
                    }
                    final Path targetFile = currentPath
                        .resolve(downloadBO.getDownloadId() + ".json");
                    final ReportInfoBO infoBO = ReportInfoBO.builder()
                        .username(paramInfoList.get(1).getUsername())
                        .startDate(req.getStartDay())
                        .endDate(req.getEndDay())
                        .clickOrOrderCaliber(req.getClickOrOrderCaliber())
                        .clickOrOrderDay(req.getClickOrOrderDay())
                        .orderStatusCategory(req.getOrderStatusCategory())
                        .giftFlag(req.getGiftFlag()).build();
                    final String name = WebUrlUtils
                        .getFileNameFromHeader(new URL(downloadUrl));
                    if (name.endsWith(".csv")) {
                      processDownloadCsv(downloadId, downloadUrl, currentPath, infoBO);
                    } else if (name.endsWith(".zip")) {
                      processDownloadZipFile(downloadId, downloadUrl, currentPath, infoBO);
                    } else {
                      log.error("{},得到的文件路径既不是zip，也不是csv，文件路径为:{}", downloadId, downloadUrl);
                    }
                  } catch (Exception e) {
                    log.error(e.getMessage(), e);
                  }
                }, threadPoolExecutorTwo)
        ).collect(Collectors.toList());
        thisUserAllFutureResult.addAll(thisTimeFutureResult);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return thisUserAllFutureResult;
  }

  private void processDownloadCsv(Integer downloadId, String downloadUrl,
      Path directoryPath, ReportInfoBO infoBO) {
    try {
      byte[] fileByteArray = WebUrlUtils.getByteArrayFromUrl(downloadUrl);
      if (fileByteArray == null || fileByteArray.length == 0) {
        log.error("downloadId:{}, downloadUrl:{},参数为:{}, 没有从流中遍历到该文件", downloadId,
            downloadUrl, JSON.toJSONString(infoBO));
        return;
      }
      final Path oldFileName = directoryPath.resolve(Paths.get(downloadId + ".json"));
      final long startTime = System.currentTimeMillis();
      try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileByteArray);
          InputStreamReader inputStreamReader = new InputStreamReader(byteArrayInputStream);
          Reader reader = new BufferedReader(inputStreamReader);
          JSONWriter writer = new JSONWriter(
              new BufferedWriter(new FileWriter(oldFileName.toString())));
          CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
              .withHeader(Constants.CSV_HEADER1)
              .withIgnoreHeaderCase()
              .withTrim())
      ) {
        writer.config(SerializerFeature.WriteMapNullValue, true);
        writer.startArray();
        int i = 0;
        for (CSVRecord csvRecord : csvParser) {
          if (csvParser.getCurrentLineNumber() == 1) {
            continue;
          }
          JSONObject eachObject = new JSONObject();
          for (Map.Entry<String, String> entry : Constants.CSV_HEADER_MAP1.entrySet()) {
            final String chineseName = entry.getKey();
            final String targetEnglishName = entry.getValue();
            final String value = csvRecord.get(chineseName);
            eachObject.put(targetEnglishName, value);
          }
          //设置基本信息
          processBaseInfo(eachObject, infoBO);
          writer.writeObject(eachObject);
          i++;
        }
        writer.endArray();
        writer.flush();
        log.info("downloadId:{},  一共输出了: {}条", downloadId, i);
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      }
      final long endTime = System.currentTimeMillis();
      String builder = downloadId + "-" + (endTime - startTime)
          + "ms"
          + "-"
          + infoBO.getClickOrOrderDay()
          + "-"
          + infoBO.getClickOrOrderCaliber()
          + "-"
          + infoBO.getOrderStatusCategory()
          + ".json";
      final File newFileName = oldFileName.getParent().resolve(builder).toFile();
      final File oldFile = oldFileName.toFile();
      final boolean b = oldFile.renameTo(newFileName);
      if (!b) {
        log.error("文件名称从:{},转换为:{},失败", oldFile.getName(), newFileName.getName());
      }

    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  private void processDownloadZipFile(Integer downloadId, String downloadUrl,
      Path directoryPath, ReportInfoBO infoBO) {
    //获取zip流,并进行文件解析
    try {
      ZipInputStream zipInputStream = WebUrlUtils.getZipInputStreamFromUrl(downloadUrl);
      int csvNum = 0;
      ZipEntry zipEntry;
      while ((zipEntry = zipInputStream.getNextEntry()) != null) {
        csvNum++;
        final long startTime = System.currentTimeMillis();
        final Path oldFileName = directoryPath.resolve(Paths.get(csvNum + ".json"));
        JSONWriter writer = new JSONWriter(
            new BufferedWriter(new FileWriter(oldFileName.toString())));
        writer.config(SerializerFeature.WriteMapNullValue, true);
        writer.startArray();
        try (InputStreamReader inputStreamReader =
            new InputStreamReader(
                new ByteArrayInputStream(Objects
                    .requireNonNull(WebUrlUtils.getByteArrayFromZipInputStream(zipInputStream))));
            Reader reader = new BufferedReader(inputStreamReader);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withHeader(Constants.CSV_HEADER1)
                .withIgnoreHeaderCase()
                .withTrim())
        ) {
          int i = 0;
          for (CSVRecord csvRecord : csvParser) {
            if (csvParser.getCurrentLineNumber() == 1) {
              continue;
            }
            JSONObject eachObject = new JSONObject();
            for (Map.Entry<String, String> entry : Constants.CSV_HEADER_MAP1.entrySet()) {
              final String chineseName = entry.getKey();
              final String targetEnglishName = entry.getValue();
              final String value = csvRecord.get(chineseName);
              eachObject.put(targetEnglishName, value);
            }
            //设置基本信息
            processBaseInfo(eachObject, infoBO);
            writer.writeObject(eachObject);
            i++;
          }
          log.info("downloadId:{}, 文件：{}, 一共输出了: {}条", downloadId, zipEntry.getName(), i);
          zipInputStream.closeEntry();
        } catch (Exception e) {
          log.error(e.getMessage(), e);
        }
        writer.endArray();
        writer.flush();
        final long endTime = System.currentTimeMillis();

        String builder = downloadId + "-" + (endTime - startTime)
            + "ms"
            + "-"
            + infoBO.getClickOrOrderDay()
            + "-"
            + infoBO.getClickOrOrderCaliber()
            + "-"
            + infoBO.getOrderStatusCategory()
            + "-第" + csvNum + "个文件"
            + ".json";
        final File newFileName = oldFileName.getParent().resolve(builder).toFile();
        final File oldFile = oldFileName.toFile();
        final boolean b = oldFile.renameTo(newFileName);
        if (!b) {
          log.error("文件名称从:{},转换为:{},失败", oldFile.getName(), newFileName.getName());
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  private void processBaseInfo(JSONObject object, ReportInfoBO reportInfoBO) {
    object.put(Constants.USER_NAME, reportInfoBO.getUsername());
    object.put(Constants.START_DATE, reportInfoBO.getStartDate());
    object.put(Constants.END_DATE, reportInfoBO.getEndDate());
    object.put(Constants.TRANS_DAYS, reportInfoBO.getClickOrOrderDay());
    object.put(Constants.CALIBER, reportInfoBO.getClickOrOrderCaliber());
    object.put(Constants.GIFT_FLAG, reportInfoBO.getGiftFlag());
    object.put(Constants.ORDER_STATUS, reportInfoBO.getOrderStatusCategory());
    object.put(Constants.EFFECT, 1);
    object.put(Constants.IS_DAILY, 1);
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
