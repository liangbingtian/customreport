package com.liang.customreport.jdapicall.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
 * @author dyy
 * @version 1.0
 * @date 2022/10/5 13:41
 * @desc
 */
@Data
@ApiModel(value = "JdShopAuthorizeInfo对象", description = "账号授权信息")
public class JdShopAuthorizeInfoPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "账户名")
    private String username;

    @ApiModelProperty(value = "accessToken")
    private String accessToken;

    @ApiModelProperty(value = "appkey")
    private String appkey;

    @ApiModelProperty(value = "appSecret")
    private String appSecret;

    @ApiModelProperty(value = "是否开启")
    private Boolean tfOpen;

    @ApiModelProperty(value = "创建时间戳")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @ApiModelProperty(value = "更新时间戳")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;
}
