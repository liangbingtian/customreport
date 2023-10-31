package com.liang.customreport.excel.handler;

import com.liang.customreport.excel.IExcelProcessor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.RKRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.StringRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * 处理xls格式的文件
 *
 * @author liuqiangm
 */
@Setter
@Getter
@Slf4j
abstract class HSSFBaseHandler extends AbstractExcelHandler implements HSSFListener {

  private SSTRecord sstrec;

  // 工作簿名称列表
  protected List<String> sheetNameList = new ArrayList<>();

  protected int minColumns = -1;
  protected POIFSFileSystem fs;

  protected int lastRowNumber;
  protected int lastColumnNumber;

  protected boolean outputFormulaValues = true;

  protected SheetRecordCollectingListener workbookBuildingListener;
  protected HSSFWorkbook stubWorkbook;

  protected SSTRecord sstRecord;
  protected FormatTrackingHSSFListener formatListener;

  protected BoundSheetRecord[] orderedBSRs;
  protected List<BoundSheetRecord> boundSheetRecords = new ArrayList<>();

  protected int nextRow;
  protected int nextColumn;
  protected boolean outputNextStringRecord;

  public HSSFBaseHandler(
      List<IExcelProcessor> excelProcessorList) {
    super(excelProcessorList);
  }

  @Override
  public void processRecord(Record record) {
    int thisRow = -1;
    int thisColumn = -1;
    String thisStr = null;

    switch (record.getSid()) {
      case BoundSheetRecord.sid:
        // 首先遍历所有的工作簿名称
        BoundSheetRecord bsr = (BoundSheetRecord) record;
        sheetNameList.add(bsr.getSheetname());
        break;
      case BOFRecord.sid:
        BOFRecord bof = (BOFRecord) record;
        if (bof.getType() == BOFRecord.TYPE_WORKSHEET) {
          sheetIndex++;
          this.sheetName = sheetNameList.get(sheetIndex);
        }
        break;
      case SSTRecord.sid:
        sstRecord = (SSTRecord) record;
        break;

      case BlankRecord.sid:
        BlankRecord brec = (BlankRecord) record;
        break;
      case BoolErrRecord.sid:
        break;

      case FormulaRecord.sid:
        FormulaRecord frec = (FormulaRecord) record;

        thisRow = frec.getRow();
        thisColumn = frec.getColumn();

        if (outputFormulaValues) {
          if (Double.isNaN(frec.getValue())) {
            outputNextStringRecord = true;
            nextRow = frec.getRow();
            nextColumn = frec.getColumn();
          } else {
            thisStr = formatListener.formatNumberDateCell(frec);
          }
        } else {
          thisStr = HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedExpression());
        }
        break;
      case StringRecord.sid:
        if (outputNextStringRecord) {
          StringRecord srec = (StringRecord) record;
          thisStr = srec.getString();
          thisRow = nextRow;
          thisColumn = nextColumn;
          outputNextStringRecord = false;
        }
        break;

      case LabelRecord.sid:
        LabelRecord lrec = (LabelRecord) record;

        thisRow = lrec.getRow();
        thisColumn = lrec.getColumn();
        thisStr = lrec.getValue();
        break;
      case LabelSSTRecord.sid:
        LabelSSTRecord lsrec = (LabelSSTRecord) record;

        thisRow = lsrec.getRow();
        thisColumn = lsrec.getColumn();
        if (sstRecord == null) {
          thisStr = '"' + "(No SST Record, can't identify string)" + '"';
        } else {
          thisStr = sstRecord.getString(lsrec.getSSTIndex()).toString();
        }
        break;
      case NoteRecord.sid:
        NoteRecord nrec = (NoteRecord) record;

        thisRow = nrec.getRow();
        thisColumn = nrec.getColumn();
        thisStr = '"' + "(TODO)" + '"';
        break;
      case NumberRecord.sid:
        NumberRecord numrec = (NumberRecord) record;

        thisRow = numrec.getRow();
        thisColumn = numrec.getColumn();

        thisStr = formatListener.formatNumberDateCell(numrec);
        break;
      case RKRecord.sid:
        RKRecord rkrec = (RKRecord) record;

        thisRow = rkrec.getRow();
        thisColumn = rkrec.getColumn();
        thisStr = '"' + "(TODO)" + '"';
        break;
      default:
        break;
    }
    if (thisRow != -1 && thisRow != lastRowNumber) {
      lastColumnNumber = -1;
    }
    if (record instanceof MissingCellDummyRecord) {
      MissingCellDummyRecord mc = (MissingCellDummyRecord) record;
      thisRow = mc.getRow();
      thisColumn = mc.getColumn();
      thisStr = "";
    }
    if (StringUtils.isNotEmpty(thisStr)) {
      thisStr = thisStr.trim();
      this.curRow = thisRow;
      this.curColumn = thisColumn;
      addValue(thisStr);
    }
    if (thisRow > -1) {
      lastRowNumber = thisRow;
    }
    if (thisColumn > -1) {
      lastColumnNumber = thisColumn;
    }

    if (record instanceof LastCellOfRowDummyRecord) {
      processRecord();
    }
  }

  protected abstract void processRecord();

  @Override
  public void processFileInput(String filePath) {
    try (InputStream inputStream = new FileInputStream(new File(filePath))) {
      processInputStream(inputStream);
    } catch (Exception e) {
      log.error("", e);
    }

  }

  @Override
  public void processInputStream(InputStream inputStream) {

    InputStream din = null;
    try {
      fs = new POIFSFileSystem(inputStream);
      din = fs.createDocumentInputStream("Workbook");
      MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
      formatListener = new FormatTrackingHSSFListener(listener);
      HSSFEventFactory factory = new HSSFEventFactory();
      HSSFRequest req = new HSSFRequest();
      if (outputFormulaValues) {
        req.addListenerForAllRecords(formatListener);
      } else {
        workbookBuildingListener = new SheetRecordCollectingListener(formatListener);
        req.addListenerForAllRecords(workbookBuildingListener);
      }
      factory.processWorkbookEvents(req, fs);
    } catch (Exception e) {
      log.error("", e);
    } finally {
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
    }
  }
}
