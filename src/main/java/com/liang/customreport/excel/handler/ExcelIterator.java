package com.liang.customreport.excel.handler;

import com.liang.customreport.excel.dto.ExcelRowItem;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liuqiangm
 * @create 2020/12/28 20:28
 */
@Slf4j
public class ExcelIterator implements Iterator<ExcelRowItem>, Closeable {

  BufferedInputStream bufferedInputStream;

  Iterator<ExcelRowItem> iterator;

  private InputStream inputStream;

  public ExcelIterator(InputStream inputStream) {
    this.inputStream = inputStream;
    this.bufferedInputStream = new BufferedInputStream(inputStream);
    this.bufferedInputStream.mark(-1);
    byte[] bytes = new byte[8];
    try {
      bufferedInputStream.read(bytes);
      bufferedInputStream.reset();
    } catch (IOException e) {
      log.error("", e);
      throw new RuntimeException(e.getMessage());
    }
    // xls格式
    if(bytes[0] == -48 && bytes[1] == -49 && bytes[2] == 17 && bytes[3] == -32
        && bytes[4] == -95 && bytes[5] == -79 && bytes[6] == 26 && bytes[7] == -31) {
      iterator = new HSSFIterator(bufferedInputStream);
    } else {
      // xlsx格式
      iterator = new XSSFIterator(bufferedInputStream);
    }
  }

  @Override
  public void close() {
    try {
      this.bufferedInputStream.close();
    } catch (IOException e) {
      log.error("", e);
      throw new RuntimeException(e.getMessage());
    }
    try {
      this.inputStream.close();
    } catch (IOException e) {
      log.error("", e);
      throw new RuntimeException(e.getMessage());
    }
    if(iterator instanceof XSSFIterator) {
      ((XSSFIterator) iterator).close();
    } else if(iterator instanceof HSSFIterator) {
      ((HSSFIterator) iterator).close();
    }
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public ExcelRowItem next() {
    return iterator.next();
  }
}
