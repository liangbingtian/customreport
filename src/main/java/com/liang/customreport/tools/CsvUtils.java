package com.liang.customreport.tools;

import com.liang.customreport.files.ResolveCsvVisitor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liangbingtian
 * @date 2023/11/01 下午5:29
 */
@Slf4j
public class CsvUtils {

  public static void processCsvToJson(String source, String target) throws IOException {
    ResolveCsvVisitor visitor = new ResolveCsvVisitor(target);
    Files.walkFileTree(Paths.get(source), visitor);
    log.info("目录:{}里的csv文件转换为json文件完毕", source);
  }

}
