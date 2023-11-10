package com.liang.customreport.job.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author liangbingtian
 * @date 2023/11/03 下午2:18 如果需要有排列组合传参的，进行参数的排列组合传递
 */
public abstract class AbstractRandomFieldCombine<T, C> {

  private final T reqBO;

  private final List<T> result = new ArrayList<>();

  private final List<BiConsumer<T, C>> randomSetList;

  private final List<C[]> paramValueList;

  private final int fieldCount;

  private final int valueCount;

  public AbstractRandomFieldCombine(T reqBO,
      List<BiConsumer<T, C>> randomSetList, List<C[]> paramValueList, int fieldCount,
      int valueCount) {
    this.reqBO = reqBO;
    this.randomSetList = randomSetList;
    this.paramValueList = paramValueList;
    this.fieldCount = fieldCount;
    this.valueCount = valueCount;
  }

  /**
   * 根据字段的个数以及字段值的个数随机出不同的字段排列组合，然后设置对象
   *
   */
  public void randomFieldCombine() {
    int totalCombinations = (int) Math.pow(valueCount, fieldCount);
    for (int i = 0; i < totalCombinations; i++) {
      int j=0;
      for (BiConsumer<T, C> consumer: randomSetList) {
        int value = (i >> j) & 1;
        // 根据每位的值从不同的数组中查询元素
        processValue(consumer, paramValueList.get(j)[value]);
        j++;
      }
      //然后生成一个新的对象!!
      T t = generateSameObject(reqBO);
      result.add(t);
    }
  }

  /**
   * 使用mapstruct生成一个一样的对象
   * @param reqBO
   * @return
   */
  protected abstract T generateSameObject(T reqBO);

  /**
   * 设置具体的字段
   *
   */
 private void processValue(BiConsumer<T, C> consumer, C value){
   consumer.accept(reqBO, value);
 }

  public List<T> getResult() {
    return result;
  }
}
