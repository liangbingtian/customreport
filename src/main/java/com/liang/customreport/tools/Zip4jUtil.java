package com.liang.customreport.tools;

import com.liang.customreport.exception.BusiExceptionUtils;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;

/**
 * Zip4j工具类，用于解决一些包含/或../的压缩包兼容性问题
 *
 * @author gaotx
 */
@Slf4j
public class Zip4jUtil {

  /**
   * 压缩文件夹中需要过滤的FileHeader
   */
  private static final String[] ILLEGAL_HEADER_NAMES = new String[]{"/", "../", ".DS_Store"};

  /**
   * 构建ZipFile，去掉不需要的根目录相关内容
   */
  public static ZipFile newZipFile(String filePath) {
    try {
      ZipFile zipFile = new ZipFile(filePath);
      zipFile.setCharset(StandardCharsets.UTF_8);
      Iterator<FileHeader> iterator = zipFile.getFileHeaders().iterator();
      while (iterator.hasNext()) {
        FileHeader header = iterator.next();
        for (String headerName : ILLEGAL_HEADER_NAMES) {
          if (headerName.equals(header.getFileName())) {
            log.warn("zip文件{}中存在异常文件夹{},已移除", filePath, header.getFileName());
            iterator.remove();
          }
        }
      }
      return zipFile;
    } catch (Exception e) {
      BusiExceptionUtils.marshException(e);
    }
    return null;
  }

}
