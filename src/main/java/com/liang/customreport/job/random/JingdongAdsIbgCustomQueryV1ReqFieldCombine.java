package com.liang.customreport.job.random;

import com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgCustomQueryV1ReqBO;
import com.liang.customreport.mapstructs.JingdongAdsIbgCustomQueryV1ReqMappering;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liangbingtian
 * @date 2023/11/03 下午2:29
 */
public class JingdongAdsIbgCustomQueryV1ReqFieldCombine extends AbstractRandomFieldCombine<JingdongAdsIbgCustomQueryV1ReqBO, Integer> {

  public JingdongAdsIbgCustomQueryV1ReqFieldCombine(JingdongAdsIbgCustomQueryV1ReqBO paramBO) {
    this.reqBO = paramBO;
  }

  public void randomFieldCombine() {
    super.randomFieldCombine(3, 2, new Integer[]{0, 15}, new Integer[]{null, 1}, new Integer[]{0, 1});
  }

  @Override
  protected JingdongAdsIbgCustomQueryV1ReqBO generateSameObject(
      JingdongAdsIbgCustomQueryV1ReqBO reqBO) {
    return JingdongAdsIbgCustomQueryV1ReqMappering.INSTANCE.copyOne(reqBO);
  }

  @Override
  protected void processValue(int j, Integer s) {
    switch (j) {
      case 0:
        reqBO.setClickOrOrderDay(s);
        break;
      case 1:
        reqBO.setOrderStatusCategory(s);
        break;
      case 2:
        reqBO.setClickOrOrderCaliber(s);
        break;
      default:
    }
  }
}
