package csv;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

/**
 * @author liangbingtian
 * @date 2023/10/31 下午5:35
 */
public class CSVTest {

  @Test
  public void basicCsvReader() throws IOException, URISyntaxException {
    final Path path = getPath();
    try (
        Reader reader = Files.newBufferedReader(path);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
    ) {
      for (CSVRecord csvRecord : csvParser) {
        // Accessing Values by Column Index
        String name = csvRecord.get(0);
        String email = csvRecord.get(1);
        String phone = csvRecord.get(2);
        String country = csvRecord.get(3);
      }
    }
  }

  private Path getPath() throws URISyntaxException, IOException {
    final URL resource = this.getClass().getClassLoader().getResource("1.csv");
    return Paths
        .get(Optional.ofNullable(resource).orElseThrow(() -> new IOException("文件路径为空")).toURI());
  }

  @Test
  public void CSVReaderWithManualHeader() throws IOException, URISyntaxException {
    final Path path = getPath();
    try (
        Reader reader = Files.newBufferedReader(path);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
            .withHeader("日期", "账户名称", "产品线", "计划ID")
            .withIgnoreHeaderCase()
            .withTrim());
    ) {
      for (CSVRecord csvRecord : csvParser) {
        System.out.println(csvParser.getCurrentLineNumber());
        // Accessing values by the names assigned to each column
        String name = csvRecord.get("日期");
        String email = csvRecord.get("账户名称");
        String phone = csvRecord.get("产品线");
        String country = csvRecord.get("计划ID");
      }
    }
  }
}
