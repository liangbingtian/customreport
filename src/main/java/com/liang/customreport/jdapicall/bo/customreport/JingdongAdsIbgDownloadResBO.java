package com.liang.customreport.jdapicall.bo.customreport;

import com.alibaba.fastjson.annotation.JSONField;
import com.liang.customreport.jdapicall.bo.common.JdApiV2ResultBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liangbingtian
 * @date 2023/11/02 下午5:39
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "", description = "报表异步下载返回结果")
public class JingdongAdsIbgDownloadResBO implements Serializable {

  @ApiModelProperty(value = "响应", example = "")
  @JSONField(name = "jingdong_ads_ibg_UniversalJosService_download_responce")
  private JingdongAdsIbgDownloadResponse response;

  @Data
  public static class JingdongAdsIbgDownloadResponse {

    @ApiModelProperty(value = "响应", example = "00000")
    private JdApiV2ResultBo<JingdongAdsIbgDownloadResBO.DataBO> returnType;
  }

  @Data
  public static class DataBO {

    @ApiModelProperty(value = "异步下载的id")
    private Integer id;

    @ApiModelProperty(value = "下载链接")
    private String downloadUrl;

    @ApiModelProperty(value = "成功标识，1-报表生成中，2-报表生成成功")
    private Integer status;
  }
}
