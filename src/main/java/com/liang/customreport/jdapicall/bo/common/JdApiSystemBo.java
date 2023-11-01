package com.liang.customreport.jdapicall.bo.common;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName:JdApiBaseBo
 * @Description 京东接口从2.0开始,业务接口都需要传这两个参数,使用token模式
 * 调用默认accessPin="" & authType="0"
 * @Author:qzm
 * @Date:2022/9/30
 */
@Data
@Accessors(chain = true)
public class JdApiSystemBo implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "被免密访问的pin", example = "")
    private String accessPin = "";
    @ApiModelProperty(value = "授权模式", example = "0")
    private String authType = "0";
    @ApiModelProperty(value = "平台业务类型，DST_JZT：京准通", example = "0")
    private String platformBusinessType = "DST_JZT";
}
