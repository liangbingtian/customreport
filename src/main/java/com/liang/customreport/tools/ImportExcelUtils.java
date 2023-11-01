package com.liang.customreport.tools;

import com.google.common.base.Preconditions;
import com.liang.customreport.excel.BatchExcelConsumer;
import com.liang.customreport.excel.IExcelProcessor;
import com.liang.customreport.excel.handler.ExcelHandlerFacade;
import com.liang.customreport.exception.BusiExceptionUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import org.springframework.web.multipart.MultipartFile;

/**
 * 2019/11/28 上午12:17
 *
 * @author liangbingtian
 *
 * @Description Excel处理的入口类
 */
public class ImportExcelUtils {

  public static void processExcel(MultipartFile file, IExcelProcessor excelProcessor) {
    Preconditions.checkNotNull(file, "导入的文件为空");
    try {
      InputStream inputStream = file.getInputStream();
      processExcel(inputStream, excelProcessor);
    } catch (IOException e) {
      BusiExceptionUtils.marshException(e);
    }

  }

  public static void processExcel(File file, IExcelProcessor excelProcessor) {
    try {
      InputStream inputStream = Files.newInputStream(file.toPath());
      processExcel(inputStream, excelProcessor);
    }
    catch (IOException e) {
      BusiExceptionUtils.marshException(e);
    }
  }

  public static void processExcel(InputStream inputStream, IExcelProcessor excelProcessor) {
    Preconditions.checkNotNull(inputStream, "输入流为空");
    ExcelHandlerFacade excelHandler = new ExcelHandlerFacade();
    excelHandler.addExcelProcessor(excelProcessor);
    if(excelProcessor instanceof BatchExcelConsumer) {
      ((BatchExcelConsumer) excelProcessor).start();
    }
    excelHandler.processInputStream(inputStream);
    if(excelProcessor instanceof BatchExcelConsumer) {
      ((BatchExcelConsumer) excelProcessor).end();
    }
  }

}
