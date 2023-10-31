package com.liang.customreport.excel.handler;

import com.google.common.base.Objects;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 每一个Sheet，都会执行该handler
 *
 * @author liuqiangm
 */
@Slf4j
class XSSFSheetHandler extends DefaultHandler {

  /**
   * 用于存储excel中的列索引（A~Z...）等翻译过来的int值。
   */
  private static final Map<String, Integer> column2IndexMap = new ConcurrentHashMap<>();

  XSSFHandler excelHandler;
  private SharedStringsTable sst;
  private StylesTable stylesTable;
  private String lastContents;
  private boolean nextIsString;
  private final DataFormatter formatter = new DataFormatter();

  private boolean inlineStr = false;
  private String style = null;

  public XSSFSheetHandler(SharedStringsTable sst, StylesTable stylesTable,
      XSSFHandler excelHandler) {
    this.sst = sst;
    this.stylesTable = stylesTable;
    this.excelHandler = excelHandler;
  }

  @Override
  public void startElement(String uri, String localName, String name,
      Attributes attributes) throws SAXException {
    if ("row".equals(name)) {
      // 获取当前行号（从1开始）
      excelHandler.setCurRow(Integer.valueOf(attributes.getValue("r")) - 1);
      return;
    }
    if ("c".equals(name)) {
      String cellType = attributes.getValue("t");
      // 内联字符串
      if ("inlineStr".equals(cellType)) {
        inlineStr = true;
      }
      // 设置当前列索引
      excelHandler.setCurColumn(getColumnIndex(attributes.getValue("r")));
      // 通过常量池获取字符串
      if (cellType != null && "s".equals(cellType)) {
        nextIsString = true;
      } else {
        nextIsString = false;
      }
      style = attributes.getValue("s");
    }
    lastContents = "";
  }

  @Override
  public void endElement(String uri, String localName, String name)
      throws SAXException {
    boolean strType = nextIsString || inlineStr;
    // 通过常量池获取字符串
    if (nextIsString) {
      int idx = Integer.parseInt(lastContents);
      lastContents = sst.getItemAt(idx).getString();
      nextIsString = false;
    }
    // 行遍历结束
    if ("c".equals(name)) {
      inlineStr = false;
      style = null;
    }
    if("row".equals(name)) {
      excelHandler.processRowRecord();
    }
    if (inlineStr && "t".equals(name)) {
      excelHandler.addValue(lastContents);
      inlineStr = false;
    }
    if ("v".equals(name)) {
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
      excelHandler.addValue(lastContents);
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) {
    lastContents += new String(ch, start, length);
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
        log.error("当前列坐标不是字母形式，请检查,{}", str);
      }
      columnIndex = columnIndex * 26 + (ch - 'A' + 1);
    }
    column2IndexMap.put(str, columnIndex - 1);
    return columnIndex - 1;
  }
}