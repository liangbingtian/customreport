package com.liang.customreport.excel.handler;

import com.liang.customreport.excel.dto.ExcelRowItem;
import java.io.Closeable;
import java.io.InputStream;
import java.util.Iterator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 处理xls格式的文件
 *
 * @author liuqiangm
 */
@Setter
@Getter
@Slf4j
class HSSFIterator implements Iterator<ExcelRowItem>, Closeable {

  /**
   * 输入流
   */
  private InputStream inputStream;

  HSSFIteratorHandler hssfIteratorHandler;

  public HSSFIterator(InputStream inputStream) {
    this.inputStream = inputStream;
    hssfIteratorHandler = new HSSFIteratorHandler();
    hssfIteratorHandler.processInputStream(this.inputStream);
  }

  @Override
  public boolean hasNext() {
    boolean result = hssfIteratorHandler.hasNext();
    if(!result) {
      hssfIteratorHandler.close();
    }
    return result;
  }

  @Override
  public ExcelRowItem next() {
    return hssfIteratorHandler.next();
  }

  public void close() {
    hssfIteratorHandler.close();
  }
}
