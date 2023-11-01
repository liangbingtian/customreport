package com.liang.customreport.jdapicall.bo.common;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author dyy
 * @version 1.0
 * @date 2023/3/15 16:06
 * @desc 京东2.0接口请求入参
 */
@Data
@Accessors(chain = true)
public class JdApiV2RequestBo<T> implements Serializable {
    @ApiModelProperty(value = "采用OAuth授权方式是必填参数，具体的获取查看页面：https://jos.jd.com/commondoc?listId=32", example = "")
    private String accessToken;
    @ApiModelProperty(value = "应用的app_key", example = "")
    private String appKey;
    @ApiModelProperty(value = "应用的app_secret", example = "")
    private String appSecret;
    @ApiModelProperty(value = "时间戳，格式为yyyy-MM-dd HH:mm:ss，例如：2019-05-01 00:00:00。京东API服务端允许客户端请求时间误差为10分钟", example = "")
    private String timestamp;
    @ApiModelProperty(value = "要将应用级参数作为一个整体对象以json的形式拼接传递", example = "")
    private T param;
    @ApiModelProperty(value = "API协议版本，参考接口文档版本", example = "")
    private String v = "2.0";
}
