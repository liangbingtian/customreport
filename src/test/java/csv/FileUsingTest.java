package csv;

import com.liang.customreport.files.ExtractFileUtils;
import com.liang.customreport.tools.CsvUtils;
import java.io.IOException;
import java.nio.file.Paths;
import org.junit.Test;

/**
 * @author liangbingtian
 * @date 2023/10/31 下午6:11
 */
public class FileUsingTest {

  final String source = "/Users/liangbingtian/Downloads/压缩文件测试";
  final String target = "/Users/liangbingtian/Downloads/压缩文件解压";
  final String target2 = "/Users/liangbingtian/Downloads/json文件";

  @Test
  public void unzipFiles() throws IOException {
    ExtractFileUtils.extractZipFiles(source,
        target);
  }

  @Test
  public void csvToJson() throws IOException {
    CsvUtils.processCsvToJson(target, target2);
  }

}
