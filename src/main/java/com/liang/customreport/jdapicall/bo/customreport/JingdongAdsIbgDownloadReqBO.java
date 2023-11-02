package com.liang.customreport.jdapicall.bo.customreport;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liangbingtian
 * @date 2023/11/02 下午5:32
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "", description = "报表异步下载查询")
public class JingdongAdsIbgDownloadReqBO implements Serializable {

  @ApiModelProperty(value = "被免密访问的pin", example = "")
  private String accessPin = "";

  @ApiModelProperty(value = "授权模式", example = "0")
  private String authType = "0";

  @ApiModelProperty(value = "平台业务类型，DST_JZT：京准通", example = "0")
  private String platformBusinessType = "DST_JZT";

  @ApiModelProperty(value = "文件下载id")
  private Integer downloadId;

}
