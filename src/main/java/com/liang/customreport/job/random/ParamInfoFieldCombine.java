package com.liang.customreport.job.random;

import com.liang.customreport.enums.JdApiEnum;
import com.liang.customreport.jdapicall.bo.ParamInfo;
import com.liang.customreport.mapstructs.ParamInfoMappering;

/**
 * @author liangbingtian
 * @date 2023/11/04 下午12:48
 */
public class ParamInfoFieldCombine extends AbstractRandomFieldCombine<ParamInfo, String> {

  public ParamInfoFieldCombine(ParamInfo info) {
    this.reqBO = info;
  }

  public void randomFieldCombine() {
    super.randomFieldCombine(1, 2, new String[]{JdApiEnum.CUSTOM_REPORT_QUERY.getApi(),
        JdApiEnum.REPORT_DOWNLOAD_QUERY.getApi()});
  }

  @Override
  protected ParamInfo generateSameObject(ParamInfo reqBO) {
    return ParamInfoMappering.INSTANCE.copyOne(reqBO);
  }

  @Override
  protected void processValue(int j, String s) {
    reqBO.setApi(s);
  }
}
