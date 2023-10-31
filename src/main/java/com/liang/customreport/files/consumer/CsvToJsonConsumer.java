package com.liang.customreport.files.consumer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liangbingtian
 * @date 2023/11/01 上午12:13
 */
public class CsvToJsonConsumer {

  private List<Object> list = new ArrayList<>(1000);

  private final String targetPath;

  public CsvToJsonConsumer(String targetPath) {
    this.targetPath = targetPath;
  }

  public void add(Object object) {
    if (list.size()==1000) {
      save();
    }
    list.add(object);
  }

  private void save() {
    //todo 导出json文件

    list.clear();
  }

}
