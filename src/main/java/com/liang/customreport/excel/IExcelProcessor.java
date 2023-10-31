package com.liang.customreport.excel;

import java.util.Map;

/**
 * 函数式接口。 用于表示获取到指定工作簿、工作簿索引、行号的记录数据后，应该做的处理。 在遍历excel时，调用方需要指定针对每条记录所做的处理。 可使用lambda表达式
 *
 * @author liuqiangm
 */
@FunctionalInterface
public interface IExcelProcessor {

  /**
   * 获取到excel内部的每一行元素后，添加处理逻辑。
   * recordMap是Map<String, Object>类型，可以方便转换成JSONObject：new JSONObject(recordMap)
   * @param sheetName
   * @param sheetIndex
   * @param rowIndex
   * @param recordMap
   */
  void processRecord(String sheetName, int sheetIndex, int rowIndex,
      Map<String, Object> recordMap);
}
