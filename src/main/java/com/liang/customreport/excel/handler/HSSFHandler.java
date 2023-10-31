package com.liang.customreport.excel.handler;

import com.liang.customreport.excel.IExcelProcessor;
import java.util.List;
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
class HSSFHandler extends HSSFBaseHandler {

  public HSSFHandler(
      List<IExcelProcessor> excelProcessorList) {
    super(excelProcessorList);
  }

  @Override
  protected void processRecord() {
    processRowRecord();
  }
}
