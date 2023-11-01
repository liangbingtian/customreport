package com.liang.customreport.files;

import com.alibaba.fastjson.JSONWriter;
import com.liang.customreport.common.Constants;
import com.liang.customreport.files.consumer.CsvToJsonConsumer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Random;
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
    if (!file.toString().endsWith(".csv")) {
      return FileVisitResult.CONTINUE;
    }
    return FileVisitResult.CONTINUE;
  }

  private void doResolveCsv(Path path) {
    final String targetJsonPath = targetPath + "/" + System.currentTimeMillis() + ".json";
    CsvToJsonConsumer consumer = new CsvToJsonConsumer(targetJsonPath);
    try (
        Reader reader = Files.newBufferedReader(path);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
            .withHeader(Constants.CSV_HEADER)
            .withIgnoreHeaderCase()
            .withTrim());
        JSONWriter writer = new JSONWriter(new BufferedWriter(new FileWriter(targetJsonPath)))
    ) {
      for (CSVRecord csvRecord : csvParser) {
        if (csvParser.getCurrentLineNumber()==1) {
          continue;
        }
        // Accessing values by the names assigned to each column
        String name = csvRecord.get("日期");
        String email = csvRecord.get("账户名称");
        String phone = csvRecord.get("产品线");
        String country = csvRecord.get("计划ID");
      }

    } catch (Exception e) {

    }
  }
}
