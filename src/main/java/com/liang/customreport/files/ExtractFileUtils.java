package com.liang.customreport.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author liangbingtian
 * @date 2023/10/31 下午6:06
 */
public class ExtractFileUtils {

  public static void extractZipFiles(String source, String target) throws IOException {
    UnzipFileVisitor visitor = new UnzipFileVisitor(target);
    Files.walkFileTree(Paths.get(source), visitor);
    //todo 删除文件
  }



}
