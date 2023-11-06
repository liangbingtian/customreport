package com.liang.customreport.files;

import cn.hutool.core.util.ZipUtil;
import com.liang.customreport.exception.BusinessRuntimeException;
import com.liang.customreport.tools.Zip4jUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor
@AllArgsConstructor
public class UnzipFileVisitor extends SimpleFileVisitor<Path> {

  private static final Logger logger = LoggerFactory.getLogger(UnzipFileVisitor.class);

  private String targetPath;

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
    Files.deleteIfExists(file);
    return FileVisitResult.CONTINUE;
  }

  private void doExtract(Path path) {
    ZipUtil.unzip(path.toString(), StringUtils.isNotBlank(targetPath)?targetPath:path.getParent().toString());
  }
}
