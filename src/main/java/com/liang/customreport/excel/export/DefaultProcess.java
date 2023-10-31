package com.liang.customreport.excel.export;


import java.math.BigDecimal;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.stereotype.Component;

/**
 * @author baggio
 * @create 2018-12-23 15:40
 **/
@Component("defaultProcess")
public class DefaultProcess implements IExcelValueWriteProcess {

  @Override
  public Object process(Cell cell, Object input, Font font) {
    if (input == null) {
      return "";
    }
    if (input instanceof BigDecimal) {
      return ((BigDecimal) input).doubleValue();
    }
    return input;
  }

}
