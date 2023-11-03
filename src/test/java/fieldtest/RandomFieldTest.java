package fieldtest;

import org.junit.Test;

/**
 * @author liangbingtian
 * @date 2023/11/03 下午1:43
 */
public class RandomFieldTest {

  @Test
  public void randomEightField(int[] ... a) {
    int fieldCount = 3;
    int valueCount = 2;
    int totalCombinations = (int) Math.pow(valueCount, fieldCount);

    // 三个不同的数组，分别代表不同的数据
    int[] dataArray1 = {1, 2, 3, 4};
    int[] dataArray2 = {10, 20, 30, 40};
    int[] dataArray3 = {100, 200, 300, 400};

    for (int i = 0; i < totalCombinations; i++) {
      int combination = i;

      for (int j = 0; j < fieldCount; j++) {
        int value = (combination >> j) & 1;

        // 根据每位的值从不同的数组中查询元素
        int element;
        if (j == 0) {
          element = dataArray1[value];
        } else if (j == 1) {
          element = dataArray2[value];
        } else {
          element = dataArray3[value];
        }

        System.out.println("Combination: " + i + " - Field " + j + ": " + element);
      }
    }
  }

}
