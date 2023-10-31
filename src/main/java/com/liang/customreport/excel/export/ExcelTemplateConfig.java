package com.liang.customreport.excel.export;

import lombok.Getter;
import lombok.Setter;

/**
 * @author baggio
 * @create 2018-12-23 15:09
 * excel每一个cell的读取模板
 **/
@Getter
@Setter
public class ExcelTemplateConfig {

  /**
   * 字段
   */
  private String field;
  /**
   * excel页签
   */
  private int sheet;
  /**
   * 行
   */
  private int row;

  /**
   * 单元格
   */
  private int cell;

  /**
   * 数据预处理
   */
  private String preProcessClass;

}
