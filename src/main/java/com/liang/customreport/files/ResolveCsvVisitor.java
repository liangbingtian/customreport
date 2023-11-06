package com.liang.customreport.files;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.liang.customreport.common.Constants;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liangbingtian
 * @date 2023/10/31 下午11:22
 */
@Slf4j
public abstract class ResolveCsvVisitor<T> extends SimpleFileVisitor<Path> {

  private final String targetPath;

  private final T info;

  private final String[] excelHeaders;

  private final Map<String, String> excelHeadersMapping;

  public ResolveCsvVisitor(String targetPath, T info,
      String[] excelHeaders, Map<String, String> excelHeadersMapping) {
    this.targetPath = targetPath;
    this.info = info;
    this.excelHeaders = excelHeaders;
    this.excelHeadersMapping = excelHeadersMapping;
  }


  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
    if (!file.toString().endsWith(Constants.CSV)) {
      return FileVisitResult.CONTINUE;
    }
    log.info("csv文件转换为json:{}", file.toAbsolutePath().toString());
    doResolveCsv(file);
    Files.deleteIfExists(file);
    return FileVisitResult.CONTINUE;
  }

  private void doResolveCsv(Path path) {
    try (
        Reader reader = Files.newBufferedReader(path);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
            .withHeader(excelHeaders)
            .withIgnoreHeaderCase()
            .withTrim());
        JSONWriter writer = new JSONWriter(new BufferedWriter(new FileWriter(
            StringUtils.isNotBlank(targetPath)?targetPath:path.toString()
            .replace(".csv", ".json"))))
    ) {
      writer.config(SerializerFeature.WriteMapNullValue, true);
      writer.startArray();
      int i = 0;
      for (CSVRecord csvRecord : csvParser) {
        if (csvParser.getCurrentLineNumber() == 1) {
          continue;
        }
        JSONObject eachObject = new JSONObject();
        for (Map.Entry<String, String> entry : excelHeadersMapping.entrySet()) {
          final String chineseName = entry.getKey();
          final String targetEnglishName = entry.getValue();
          final String value = csvRecord.get(chineseName);
          eachObject.put(targetEnglishName, value);
        }
        //设置基本信息
        processBaseInfo(eachObject, info);
        writer.writeObject(eachObject);
        i++;
      }
      log.info("一共输出了: " + i + "条");
      writer.endArray();
      writer.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 解析csv时候有和业务相关的基本信息
   * @param t 基本信息
   * @param object jsonObject
   */
  abstract protected void processBaseInfo(JSONObject object, T t);
}
