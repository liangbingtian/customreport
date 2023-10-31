package com.liang.customreport.excel.handler;

import com.liang.customreport.excel.IExcelProcessor;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Setter
@Getter
abstract class AbstractExcelHandler {

  protected int curRow;
  protected int curColumn;
  /**
   * 当前sheet的索引
   */
  protected int sheetIndex = -1;
  /**
   * 当前sheet的名称
   */
  protected String sheetName = "";

  protected List<IExcelProcessor> excelProcessorList;
  /**
   * 当前行记录的LinkedHashMap存储结构
   * recordMap是Map<String, Object>类型，可以方便转换成JSONObject：new JSONObject(recordMap)
   * 因此，key为String类型，value为Object类型
   */
  protected Map<String, Object> recordMap = new LinkedHashMap<>();

  public AbstractExcelHandler(List<IExcelProcessor> excelProcessorList) {
    this.excelProcessorList = excelProcessorList;
  }

  abstract void processFileInput(String filePath);

  abstract void processInputStream(InputStream inputStream);

  /**
   * 表示excel中的一行遍历结束 当前lineRecordMap为空时，过滤。
   */
  public void processRowRecord() {
    if (recordMap.size() != 0) {
      Map<String, Object> map = recordMap;
      recordMap = new LinkedHashMap<>();
      excelProcessorList.forEach(excelProcessor -> {
        excelProcessor.processRecord(this.sheetName, this.sheetIndex, this.getCurRow(), map);
      });
    }
  }

  /**
   * 供Sheethandler使用。当某个单元格有值时，添加到lineRecordMap 过滤空值
   *
   * @param value
   */
  public void addValue(String value) {
    if (StringUtils.isEmpty(value)) {
      return;
    }
    value = value.trim();
    this.recordMap.put(this.getCurColumn() + "", value);
  }

}
