package com.liang.customreport.jdapicall.bo.common;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName:JdApiBaseBo
 * @Description 京东接口从2.0开始, 业务接口都需要传这两个参数, 使用token模式
 * 调用默认accessPin="" & authType="0"
 * @Author:qzm
 * @Date:2022/9/30
 */
@Data
@Accessors(chain = true)
public class JdApiV2ResultBo<T> implements Serializable {
    @ApiModelProperty(value = "响应描述", example = "")
    private String msg;
    @ApiModelProperty(value = "响应码", example = "")
    private Long code;
    @ApiModelProperty(value = "请求是否成功", example = "")
    private Boolean success;
    @ApiModelProperty(value = "业务结果", example = "")
    private T data;
    @ApiModelProperty(value = "系统参数", example = "")
    private JdApiV2ParamExt system;
}
