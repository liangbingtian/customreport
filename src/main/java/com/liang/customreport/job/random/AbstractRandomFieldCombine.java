package com.liang.customreport.job.random;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liangbingtian
 * @date 2023/11/03 下午2:18 如果需要有排列组合传参的，进行参数的排列组合传递
 */
public abstract class AbstractRandomFieldCombine<T> {


  protected T reqBO;

  protected final List<T> result = new ArrayList<>();

  /**
   * 根据字段的个数以及字段值的个数随机出不同的字段排列组合，然后设置对象
   *
   * @param fieldCount 字段个数
   * @param valueCount 字段值的个数
   */
  public void randomFieldCombine(int fieldCount, int valueCount, Integer[]... arrays) {
    int totalCombinations = (int) Math.pow(valueCount, fieldCount);
    for (int i = 0; i < totalCombinations; i++) {
      for (int j = 0; j < fieldCount; j++) {
        int value = (i >> j) & 1;
        // 根据每位的值从不同的数组中查询元素
        processValue(j, arrays[j][value]);
      }
      //然后生成一个新的对象!!
      T t = generateSameObject(reqBO);
      result.add(t);
    }
  }

  /**
   * 使用mapstruct生成一个一样的对象，潜拷贝
   *
   * @param reqBO
   * @return
   */
  protected abstract T generateSameObject(T reqBO);

  /**
   * 设置具体的字段
   *
   * @param j 具体需要设置的字段的索引
   * @param s 具体要设置的值
   */
  protected abstract void processValue(int j, Integer s);

  public List<T> getResult() {
    return result;
  }
}
