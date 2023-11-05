package com.liang.customreport.jdapicall.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangbingtian
 * @date 2023/11/01 上午11:13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParamInfo {

  private String appSecret;

  private String accessToken;

  private String appKey;

  private String api;

  private String username;


}
