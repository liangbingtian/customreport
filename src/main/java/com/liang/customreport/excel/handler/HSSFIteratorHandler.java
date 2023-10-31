package com.liang.customreport.excel.handler;

import com.liang.customreport.excel.dto.ExcelRowItem;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * 处理xls格式的文件
 *
 * @author liuqiangm
 */
@Setter
@Getter
@Slf4j
class HSSFIteratorHandler extends HSSFBaseHandler {

  private InputStream din;

  /**
   * 是否已经读取到了可用数据
   */
  private boolean dataExist = false;

  private HSSFIteratorEventFactory factory;

  private Deque<ExcelRowItem> rowItemDeque = new LinkedList<>();

  public HSSFIteratorHandler() {
    super(Collections.emptyList());
  }

  public boolean hasNext() {
    return factory.hasNext();
  }

  public ExcelRowItem next() {
    return factory.next();
  }

  @Override
  protected void processRecord() {
    if(recordMap.size() == 0) {
      return;
    }
    ExcelRowItem rowItem = new ExcelRowItem();
    rowItem.setDataMap(recordMap);
    rowItem.setRowIndex(this.curRow);
    rowItem.setSheetIndex(this.sheetIndex);
    rowItem.setSheetName(this.sheetName);
    rowItemDeque.addLast(rowItem);
    recordMap = new LinkedHashMap<>();
    // 标记当前已经找到了有效数据
    this.dataExist = true;
  }

  public ExcelRowItem getRowItem() {
    if(rowItemDeque.isEmpty()) {
      return null;
    }
    return rowItemDeque.pollFirst();
  }

  public boolean dataExist() {
    return !rowItemDeque.isEmpty();
  }

  public void setDataExist(boolean dataExist) {
    this.dataExist = dataExist;
  }

  @Override
  public void processInputStream(InputStream inputStream) {

    try {
      fs = new POIFSFileSystem(inputStream);
      din = fs.createDocumentInputStream("Workbook");
      MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
      formatListener = new FormatTrackingHSSFListener(listener);
      factory = new HSSFIteratorEventFactory(this, formatListener);
      HSSFRequest req = new HSSFRequest();
      req.addListenerForAllRecords(formatListener);
//      if (outputFormulaValues) {
//        req.addListenerForAllRecords(formatListener);
//      } else {
//        workbookBuildingListener = new SheetRecordCollectingListener(formatListener);
//        req.addListenerForAllRecords(workbookBuildingListener);
//      }
      factory.processWorkbookEvents(req, fs);
    } catch (Exception e) {
      log.error("", e);
    }
  }

  public void close() {
    if (din != null) {
      try {
        din.close();
      } catch (IOException e1) {
        log.error("", e1);
      }
    }
    if (fs != null) {
      try {
        fs.close();
      } catch (IOException e2) {
        log.error("", e2);
      }
    }
    if(factory != null) {
      factory.close();
    }
  }
}
