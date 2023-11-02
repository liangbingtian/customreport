package apitest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.liang.customreport.enums.JdApiEnum;
import com.liang.customreport.jdapicall.JdApiV2Service;
import com.liang.customreport.jdapicall.bo.ParamInfo;
import com.liang.customreport.jdapicall.bo.common.JdApiV2ResultBo;
import com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgCustomQueryV1ReqBO;
import com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgCustomQueryV1ResBO;
import com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgDownloadReqBO;
import com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgDownloadResBO;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Optional;
import org.junit.Test;

/**
 * @author liangbingtian
 * @date 2023/11/01 下午12:25
 */
public class ApiCallTest {

  @Test
  public void callApiTest() {
    try (InputStream inputStream = this.getClass().getClassLoader()
        .getResourceAsStream("api/customreport_queryparam.json")) {
      final JingdongAdsIbgCustomQueryV1ReqBO o = JSON.parseObject(
          Optional.ofNullable(inputStream).orElseThrow(() -> new RuntimeException("读取的为null")),
          JingdongAdsIbgCustomQueryV1ReqBO.class, Feature.OrderedField);
      final ParamInfo info = ParamInfo.builder()
          .accessToken("1fb2353257524bf88d3a067195bdba82mzi1")
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
      reqBO.setDownloadId(46915131);
      final ParamInfo info = ParamInfo.builder()
          .accessToken("1fb2353257524bf88d3a067195bdba82mzi1")
          .appKey("A1D3C721A3E382FF4915BE266B4294F6")
          .appSecret("8d08db8de0ec468ebe234dcfdc1c3dca")
          .api(JdApiEnum.REPORT_DOWNLOAD_QUERY.getApi())
          .build();
      JdApiV2Service<JingdongAdsIbgDownloadReqBO, JingdongAdsIbgDownloadResBO> service = new JdApiV2Service<>();
      final JingdongAdsIbgDownloadResBO result = service
          .doRequest(reqBO, JingdongAdsIbgDownloadResBO.class, info);
      System.out.println();
    } catch (Exception e) {
      e.printStackTrace();
    }
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
    String saveDirectory = "/Users/liangbingtian/Downloads/文件下载测试/"; // 替换为你要保存文件的目录

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

}
