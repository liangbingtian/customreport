package com.liang.customreport.excel.iterator;

import com.google.common.base.Objects;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * 迭代器模式实现针对超大xml文件进行遍历
 * @author liuqiangm
 * @create 2020/12/24 16:54
 */
@Slf4j
public class XmlIterator implements Iterator<Map<String, String>>, Closeable {

  private InputStream inputStream;

  /**
   * 默认的xml文件编码格式
   */
  private String encoding = "UTF-8";

  private XMLEventReader reader;

  private Stack<String> pathStack = new Stack<>();

  private Stack<String> nameStack = new Stack<>();

  /**
   * 判断当前的子标签内容是否已经匹配
   */
  private boolean match = false;

  /**
   * 某一次遍历中所获取到的数据
   */
  private Map<String, String> dataMap;

  /**
   * 目标path
   */
  private String targetpath;

  /**
   * 标记是否仅仅遍历当前targetpath对应的标签节点（不包含子节点）
   * 默认为false，要包含子节点
   */
  private boolean iterCurTag = false;

  public XmlIterator(InputStream inputStream, String targetpath, String encoding) {
    this(inputStream, targetpath, encoding, false);
  }

  public XmlIterator(InputStream inputStream, String targetpath, String encoding, boolean iterCurTag) {
    this.inputStream = inputStream;
    this.targetpath = targetpath;
    if(encoding != null) {
      this.encoding = encoding;
    }
    this.targetpath = targetpath;
    init();
    this.iterCurTag = iterCurTag;
  }

  public XmlIterator(File file, String targetpath, String encoding) {
    this(file, targetpath, encoding, false);
  }

  public XmlIterator(File file, String targetpath, String encoding, boolean iterCurTag) {
    try {
      this.inputStream = Files.newInputStream(file.toPath());
    } catch (IOException e) {
      log.error("", e);
      throw new RuntimeException(e.getMessage());
    }
    checkPath(targetpath);
    this.targetpath = targetpath;
    if(encoding != null) {
      this.encoding = encoding;
    }
    init();
    this.iterCurTag = iterCurTag;
  }

  public XmlIterator(MultipartFile multipartFile, String targetpath, String encoding) {
    this(multipartFile, targetpath, encoding, false);
  }

  public XmlIterator(MultipartFile multipartFile, String targetpath, String encoding, boolean iterCurTag) {
    try {
      inputStream = multipartFile.getInputStream();
    } catch (IOException e) {
      log.error("", e);
      throw new RuntimeException(e.getMessage());
    }
    checkPath(targetpath);
    this.targetpath = targetpath;
    if(encoding != null) {
      this.encoding = encoding;
    }
    init();
    this.iterCurTag = iterCurTag;
  }

  @Override
  public boolean hasNext() {
    // inputStream已经关闭
    if(inputStream == null) {
      return false;
    }
    // 进行一次遍历
    iter();
    // 遍历结束之后并未获取到数据，则说明已经遍历结束
    if(dataMap == null) {
      close();
      return false;
    }
    // 遍历中得到了目标数据
    return true;
  }

  @Override
  public Map<String, String> next() {
    Map<String, String> result = dataMap;
    dataMap = null;
    return result;
  }

  private void init() {
    XMLInputFactory factory = XMLInputFactory.newInstance();
    try {
      reader = factory.createXMLEventReader(inputStream);
    } catch (XMLStreamException e) {
      log.error("", e);
    }
  }

  /**
   * 进行一次遍历
   */
  private void iter() {
    while (reader.hasNext()) {
      XMLEvent event = null;
      try {
        event = reader.nextEvent();
      } catch (XMLStreamException e) {
        log.error("", e);
        throw new RuntimeException(e.getMessage());
      }
      // 起始标签
      if(event.isStartElement()) {
        StartElement startElement = event.asStartElement();
        String name = startElement.getName().getLocalPart();
        String curpath = pathStack.isEmpty()? name : pathStack.peek() + "|" + name;
        if(Objects.equal(targetpath, curpath)) {
          match = true;
          dataMap = new LinkedHashMap<>();
        }
        if(match) {
          if(iterCurTag) {
            if(Objects.equal(targetpath, curpath)) {
              // 添加标签内属性
              Iterator<Attribute> iterator = startElement.getAttributes();
              while (iterator.hasNext()) {
                Attribute attribute = iterator.next();
                dataMap.put(name + "@" + attribute.getName(), attribute.getValue());
              }
            }
          } else {
            // 添加标签内属性
            Iterator<Attribute> iterator = startElement.getAttributes();
            while (iterator.hasNext()) {
              Attribute attribute = iterator.next();
              dataMap.put(name + "@" + attribute.getName(), attribute.getValue());
            }
          }
        }
        // 当前路径
        pathStack.push(curpath);
        // 当前标签
        nameStack.push(name);
      } else if(event.isCharacters()) { // 标签内值
        if(match) {
          if(iterCurTag) {
            if(Objects.equal(targetpath, pathStack.peek())) {
              String data = event.asCharacters().getData().trim();
              if (data == null || "".equals(data)) {
                continue;
              }
              dataMap.put(nameStack.peek(), data);
            }
          } else {
            String data = event.asCharacters().getData().trim();
            if(data == null || "".equals(data)) {
              continue;
            }
            dataMap.put(nameStack.peek(), data);
          }
        }
      } else if(event.isEndElement()) { // 终止标签
        String peekpath = pathStack.peek();
        pathStack.pop();
        nameStack.pop();
        if(match && Objects.equal(targetpath, peekpath)) {
          match = false;
          break;
        }
      }
    }
  }

  private void checkPath(String targetpath) {
    if(targetpath == null) {
      throw new RuntimeException("目标路径不能为空");
    }
  }

  @Override
  public void close() {
    try {
      reader.close();
    } catch (XMLStreamException e) {
      log.error("", e);
    } finally {
      reader = null;
    }
    try {
      inputStream.close();
    } catch (IOException e) {
      log.error("", e);
    } finally {
      inputStream = null;
    }
  }

}
