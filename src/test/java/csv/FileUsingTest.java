package csv;

import com.liang.customreport.files.ExtractFileUtils;
import com.liang.customreport.tools.CsvUtils;
import com.liang.customreport.tools.WebUrlUtils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipInputStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author liangbingtian
 * @date 2023/10/31 下午6:11
 */
@Slf4j
public class FileUsingTest {

  final String source = "/Users/liangbingtian/Downloads/报错文件测试";
  final String target2 = "/Users/liangbingtian/Downloads/json文件";
  final String target3 = "/Users/liangbingtian/Downloads/整体流程测试/FZ";

  @Test
  public void unzipFiles() throws IOException {
    ExtractFileUtils.extractZipFiles(source, null);
  }

  @Test
  public void csvToJson() throws IOException {
    CsvUtils.processCsvToJson(source, null);
  }

  @Test
  public void createDirectory() throws IOException {
    String directoryName = "newDirectory";
    // 获取当前项目的路径
    Path currentPath = Paths.get(System.getProperty("user.dir"));
    // 拼接要创建的目录路径
    Path directoryPath = currentPath.resolve(directoryName);
    Files.createDirectories(directoryPath);
    log.info("目录创建成功，目录路径为:{}", directoryPath);
  }

  @Test
  public void urlFileTest() throws IOException {
    String url = "https://storage.jd.com/report-engine-cloud-clouds/20231107/1699356857755000-537200239923998720/%E8%87%AA%E5%AE%9A%E4%B9%89%E6%8A%A5%E8%A1%A8API_2023%E5%B9%B411%E6%9C%8807%E6%97%A519%E6%97%B634%E5%88%8617%E7%A7%92%E4%B8%8B%E8%BD%BD.csv?Expires=1715254457&AccessKey=JdqUoH2NY2px0LAI&Signature=gpnn9Yu9VPgNGoXXvT7TM%2BS7mw0%3D";
    final ZipInputStream zipInputStreamFromUrl = WebUrlUtils.getZipInputStreamFromUrl(url);
    final byte[] byteArrayFromZipInputStream = WebUrlUtils
        .getByteArrayFromZipInputStream(zipInputStreamFromUrl);
    System.out.println();
  }

}
