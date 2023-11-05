package com.liang.customreport.mapstructs;

import com.liang.customreport.jdapicall.bo.ParamInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author liangbingtian
 * @date 2023/11/04 下午12:56
 */
@Mapper
public interface ParamInfoMappering {

  ParamInfoMappering INSTANCE = Mappers.getMapper(ParamInfoMappering.class);

  /**
   * 单纯复制一个对象
   * @param info
   * @return
   */
  ParamInfo copyOne(ParamInfo info);

}
