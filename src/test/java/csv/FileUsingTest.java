package csv;

import com.liang.customreport.files.ExtractFileUtils;
import java.io.IOException;
import java.nio.file.Paths;
import org.junit.Test;

/**
 * @author liangbingtian
 * @date 2023/10/31 下午6:11
 */
public class FileUsingTest {

  @Test
  public void unzipFiles() throws IOException {
    ExtractFileUtils.extractZipFiles("/Users/liangbingtian/Downloads/压缩文件测试",
        "/Users/liangbingtian/Downloads/压缩文件解压");
  }

}
