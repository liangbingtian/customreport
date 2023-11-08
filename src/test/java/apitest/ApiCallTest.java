package apitest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONWriter;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
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
import com.liang.customreport.tools.WebUrlUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author liangbingtian
 * @date 2023/11/01 下午12:25
 */
@Slf4j
public class ApiCallTest {

  private static final String saveDirectory = "/Users/liangbingtian/Downloads/整体流程测试";
  private static final String saveDirectory1 = "/Users/liangbingtian/Downloads/报错文件测试/";

  @Test
  public void callApiTest() {
    try (InputStream inputStream = this.getClass().getClassLoader()
        .getResourceAsStream("api/customreport_queryparam.json")) {
      final JingdongAdsIbgCustomQueryV1ReqBO o = JSON.parseObject(
          Optional.ofNullable(inputStream).orElseThrow(() -> new RuntimeException("读取的为null")),
          JingdongAdsIbgCustomQueryV1ReqBO.class, Feature.OrderedField);
      final ParamInfo info = ParamInfo.builder()
          .accessToken("c72afbe7de184f9da2d249118d2f7d1bzizt")
          .appKey("A1D3C721A3E382FF4915BE266B4294F6")
          .appSecret("8d08db8de0ec468ebe234dcfdc1c3dca")
          .api(JdApiEnum.CUSTOM_REPORT_QUERY.getApi())
          .build();
      JdApiV2Service<JingdongAdsIbgCustomQueryV1ReqBO, JingdongAdsIbgCustomQueryV1ResBO> service = new JdApiV2Service<>();
      final JingdongAdsIbgCustomQueryV1ResBO result = service
          .doRequest(o, JingdongAdsIbgCustomQueryV1ResBO.class, info);
      System.out.println();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void callApiTest1() {
    try {
      JingdongAdsIbgDownloadReqBO reqBO = new JingdongAdsIbgDownloadReqBO();
      reqBO.setDownloadId(47227393);
      final ParamInfo info = ParamInfo.builder()
          .accessToken("f7078b4f58ec41829239a809eedbf207hing")
          .appKey("A1D3C721A3E382FF4915BE266B4294F6")
          .appSecret("8d08db8de0ec468ebe234dcfdc1c3dca")
          .api(JdApiEnum.REPORT_DOWNLOAD_QUERY.getApi())
          .build();
      JdApiV2Service<JingdongAdsIbgDownloadReqBO, JingdongAdsIbgDownloadResBO> service = new JdApiV2Service<>();
      JingdongAdsIbgDownloadResBO result = service
          .doRequest(reqBO, JingdongAdsIbgDownloadResBO.class, info);
      Integer status = result.getResponse().getReturnType().getData().getStatus();
      while (status == 1) {
        log.info("downloadId:{},未获取到文件链接,继续获取...",
            result.getResponse().getReturnType().getData().getId());
        //status 为1 的话继续调用
        Thread.sleep(5000);
        result = service.doRequest(reqBO, JingdongAdsIbgDownloadResBO.class, info);
        status = result.getResponse().getReturnType().getData().getStatus();
      }
      Preconditions.checkArgument(status == 2, "调用异步下载api返回出错，返回报文为:" + JSON.toJSONString(result));
      final String downloadUrl = result.getResponse().getReturnType().getData().getDownloadUrl();
      Preconditions.checkArgument(StringUtils.isNotBlank(downloadUrl),
          "status正常但是downloadUrl为空，返回报文为:" + JSON.toJSONString(result));
      //获取成功，创建文件目录，往目录里下载
      log.info("downloadUrl为:{}", downloadUrl);
      Path subDirectory = Paths.get(saveDirectory, "FZ");
      Path subDirectory1 = subDirectory.resolve(Paths.get("2023-10-01->2023-10-31"));

      Path targetDirectory = subDirectory1.resolve(reqBO.getDownloadId().toString());
      if (Files.notExists(targetDirectory)) {
        Files.createDirectories(targetDirectory);
        log.info("目录创建成功，目录路径为:{}", targetDirectory);
      }
      //获取zip流直接解压
      final ReportInfoBO infoBO = ReportInfoBO.builder()
          .username("123")
          .startDate("2023-10-01")
          .endDate("2023-10-31")
          .clickOrOrderCaliber(0)
          .clickOrOrderDay(15)
          .orderStatusCategory(null)
          .giftFlag(1).build();
      processDownloadCsv(reqBO.getDownloadId(), downloadUrl, targetDirectory, infoBO);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      e.printStackTrace();
    }
  }

  @Test
  public void testFileError() {
    //获取zip流,并进行文件解析
    try (JSONWriter writer = new JSONWriter(
        new BufferedWriter(new FileWriter(saveDirectory1 + "1.json")));
        FileInputStream fileInputStream = new FileInputStream(
            saveDirectory1 + "自定义报表API_2023年11月07日22时48分33秒下载.zip");
        ZipInputStream zipInputStream = new ZipInputStream(fileInputStream)) {

      final long startTime = System.currentTimeMillis();
      writer.config(SerializerFeature.WriteMapNullValue, true);
      writer.startArray();
      int csvNum = 0;
      ZipEntry zipEntry;
      while ((zipEntry = zipInputStream.getNextEntry()) != null) {
        csvNum++;
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
          JSONObject eachObject = null;
          for (CSVRecord csvRecord : csvParser) {
            if (csvParser.getCurrentLineNumber() == 1) {
              continue;
            }
            eachObject = new JSONObject();
            for (Map.Entry<String, String> entry : Constants.CSV_HEADER_MAP1.entrySet()) {
              final String chineseName = entry.getKey();
              final String targetEnglishName = entry.getValue();
              final String value = csvRecord.get(chineseName);
              eachObject.put(targetEnglishName, value);
            }
            //设置基本信息
            writer.writeObject(eachObject);
            eachObject = null;
            i++;
          }
          log.info("文件：{}, 一共输出了: {}条", zipEntry.getName(), i);
          zipInputStream.closeEntry();
        } catch (Exception e) {
          log.error(e.getMessage(), e);
        }
      }
      writer.endArray();
      writer.flush();
      final long endTime = System.currentTimeMillis();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
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
      JsonFactory factory = new JsonFactory();
      try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileByteArray);
          InputStreamReader inputStreamReader = new InputStreamReader(byteArrayInputStream);
          Reader reader = new BufferedReader(inputStreamReader);
          FileOutputStream outputStream = new FileOutputStream(oldFileName.toString());
          JsonGenerator generator = factory.createGenerator(outputStream);
          CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
              .withHeader(Constants.CSV_HEADER1)
              .withIgnoreHeaderCase()
              .withTrim())
      ) {
        generator.writeStartArray();
        int i = 0;
        for (CSVRecord csvRecord : csvParser) {
          if (csvParser.getCurrentLineNumber() == 1) {
            continue;
          }
          generator.writeStartObject();
          for (Map.Entry<String, String> entry : Constants.CSV_HEADER_STR_MAP1.entrySet()) {
            final String chineseName = entry.getKey();
            final String targetEnglishName = entry.getValue();
            final String value = csvRecord.get(chineseName);
            generator.writeStringField(targetEnglishName, value);
          }
          for (Map.Entry<String, String> entry : Constants.CSV_HEADER_NUMBER_MAP1.entrySet()) {
            final String chineseName = entry.getKey();
            final String targetEnglishName = entry.getValue();
            final String value = csvRecord.get(chineseName);
            generator.writeNumberField(targetEnglishName, Long.parseLong(value));
          }
          //设置基本信息
          processBaseInfo(generator, infoBO);
          generator.writeEndObject();
          i++;
        }
        generator.writeEndArray();
        generator.flush();
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
    JsonFactory factory = new JsonFactory();
    final Path oldFileName = directoryPath.resolve(Paths.get(downloadId + ".json"));
    try (
        ZipInputStream zipInputStream = WebUrlUtils.getZipInputStreamFromUrl(downloadUrl);
        FileOutputStream outputStream = new FileOutputStream(oldFileName.toString());
        JsonGenerator generator = factory.createGenerator(outputStream);
    ) {
      ZipEntry zipEntry;
      final long startTime = System.currentTimeMillis();
      generator.writeStartArray();
      while ((zipEntry = zipInputStream.getNextEntry()) != null) {
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
            generator.writeStartObject();
            for (Map.Entry<String, String> entry : Constants.CSV_HEADER_STR_MAP1.entrySet()) {
              final String chineseName = entry.getKey();
              final String targetEnglishName = entry.getValue();
              final String value = csvRecord.get(chineseName);
              generator.writeStringField(targetEnglishName, value);
            }
            for (Map.Entry<String, String> entry : Constants.CSV_HEADER_NUMBER_MAP1.entrySet()) {
              final String chineseName = entry.getKey();
              final String targetEnglishName = entry.getValue();
              final String value = csvRecord.get(chineseName);
              generator.writeNumberField(targetEnglishName, Long.parseLong(value));
            }
            //设置基本信息
            processBaseInfo(generator, infoBO);
            generator.writeEndObject();
            i++;
          }
          log.info("downloadId:{}, 文件：{}, 一共输出了: {}条", downloadId, zipEntry.getName(), i);
          zipInputStream.closeEntry();
        } catch (Exception e) {
          log.error(e.getMessage(), e);
        }
      }
      generator.writeEndArray();
      generator.flush();
      generator.close();
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

  public void processBaseInfo(JsonGenerator object, ReportInfoBO reportInfoBO) throws IOException {
    object.writeStringField(Constants.USER_NAME, reportInfoBO.getUsername());
    object.writeStringField(Constants.START_DATE, reportInfoBO.getStartDate());
    object.writeStringField(Constants.END_DATE, reportInfoBO.getEndDate());
    object.writeNumberField(Constants.TRANS_DAYS, reportInfoBO.getClickOrOrderDay());
    object.writeNumberField(Constants.CALIBER, reportInfoBO.getClickOrOrderCaliber());
    object.writeNumberField(Constants.GIFT_FLAG, reportInfoBO.getGiftFlag());
    if (reportInfoBO.getOrderStatusCategory()==null) {
      object.writeNullField(Constants.ORDER_STATUS);
    }else {
      object.writeNumberField(Constants.ORDER_STATUS, reportInfoBO.getOrderStatusCategory());
    }
    object.writeNumberField(Constants.EFFECT, 1);
    object.writeNumberField(Constants.IS_DAILY, 1);
  }

  @Test
  public void readTest() throws MalformedURLException, UnsupportedEncodingException {
    String fileURL = "https://storage.jd.com/report-engine-cloud-clouds/20231101/1698831518241000-534996806300647425/%E8%87%AA%E5%AE%9A%E4%B9%89%E6%8A%A5%E8%A1%A8API_2023%E5%B9%B411%E6%9C%8801%E6%97%A517%E6%97%B638%E5%88%8638%E7%A7%92%E4%B8%8B%E8%BD%BD.csv?Expires=1714729118&AccessKey=JdqUoH2NY2px0LAI&Signature=zpErMGMIz8OR%2BLPgnPTGkNh0J9c%3D";
    final URL url = new URL(fileURL);
    final String path = url.getPath();
    final String decode = URLDecoder.decode(path, "UTF-8");
    System.out.println(decode);
  }

  @Test
  public void getFileNameFromUrl() {
    String fileURL = "https://storage.jd.com/report-engine-cloud-clouds/20231101/1698831518241000-534996806300647425/%E8%87%AA%E5%AE%9A%E4%B9%89%E6%8A%A5%E8%A1%A8API_2023%E5%B9%B411%E6%9C%8801%E6%97%A517%E6%97%B638%E5%88%8638%E7%A7%92%E4%B8%8B%E8%BD%BD.csv?Expires=1714729118&AccessKey=JdqUoH2NY2px0LAI&Signature=zpErMGMIz8OR%2BLPgnPTGkNh0J9c%3D"; // 替换为你要下载的文件的 URL
    // 替换为你要保存文件的目录

    try {
      // 创建 URL 对象
      URL url = new URL(fileURL);

      // 打开连接
      URLConnection connection = url.openConnection();

      // 获取文件名（从 Content-Disposition 头字段中获取）
      String fileName = getFileNameFromHeader(url);

      // 创建文件输出流
      FileOutputStream outputStream = new FileOutputStream(saveDirectory + fileName);

      // 获取输入流
      InputStream inputStream = connection.getInputStream();

      // 读取和写入文件
      byte[] buffer = new byte[1024];
      int length;
      while ((length = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, length);
      }

      // 关闭流
      inputStream.close();
      outputStream.close();

      System.out.println("文件下载完成！文件名: " + fileName);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static String getFileNameFromHeader(URL url) throws UnsupportedEncodingException {
    String fileName = null;
    final String decode = URLDecoder.decode(url.getPath(), "UTF-8");
    int lastSlashIndex = decode.lastIndexOf('/');
    if (lastSlashIndex != -1) {
      // 提取最后一个斜杠后面的所有字符
      fileName = decode.substring(lastSlashIndex + 1);
    }
    if (fileName == null || fileName.isEmpty()) {
      fileName = "downloadedFile"; // 如果无法从头字段中获取文件名，使用默认文件名
    }
    return fileName;
  }

  @Test
  public void ReadJsonTest() throws IOException {
    final List<JdShopAuthorizeInfoPO> infos = JSON
        .parseArray(Constants.INFO_LIST, JdShopAuthorizeInfoPO.class);
    System.out.println();

  }

}
