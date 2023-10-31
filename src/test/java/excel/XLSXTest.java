package excel;

import com.alibaba.fastjson.JSONObject;
import com.liang.customreport.excel.dto.ExcelRowItem;
import com.liang.customreport.excel.enums.ExcelTypeEnum;
import com.liang.customreport.excel.handler.ExcelHandlerFacade;
import com.liang.customreport.excel.handler.ExcelIterator;
import org.junit.Test;

/**
 * 针对XLSX、XLSM格式，可正确处理。
 */
public class XLSXTest {

  @Test
  public void xlsxTest() {
    ExcelHandlerFacade excelHandler = new ExcelHandlerFacade();
    excelHandler.addExcelProcessor(((sheetName, sheetIndex, rowIndex, recordMap) -> {
      System.out.println(String
          .format("sheetName: %s, sheetIndex: %s, row: %d, record: %s", sheetName, sheetIndex,
              rowIndex, recordMap.toString()));
      JSONObject jsonObject = new JSONObject(recordMap);
      // 可以在此处将jsonObject数据使用转换工具，转换成任意目标格式（目标json或目标实体对象）
    }));
    excelHandler.processInputStream(
        XLSXTest.class.getClassLoader().getResourceAsStream("123.xlsx"));
  }

  @Test
  public void xlsxIteratorTest() {
    ExcelIterator iterator = new ExcelIterator(
        XLSXTest.class.getClassLoader().getResourceAsStream("t0.xlsx"));
    while (iterator.hasNext()) {
      ExcelRowItem rowItem = iterator.next();
      System.out.println(String
          .format("sheetName: %s, sheetIndex: %s, row: %d, record: %s", rowItem.getSheetName(),
              rowItem.getSheetIndex(), rowItem.getRowIndex(), rowItem.getDataMap().toString()));
    }
    iterator.close();
  }


  @Test
  public void xlsmTest() {
    ExcelHandlerFacade excelHandler = new ExcelHandlerFacade();
    excelHandler.addExcelProcessor(((sheetName, sheetIndex, rowIndex, recordMap) -> {
      System.out.println(String
          .format("sheetName: %s, sheetIndex: %s, row: %d, record: %s", sheetName, sheetIndex,
              rowIndex, recordMap.toString()));
      JSONObject jsonObject = new JSONObject(recordMap);
      // 可以在此处将jsonObject数据使用转换工具，转换成任意目标格式（目标json或目标实体对象）
    }));
    excelHandler.processInputStream(
        XLSXTest.class.getClassLoader().getResourceAsStream("t0.xlsm"),
        ExcelTypeEnum.XLSX);
  }

}
