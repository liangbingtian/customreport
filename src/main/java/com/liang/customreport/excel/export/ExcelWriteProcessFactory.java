package com.liang.customreport.excel.export;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author baggio
 * @create 2018-12-23 15:40
 **/
@Component
public class ExcelWriteProcessFactory {

  /*  @Autowired
    private static DateProcess dateProcess;*/
  @Autowired
  private ApplicationContext applicationContext;

  public IExcelValueWriteProcess getProcess(String name) {
    if (StringUtils.isEmpty(name)) {
      name = "defaultProcess";
    } else {
      name += "Process";
    }
    return applicationContext.getBean(name, IExcelValueWriteProcess.class);
  }

}
