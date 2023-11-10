package com.liang.customreport.job.random;

import com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgCustomQueryV1ReqBO;
import com.liang.customreport.mapstructs.JingdongAdsIbgCustomQueryV1ReqMappering;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author liangbingtian
 * @date 2023/11/03 下午2:29
 */
public class JingdongAdsIbgCustomQueryV1ReqFieldCombine extends
    AbstractRandomFieldCombine<JingdongAdsIbgCustomQueryV1ReqBO, Integer> {

  public JingdongAdsIbgCustomQueryV1ReqFieldCombine(
      JingdongAdsIbgCustomQueryV1ReqBO paramBO,
      List<BiConsumer<JingdongAdsIbgCustomQueryV1ReqBO, Integer>> randomSetList,
      List<Integer[]> paramValueList,
      int fieldCount,
      int valueCount) {
    super(paramBO, randomSetList, paramValueList, fieldCount, valueCount);
  }

  @Override
  protected JingdongAdsIbgCustomQueryV1ReqBO generateSameObject(
      JingdongAdsIbgCustomQueryV1ReqBO reqBO) {
    return JingdongAdsIbgCustomQueryV1ReqMappering.INSTANCE.copyOne(reqBO);
  }

}
