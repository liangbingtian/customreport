package com.liang.customreport.jdapicall.bo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dyy
 * @version 1.0
 * @date 2023/3/19 18:05
 * @desc
 */
@Data
public class JdApiV2ParamExt {
    @ApiModelProperty(value = "用户pin", example = "")
    private String pin;

    @ApiModelProperty(value = "venderId", example = "")
    private String venderId;

    @ApiModelProperty(value = "业务来源", example = "")
    private String requestFrom;

    @ApiModelProperty(value = "appKey", example = "")
    private String appKey;

    @ApiModelProperty(value = "traceId", example = "")
    private String traceId;
}
