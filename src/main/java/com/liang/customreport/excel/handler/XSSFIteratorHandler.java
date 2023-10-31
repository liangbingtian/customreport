package com.liang.customreport.excel.handler;

import com.google.common.base.Objects;
import com.liang.customreport.excel.dto.ExcelRowItem;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

@Slf4j
class XSSFIteratorHandler {

  protected int curRow;
  protected int curColumn;
  /**
   * 当前sheet的索引
   */
  protected int sheetIndex = -1;
  /**
   * 当前sheet的名称
   */
  protected String sheetName = "";

  /**
   * 标记当前sheet是否已经遍历结束
   */
  private boolean curSheetEnd = true;

  private XSSFReader.SheetIterator sheetIterator;

  private InputStream inputStream;

  private XMLEventReader reader;

  private boolean inlineStr = false;

  private boolean nextIsString;

  private String style = null;

  private String lastContents;

  private SharedStringsTable sst;

  private final DataFormatter formatter = new DataFormatter();

  private StylesTable stylesTable;

  private Map<String, Object> recordMap = new LinkedHashMap<>();

  private OPCPackage pkg;

  /**
   * 数据是否存在
   */
  private boolean dataExist = false;

  /**
   * 用于存储excel中的列索引（A~Z...）等翻译过来的int值。
   */
  private static final Map<String, Integer> column2IndexMap = new ConcurrentHashMap<>();


  public void processInputStream(InputStream inputStream) {
    try {
      pkg = OPCPackage.open(inputStream);
      processAllSheets(pkg);
    } catch (OLE2NotOfficeXmlFileException e) {
      // 文件格式不匹配的异常由调用层处理
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage());
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
    sst = r.getSharedStringsTable();
    stylesTable = r.getStylesTable();
    Iterator<InputStream> sheets = r.getSheetsData();

    if (!(sheets instanceof XSSFReader.SheetIterator)) {
      return;
    }

    sheetIterator = (XSSFReader.SheetIterator) sheets;
  }

  public void close() {
    closeInputStream();
    try {
      pkg.close();
    } catch (IOException e) {
      log.error("", e);
      throw new RuntimeException(e.getMessage());
    }
  }

  private void closeInputStream() {
    if(reader != null) {
      try {
        reader.close();
      } catch (XMLStreamException e) {
        log.error("", e);
        throw new RuntimeException(e.getMessage());
      }
      reader = null;
    }
    if(inputStream != null) {
      try {
        inputStream.close();
      } catch (IOException e) {
        log.error("", e);
        throw new RuntimeException(e.getMessage());
      }
      inputStream = null;
    }
  }

  public boolean hasNext() {
    if(curSheetEnd) {
      if(!sheetIterator.hasNext()) {
        close();
        return false;
      }
      inputStream = sheetIterator.next();
      // 更新sheet的index
      sheetIndex ++;
      // 获取sheetName
      sheetName = sheetIterator.getSheetName();
      XMLInputFactory factory = XMLInputFactory.newInstance();
      try {
        reader = factory.createXMLEventReader(inputStream);
      } catch (XMLStreamException e) {
        log.error("", e);
      }
      curSheetEnd = false;
      return hasNext();
    }
    iter();
    // 遍历当前sheet未找到数据，则继续遍历下个sheet
    if(!dataExist) {
      return hasNext();
    }
    // 遍历当前sheet得到数据
    return true;
  }

  public ExcelRowItem next() {
    ExcelRowItem rowItem = new ExcelRowItem();
    rowItem.setSheetName(this.sheetName);
    rowItem.setSheetIndex(this.sheetIndex);
    rowItem.setRowIndex(this.curRow);
    rowItem.setDataMap(this.recordMap);
    this.recordMap = new LinkedHashMap<>();
    this.dataExist = false;
    return rowItem;
  }

  public void iter() {
    while (reader.hasNext()) {
      XMLEvent event = null;
      try {
        event = reader.nextEvent();
      } catch (XMLStreamException e) {
        log.error("", e);
        throw new RuntimeException(e.getMessage());
      }
      if(event.isStartElement()) {
        StartElement startElement = event.asStartElement();
        String name = startElement.getName().getLocalPart();
        if(Objects.equal("row", name)) {
          this.curRow = Integer.valueOf(getAttributeVal(startElement.getAttributes(), "r")) - 1;
          continue;
        }
        if(Objects.equal("c", name)) {
          String cellType = getAttributeVal(startElement.getAttributes(), "t");
          if("inlineStr".equals(cellType)) {
            inlineStr = true;
          }
          this.curColumn = getColumnIndex(getAttributeVal(startElement.getAttributes(), "r"));
          if(cellType != null && Objects.equal("s", cellType)) {
            nextIsString = true;
          } else {
            nextIsString= false;
          }
          style = getAttributeVal(startElement.getAttributes(), "s");
        }
        lastContents = "";
      } else if(event.isCharacters()) {
        lastContents += event.asCharacters().getData();
      } else if(event.isEndElement()) {
        boolean strType = nextIsString || inlineStr;
        if(nextIsString) {
          int idx = Integer.parseInt(lastContents);
          lastContents = sst.getItemAt(idx).getString();
          nextIsString = false;
        }
        String name = event.asEndElement().getName().getLocalPart();
        if(Objects.equal("c", name)) {
          inlineStr = false;
          style = null;
        }
        if(Objects.equal("row", name)) {
          // 处理当前行，构造ExcelRowItem对象
          if(recordMap.isEmpty()) {
             continue;
          }
          // 标记当前已经拼接了一行数据
          dataExist = true;
          return;
        }
        if(inlineStr && "t".equals(name)) {
          if(lastContents == null) {
            continue;
          }
          lastContents = lastContents.trim();
          recordMap.put(curColumn + "", lastContents);
          inlineStr = false;
        }
        if(Objects.equal("v", name)) {
          if (style != null && !strType && StringUtils.isNotEmpty(lastContents)) {
            try {
              String origContent = lastContents;
              XSSFCellStyle cellStyle = stylesTable.getStyleAt(Integer.valueOf(style));
              lastContents = formatter
                  .formatRawCellContents(Double.valueOf(lastContents), cellStyle.getDataFormat(),
                      cellStyle.getDataFormatString());
              lastContents = lastContents.replaceAll(",", "");
              // fix 解决公式计算错误时界面显示-但后台取值为- 0的bug
              if(Objects.equal("0", origContent) && Objects.equal("- 0", lastContents)) {
                lastContents = "-";
              }
            } catch (Exception e) {
              ;
            }
          }
          if(lastContents == null) {
            continue;
          }
          lastContents = lastContents.trim();
          recordMap.put(curColumn + "", lastContents);
          inlineStr = false;
        }
      }
    }
    closeInputStream();
    curSheetEnd = true;
  }

  private String getAttributeVal(Iterator<Attribute> iterator, String attrName){
    while (iterator.hasNext()) {
      Attribute attribute = iterator.next();
      if(Objects.equal(attribute.getName().getLocalPart(), attrName)) {
        return attribute.getValue();
      }
    }
    return null;
  }

  /**
   * 从excel的单元格坐标中获取列坐标。（从A开始）
   *
   * @param str
   * @return
   * @author liuqiangm
   */
  private Integer getColumnIndex(String str) {
    int end = -1;
    for (int i = 0; i < str.length(); i++) {
      // 如果出现了数字，则说明该位置之前的字符串为列坐标，其后的字符串为行坐标
      if (Character.isDigit(str.charAt(i))) {
        end = i;
        break;
      }
    }
    String origColumnIndex = str.substring(0, end);
    return calculateColumnIndex(origColumnIndex);
  }

  /**
   * 用于计算excel列坐标的索引值。例如：A对应0，Z对应25。
   *
   * @param str
   * @return
   */
  private Integer calculateColumnIndex(String str) {
    if (column2IndexMap.containsKey(str)) {
      return column2IndexMap.get(str);
    }
    int columnIndex = 0;
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      if (!Character.isAlphabetic(ch)) {
        log.error("当前列坐标不是字母形式，请检查" + str);
      }
      columnIndex = columnIndex * 26 + (ch - 'A' + 1);
    }
    column2IndexMap.put(str, columnIndex - 1);
    return columnIndex - 1;
  }

}