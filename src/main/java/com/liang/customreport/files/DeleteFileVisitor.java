package com.liang.customreport.files;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 删除文件
 */
public class DeleteFileVisitor extends SimpleFileVisitor<Path> {

  private static final Logger logger = LoggerFactory.getLogger(
      DeleteFileVisitor.class);

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
      throws IOException {
    Objects.requireNonNull(file);
    Objects.requireNonNull(attrs);
    logger.info("删除:{}", file.toAbsolutePath().toString());
    Files.deleteIfExists(file);
    return FileVisitResult.CONTINUE;
  }

  @Override
  public FileVisitResult postVisitDirectory(Path dir, IOException exc)
      throws IOException {
    Objects.requireNonNull(dir);
    logger.info("删除:{}", dir.toAbsolutePath().toString());
    Files.deleteIfExists(dir);
    if (exc != null) {
      throw exc;
    }
    return FileVisitResult.CONTINUE;
  }


}
