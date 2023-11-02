package com.liang.customreport.enums;


import com.google.common.base.Preconditions;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author dyy
 * @version 1.0
 * @date 2023/3/19 17:38
 * @desc
 */
@Getter
public enum JdApiEnum {
  /**
   * 查询京东快车计划列表信息和数据
   */
  CUSTOM_REPORT_QUERY("jingdong.ads.ibg.UniversalJosService.custom.query.v1",
      "自定义报表(全渠道业务线报表)",
      "com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgCustomQueryV1ReqBO",
      "com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgCustomQueryV1ResBO"),
  REPORT_DOWNLOAD_QUERY("jingdong.ads.ibg.UniversalJosService.download",
      "报表异步下载",
      "com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgDownloadReqBO",
      "com.liang.customreport.jdapicall.bo.customreport.JingdongAdsIbgDownloadResBO"),
  ;

  /**
   * String编码
   */
  private final String api;

  /**
   * 描述
   */
  private final String desc;

  /**
   * 请求对象
   */
  private final String req;

  /**
   * 返回对象
   */
  private final String res;

  JdApiEnum(String api, String desc, String req, String res) {
    this.api = api;
    this.desc = desc;
    this.req = req;
    this.res = res;
  }

  public static JdApiEnum getInstanceByApi(String api) {
    Preconditions.checkArgument(StringUtils.isNotBlank(api), "api类型不能为空");
    return Stream.of(JdApiEnum.values()).filter(jdApiEnum -> jdApiEnum.getApi().equals(api))
        .findAny().orElseThrow(() -> new RuntimeException("未找到api对应的枚举类"));
  }
}
