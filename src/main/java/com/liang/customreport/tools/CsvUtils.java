package com.liang.customreport.tools;

import com.liang.customreport.files.ResolveCsvVisitor;
import com.liang.customreport.files.UnzipFileVisitor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author liangbingtian
 * @date 2023/11/01 下午5:29
 */
public class CsvUtils {

  public static void processCsvToJson(String source, String target) throws IOException {
    ResolveCsvVisitor visitor = new ResolveCsvVisitor(target);
    Files.walkFileTree(Paths.get(source), visitor);
  }

}
