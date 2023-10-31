package com.liang.customreport.excel.export;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;

public interface IExcelValueWriteProcess {

  Object process(Cell cell, Object input, Font font);
}
