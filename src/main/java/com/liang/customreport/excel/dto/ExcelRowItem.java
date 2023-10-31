package com.liang.customreport.excel.dto;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * excel中的行信息
 * @author liuqiangm
 * @create 2020/12/24 20:07
 */
@Getter
@Setter
@ToString
public class ExcelRowItem {
  /**
   * sheet名称
   */
  private String sheetName;
  // sheet编号
  private int sheetIndex;
  // 行号
  private int rowIndex;
  private Map<String, Object> dataMap;
}
