package fieldtest;

import com.liang.customreport.enums.JdApiEnum;
import com.liang.customreport.jdapicall.bo.ParamInfo;
import com.liang.customreport.job.random.ParamInfoFieldCombine;
import java.util.List;
import org.junit.Test;

/**
 * @author liangbingtian
 * @date 2023/11/03 下午1:43
 */
public class RandomFieldTest {

  @Test
  public void randomThreeFieldsTwoValues() {
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

  @Test
  public void randomThreeFieldsThreeValues() {
    int numberOfFields = 3;
    int bitsPerField = 2; // 两个二进制位可以表示 0, 1, 2, 3 四种不同的组合

    int combinations = (int) Math.pow(2, numberOfFields * bitsPerField);

    for (int i = 0; i < combinations; i++) {
      for (int j = 0; j < numberOfFields; j++) {
        // 计算每个字段的起始位和结束位
        int startBit = j * bitsPerField;
        int endBit = (j + 1) * bitsPerField;

        // 从 i 中提取当前字段的二进制表示
        int fieldValue = (i >> startBit) & ((1 << bitsPerField) - 1);
        System.out.print(fieldValue + " "); // 输出当前字段的取值
      }
      System.out.println();
    }
  }


  @Test
  public void test2() {
    final ParamInfo info = ParamInfo.builder()
        .accessToken("1fb2353257524bf88d3a067195bdba82mzi1")
        .appKey("A1D3C721A3E382FF4915BE266B4294F6")
        .appSecret("8d08db8de0ec468ebe234dcfdc1c3dca")
        .api(JdApiEnum.CUSTOM_REPORT_QUERY.getApi())
        .build();
    ParamInfoFieldCombine combine = new ParamInfoFieldCombine(info);
    combine.randomFieldCombine();
    final List<ParamInfo> result = combine.getResult();
    System.out.println();
  }

}
