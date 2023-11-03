package csv;

import com.liang.customreport.files.ExtractFileUtils;
import com.liang.customreport.tools.CsvUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author liangbingtian
 * @date 2023/10/31 下午6:11
 */
@Slf4j
public class FileUsingTest {

  final String source = "/Users/liangbingtian/Downloads/压缩文件测试";
  final String target = "/Users/liangbingtian/Downloads/压缩文件解压";
  final String target2 = "/Users/liangbingtian/Downloads/json文件";
  final String target3 = "/Users/liangbingtian/Downloads/整体流程测试/FZ";

  @Test
  public void unzipFiles() throws IOException {
    ExtractFileUtils.extractZipFiles(target3, null);
  }

  @Test
  public void csvToJson() throws IOException {
    CsvUtils.processCsvToJson(target3, null);
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

}
