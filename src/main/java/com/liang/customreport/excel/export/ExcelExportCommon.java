package com.liang.customreport.excel.export;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author baggio
 * @create 2018-12-21 19:52
 **/
@Slf4j
@Component("excelExportCommon")
public class ExcelExportCommon {

  @Autowired
  private ExcelWriteProcessFactory excelWriteProcessFactory;
  /**
   * 描述：todo param:
   * param: addRow:对模板增加的行数
   * param: templateName：模板名称
   * param: entityName:取值对象
   * 作者：jinggq
   * 时间：2018-12-25
   */
  public void excelExport(Workbook workbook, int addRow,int addCell, String templateName, Object entityName)
      throws Exception {
    if (entityName == null) {
      return;
    }
    try (InputStream intputStream = ExcelExportCommon.class.getClassLoader().getResourceAsStream(templateName)){
      Type type = new TypeReference<List<ExcelTemplateConfig>>() {}.getType();

      //读取excel模板
      List<ExcelTemplateConfig> configs = JSON.parseObject(IOUtils.toString(intputStream), type);

      for (ExcelTemplateConfig template : configs) {
        Sheet sheet = workbook.getSheetAt(template.getSheet());
        Row row = sheet.getRow(template.getRow() + addRow);
        Cell cell = row.getCell(template.getCell() + addCell);
        PropertyDescriptor propertyDescriptor = BeanUtils
            .getPropertyDescriptor(entityName.getClass(), template.getField());
        //读取数据
        Object object = propertyDescriptor.getReadMethod().invoke(entityName);
        //处理数据，用哪个预处理对象
        String preProsss = template.getPreProcessClass();
        IExcelValueWriteProcess process = excelWriteProcessFactory.getProcess(preProsss);

        Object value = process.process(cell, object, workbook.createFont());
        if (value instanceof Double){
          cell.setCellValue((Double) value);
        } else if (value instanceof RichTextString) {
          cell.setCellValue((RichTextString) value);
        } else {
          cell.setCellValue(value.toString());
        }
        if (!sheet.getForceFormulaRecalculation()) {
          sheet.setForceFormulaRecalculation(true);
        }
      }
    } catch (Exception e) {
      throw new Exception(e);
    }
  }
}
