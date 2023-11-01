package com.liang.customreport.jdapicall.bo.customreport;
import com.liang.customreport.jdapicall.bo.common.JdApiV2ResultBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liangbingtian
 * @date 2023/11/01 上午9:46
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "", description = "自定义报表(全渠道业务线)查询")
public class JingdongAdsIbgCustomQueryV1ResBO implements Serializable {

  @ApiModelProperty(value = "相应", example = "")
  private JingdongAdsIbgCustomQueryV1Response jingdong_ads_ibg_UniversalJosService_custom_query_v1_responce;

  @Data
  public static class JingdongAdsIbgCustomQueryV1Response {

    @ApiModelProperty(value = "响应", example = "00000")
    private JdApiV2ResultBo<DataBO> returnType;
  }

  @Data
  public static class DataBO {

    @ApiModelProperty(value = "异步下载的id")
    private Integer downloadId;
  }

}
