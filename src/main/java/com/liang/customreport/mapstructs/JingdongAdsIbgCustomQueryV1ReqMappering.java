package com.liang.customreport.mapstructs;

import com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgCustomQueryV1ReqBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author liangbingtian
 * @date 2023/11/02 下午5:19
 */
@Mapper
public interface JingdongAdsIbgCustomQueryV1ReqMappering {

  JingdongAdsIbgCustomQueryV1ReqMappering INSTANCE = Mappers.getMapper(JingdongAdsIbgCustomQueryV1ReqMappering.class);

  /**
   * 仅仅将对象进行整体复制
   * @param bo
   * @return
   */
  JingdongAdsIbgCustomQueryV1ReqBO copyOne(JingdongAdsIbgCustomQueryV1ReqBO bo);
}
