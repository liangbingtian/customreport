package com.liang.customreport.excel.handler;

import com.liang.customreport.excel.IExcelProcessor;
import com.liang.customreport.excel.enums.ExcelTypeEnum;
import io.jsonwebtoken.lang.Collections;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;

/**
 * excel处理工具。 1. 通过processFileInput或processInputStream来指定excel文件来源。 2.
 * 通过addExcelProcessor来添加针对excel中的行记录的处理方式。 3. 支持XLS、XLSX（包含XLSM等）等excel格式的解析。
 * 基于事件驱动，占用内存极小，解析速度很快。
 *
 * @author liuqiangm
 */
@Slf4j
public class ExcelHandlerFacade {

  private List<IExcelProcessor> excelProcessorList = new LinkedList<>();

  /**
   * 添加Excel处理器
   *
   * @param excelProcessor
   */
  public void addExcelProcessor(IExcelProcessor excelProcessor) {
    if (excelProcessor != null) {
      excelProcessorList.add(excelProcessor);
    }
  }

  /**
   * 若不传excel文件类型，则默认使用XLSX格式先进行解析。若失败，则进而使用XLS进行解析。 由于文件File可重复进行解析，因此这种实现方式没有问题。
   * 若使用InputStream，则要考虑到一旦解析失败，则inputStream可能无法回退。 因此，该推断方法对于InputStream不适用。
   *
   * @param filePath
   */
  public void processFileInput(String filePath) {
    try {
      processFileInput(filePath, ExcelTypeEnum.XLSX);
    } catch (OLE2NotOfficeXmlFileException e) {
      processFileInput(filePath, ExcelTypeEnum.XLS);
    }
  }

  /**
   * 传入指定的excel类型进行处理
   *
   * @param filePath
   * @param excelTypeEnum
   */
  public void processFileInput(String filePath, ExcelTypeEnum excelTypeEnum) {
    if (Collections.isEmpty(excelProcessorList)) {
      log.info("当前excel读取任务不存在处理类");
    }
    if (ExcelTypeEnum.XLSX.equals(excelTypeEnum)) {
      AbstractExcelHandler excelHandler = new XSSFHandler(excelProcessorList);
      excelHandler.processFileInput(filePath);
    } else if (ExcelTypeEnum.XLS.equals(excelTypeEnum)) {
      AbstractExcelHandler excelHandler = new HSSFHandler(excelProcessorList);
      excelHandler.processFileInput(filePath);
    } else {
      log.error(
          "调用该方法，必须传入excelTypeEnum的类型。请传入该参数，或改为调用不含excelTypeEnum参数的process方法（将默认以xlsx、xls顺序尝试解析）");
    }
  }

  /**
   * 传入指定的excel类型进行处理
   *
   * @param inputStream
   */
  public void processInputStream(InputStream inputStream) {
    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
    try {
      processInputStream(bufferedInputStream, ExcelTypeEnum.XLSX);
    } catch (OLE2NotOfficeXmlFileException e) {
      processInputStream(bufferedInputStream, ExcelTypeEnum.XLS);
    }
  }

  /**
   * 传入指定的excel类型进行处理
   *
   * @param inputStream
   * @param excelTypeEnum
   */
  public void processInputStream(InputStream inputStream, ExcelTypeEnum excelTypeEnum) {
    if (Collections.isEmpty(excelProcessorList)) {
      log.info("当前excel读取任务不存在处理类");
    }
    if (ExcelTypeEnum.XLSX.equals(excelTypeEnum)) {
      AbstractExcelHandler excelHandler = new XSSFHandler(excelProcessorList);
      excelHandler.processInputStream(inputStream);
    } else if (ExcelTypeEnum.XLS.equals(excelTypeEnum)) {
      AbstractExcelHandler excelHandler = new HSSFHandler(excelProcessorList);
      excelHandler.processInputStream(inputStream);
    } else {
      log.error("传入inputStream类型参数进行处理，必须传入excelTypeEnum的类型。请传入该参数");
    }
  }

}
