package com.liang.customreport.tools;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.util.zip.ZipInputStream;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liangbingtian
 * @date 2023/11/03 上午9:37
 */
@Slf4j
public class WebUrlUtils {

  public static void getFileFromUrl(String fileUrl, Path saveDirectory)
      throws IOException {

    // 创建 URL 对象
    URL url = new URL(fileUrl);

    // 打开连接
    URLConnection connection = url.openConnection();

    // 获取文件名（从 Content-Disposition 头字段中获取）
    String fileName = getFileNameFromHeader(url);

    // 创建文件输出流
    FileOutputStream outputStream = new FileOutputStream(
        saveDirectory.resolve(fileName).toString());

    // 获取输入流
    InputStream inputStream = connection.getInputStream();

    // 读取和写入文件
    byte[] buffer = new byte[1024];
    int length;
    while ((length = inputStream.read(buffer)) != -1) {
      outputStream.write(buffer, 0, length);
    }

    // 关闭流
    inputStream.close();
    outputStream.close();

    log.info("文件下载完成！文件夹:{},文件名:{}", saveDirectory, fileName);
  }

  public static byte[] getByteArrayFromUrl(String fileUrl) throws MalformedURLException {
    URL url = new URL(fileUrl);
    try (InputStream in = url.openStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      byte[] buffer = new byte[8192];
      int bytesRead;
      while ((bytesRead = in.read(buffer)) != -1) {
        out.write(buffer, 0, bytesRead);
      }
      return out.toByteArray();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }

  public static ZipInputStream getZipInputStreamFromUrl(String fileUrl)
      throws IOException {
    // 创建 URL 对象
    URL url = new URL(fileUrl);

    // 打开 URL 连接
    InputStream urlStream = url.openStream();

    // 创建 ZipInputStream 并将其连接到 URL 输入流
    return new ZipInputStream(urlStream);
  }

  public static byte[] getByteArrayFromZipInputStream(ZipInputStream zipInputStream) {
    try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      byte[] buffer = new byte[8192];
      int bytesRead;
      while ((bytesRead = zipInputStream.read(buffer)) != -1) {
        out.write(buffer, 0, bytesRead);
      }
      return out.toByteArray();
    }catch (Exception e) {
      log.error(e.getMessage(), e);
      return null;
    }
  }



  public static String getFileNameFromHeader(URL url) throws UnsupportedEncodingException {
    String fileName = null;
    final String decode = URLDecoder.decode(url.getPath(), "UTF-8");
    int lastSlashIndex = decode.lastIndexOf('/');
    if (lastSlashIndex != -1) {
      // 提取最后一个斜杠后面的所有字符
      fileName = decode.substring(lastSlashIndex + 1);
    }
    if (fileName == null || fileName.isEmpty()) {
      fileName = "downloadedFile";
    }
    return fileName;
  }

}
