package com.liang.customreport.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;

/**
 * 2019/11/27 下午11:11
 *
 * @author liangbingtian
 */
public abstract class ExcelConsumer<T> implements IExcelProcessor{

  private final int batchSize = 1000;
  private List<T> objectList = new ArrayList<>(batchSize);

  @Override
  public void processRecord(String sheetName, int sheetIndex, int rowIndex,
      Map<String, Object> recordMap) {
    T object = getTargetObject(rowIndex, recordMap);
    if (object == null){
      return;
    }
    objectList.add(object);
    if (objectList.size() == batchSize) {
      saveBatch(objectList);
      objectList.clear();
    }

  }

  public void end() {
    if (CollectionUtils.isNotEmpty(objectList)) {
      saveBatch(objectList);
      objectList = null;
    }
  }

  protected abstract void saveBatch(List<T> objectList);

  protected abstract T getTargetObject(int rowIndex, Map<String, Object> recordMap);

  protected abstract void initAction();

}
