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
class XSSFIterator implements Iterator<ExcelRowItem>, Closeable {

  /**
   * 输入流
   */
  private InputStream inputStream;

  XSSFIteratorHandler xssfIteratorHandler;

  public XSSFIterator(InputStream inputStream) {
    this.inputStream = inputStream;
    xssfIteratorHandler = new XSSFIteratorHandler();
    xssfIteratorHandler.processInputStream(this.inputStream);
  }

  @Override
  public boolean hasNext() {
    return xssfIteratorHandler.hasNext();
  }

  @Override
  public ExcelRowItem next() {
    return xssfIteratorHandler.next();
  }

  public void close() {
    xssfIteratorHandler.close();
  }
}
