package com.liang.customreport.excel.handler;

import static org.apache.poi.hssf.model.InternalWorkbook.WORKBOOK_DIR_ENTRY_NAMES;

import com.liang.customreport.excel.dto.ExcelRowItem;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.HSSFUserException;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.RecordFactoryInputStream;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

@Slf4j
class HSSFIteratorEventFactory {

  private static Method method = null;

  private RecordFactoryInputStream recordStream;

  private HSSFRequest req;

  private ExcelRowItem rowItem;

  private HSSFIteratorHandler iteratorHandler;

  private FormatTrackingHSSFListener listener;

  InputStream in;

  public HSSFIteratorEventFactory(
      HSSFIteratorHandler iteratorHandler, FormatTrackingHSSFListener listener) {
    this.iteratorHandler = iteratorHandler;
    this.listener = listener;
  }

  public void processWorkbookEvents(HSSFRequest req, POIFSFileSystem fs) throws IOException {
    processWorkbookEvents(req, fs.getRoot());
  }

  public void processWorkbookEvents(HSSFRequest req, DirectoryNode dir) throws IOException {
    String name = null;
    Set<String> entryNames = dir.getEntryNames();
    for (String potentialName : WORKBOOK_DIR_ENTRY_NAMES) {
      if (entryNames.contains(potentialName)) {
        name = potentialName;
        break;
      }
    }
    if (name == null) {
      name = WORKBOOK_DIR_ENTRY_NAMES[0];
    }
    in = dir.createDocumentInputStream(name);
    processEvents(req, in);
  }

  public void processEvents(HSSFRequest req, InputStream in) {
    try {
      genericProcessEvents(req, in);
    } catch (HSSFUserException hue) {
    }
  }

  private void genericProcessEvents(HSSFRequest req, InputStream in)
      throws HSSFUserException {
    recordStream = new RecordFactoryInputStream(in, false);
    this.req = req;
  }

  public boolean hasNext() {
    short userCode = 0;
    if(recordStream == null || req == null) {
      return false;
    }
    while (true) {
      Record r = recordStream.nextRecord();
      if (r == null) {
        break;
      }
      listener.processRecord(r);
      if(iteratorHandler.isDataExist()) {
        this.rowItem = iteratorHandler.getRowItem();
        iteratorHandler.setDataExist(iteratorHandler.dataExist());
        return true;
      }
    }
    return false;
  }

  public ExcelRowItem next() {
    return this.rowItem;
  }

  public void close() {
    try {
      in.close();
    } catch (Exception e) {
      log.error("", e);
    }
  }
}
