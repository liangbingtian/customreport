package com.liang.customreport.excel;

import com.google.common.base.Preconditions;
import com.liang.customreport.excel.handler.ExcelHandlerFacade;
import com.liang.customreport.exception.BusiExceptionUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 2019/11/28 上午12:17
 *
 * @author liangbingtian
 *
 * @Description Excel处理的入口类
 */
public class CommonExcel {

  public static void processExcel(MultipartFile file, IExcelProcessor iExcelProcessor) {
    Preconditions.checkNotNull(file, "导入的文件为空");
    InputStream inputStream = null;
    try {
      inputStream = new ByteArrayInputStream(IOUtils.toByteArray(file.getInputStream()));
    } catch (IOException e) {
      BusiExceptionUtils.wrapBusiException(e.getMessage());
    }
    Preconditions.checkNotNull(file, "没有从文件中获取到文件流");
    ExcelHandlerFacade excelHandler = new ExcelHandlerFacade();
    excelHandler.addExcelProcessor(iExcelProcessor);
    excelHandler.processInputStream(inputStream);
  }

  public static void processExcel(InputStream inputStream, IExcelProcessor iExcelProcessor) {
    Preconditions.checkNotNull(inputStream, "导入文件转换异常");
    ExcelHandlerFacade excelHandler = new ExcelHandlerFacade();
    excelHandler.addExcelProcessor(iExcelProcessor);
    excelHandler.processInputStream(inputStream);
  }

}