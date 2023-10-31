package com.liang.customreport.excel.handler;

import com.liang.customreport.excel.IExcelProcessor;
import com.liang.customreport.exception.BusiExceptionUtils;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ooxml.util.SAXHelper;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * 处理xlsx格式的文件
 *
 * @author liuqiangm
 */
@Setter
@Getter
@Slf4j
class XSSFHandler extends AbstractExcelHandler {

  public XSSFHandler(List<IExcelProcessor> excelProcessorList) {
    super(excelProcessorList);
  }

  @Override
  public void processFileInput(String filePath) {
    try (OPCPackage pkg = OPCPackage.open(filePath)) {
      processAllSheets(pkg);
    } catch (OLE2NotOfficeXmlFileException e) {
      log.error("");
      throw e;
    } catch (Exception e) {
      log.error("", e);
    }
  }

  @Override
  public void processInputStream(InputStream inputStream) {
    try (OPCPackage pkg = OPCPackage.open(inputStream)) {
      processAllSheets(pkg);
    } catch (OLE2NotOfficeXmlFileException e) {
      // 文件格式不匹配的异常由调用层处理
      throw e;
    } catch (Exception e) {
      BusiExceptionUtils.marshException(e);
    }
  }

  /**
   * 处理所有的sheet
   *
   * @param pkg
   * @throws Exception
   */
  private void processAllSheets(OPCPackage pkg) throws Exception {
    XSSFReader r = new XSSFReader(pkg);
    // 字符串常量符号表，这个只能一开始就全部加载到内存当中
    SharedStringsTable sst = r.getSharedStringsTable();
    StylesTable stylesTable = r.getStylesTable();
    XMLReader parser = fetchSheetParser(sst, stylesTable);
    Iterator<InputStream> sheets = r.getSheetsData();

    if (!(sheets instanceof XSSFReader.SheetIterator)) {
      return;
    }

    XSSFReader.SheetIterator sheetIterator = (XSSFReader.SheetIterator) sheets;
    while (sheetIterator.hasNext()) {
      try (InputStream dummy = sheetIterator.next()) {
        // 更新sheet的index
        sheetIndex++;
        // 获取sheetName
        sheetName = sheetIterator.getSheetName();
        InputSource sheetSource = new InputSource(dummy);
        // 对该sheet内部的数据进行处理
        parser.parse(sheetSource);
      }
    }
  }


  private XMLReader fetchSheetParser(SharedStringsTable sst, StylesTable stylesTable)
      throws SAXException, ParserConfigurationException {
    XMLReader parser = SAXHelper.newXMLReader();
    ContentHandler handler = new XSSFSheetHandler(sst, stylesTable, this);
    parser.setContentHandler(handler);
    return parser;
  }

}
