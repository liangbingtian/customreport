package com.liang.customreport.files;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONWriter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.liang.customreport.common.Constants;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * @author liangbingtian
 * @date 2023/10/31 下午11:22
 */
public class ResolveCsvVisitor extends SimpleFileVisitor<Path> {


  private final String targetPath;

  public ResolveCsvVisitor(String targetPath) {
    this.targetPath = targetPath;
  }


  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
    if (!file.toString().endsWith(Constants.CSV)) {
      return FileVisitResult.CONTINUE;
    }
    doResolveCsv(file);
    return FileVisitResult.CONTINUE;
  }

  private void doResolveCsv(Path path) {
    final String targetJsonPath = targetPath + "/" + System.currentTimeMillis() + ".json";
    try (
        Reader reader = Files.newBufferedReader(path);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
            .withHeader(Constants.CSV_HEADER)
            .withIgnoreHeaderCase()
            .withTrim());
        JSONWriter writer = new JSONWriter(new BufferedWriter(new FileWriter(targetJsonPath)))
    ) {

      writer.startArray();
      int i = 0;
      for (CSVRecord csvRecord : csvParser) {
        if (csvParser.getCurrentLineNumber() == 1) {
          continue;
        }
        JSONObject eachObject = new JSONObject();
        for (Map.Entry<String, String> entry : Constants.CSV_HEADER_MAP.entrySet()) {
          final String chineseName = entry.getKey();
          final String targetEnglishName = entry.getValue();
          final String value = csvRecord.get(chineseName);
          eachObject.put(targetEnglishName, value);
        }
        writer.writeObject(eachObject);
        i++;
      }
      System.out.println("一共输出了: " + i + "条");
      writer.endArray();
      writer.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
