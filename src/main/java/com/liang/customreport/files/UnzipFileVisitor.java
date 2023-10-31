package com.liang.customreport.files;

import com.liang.customreport.exception.BusinessRuntimeException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnzipFileVisitor extends SimpleFileVisitor<Path> {

  private static final Logger logger = LoggerFactory.getLogger(UnzipFileVisitor.class);

  List<Path> extractFiles = new CopyOnWriteArrayList<>();

  private final String targetPath;


  public UnzipFileVisitor(String targetPath) {
    this.targetPath = targetPath;
  }

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
      throws IOException {
    if (!file.toString().endsWith(".zip")) {
      return FileVisitResult.CONTINUE;
    }
    Objects.requireNonNull(file);
    Objects.requireNonNull(attrs);
    logger.info("解压文件:{}", file.toAbsolutePath().toString());
    doExtract(file);
    extractFiles.add(file);
    return FileVisitResult.CONTINUE;
  }

  public List<Path> getExtractFiles() {
    return extractFiles;
  }

  private void doExtract(Path path) throws ZipException {
    final ZipFile zipFile = Optional.ofNullable(Zip4jUtil.newZipFile(path.toString()))
        .orElseThrow(() -> new BusinessRuntimeException("找不到这个文件"));
    zipFile.extractAll(targetPath);
  }
}
