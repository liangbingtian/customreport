package com.liang.customreport.job.random;

import com.liang.customreport.jdapicall.bo.ParamInfo;
import com.liang.customreport.mapstructs.ParamInfoMappering;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author liangbingtian
 * @date 2023/11/04 下午12:48
 */
public class ParamInfoFieldCombine extends AbstractRandomFieldCombine<ParamInfo, String> {

  public ParamInfoFieldCombine(ParamInfo reqBO,
      List<BiConsumer<ParamInfo, String>> randomSetList,
      List<String[]> paramValueList, int fieldCount, int valueCount) {
    super(reqBO, randomSetList, paramValueList, fieldCount, valueCount);
  }


  @Override
  protected ParamInfo generateSameObject(ParamInfo reqBO) {
    return ParamInfoMappering.INSTANCE.copyOne(reqBO);
  }
}
