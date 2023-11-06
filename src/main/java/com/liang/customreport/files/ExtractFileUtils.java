package com.liang.customreport.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liangbingtian
 * @date 2023/10/31 下午6:06
 */
@Slf4j
public class ExtractFileUtils {

  public static void extractZipFiles(String source, String target) throws IOException {
    UnzipFileVisitor visitor = new UnzipFileVisitor(target);
    Files.walkFileTree(Paths.get(source), visitor);
    log.info("目录:{},里的zip文件解压完毕", source);
  }



}
