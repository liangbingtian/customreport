package com.liang.customreport.jdapicall.bo.customreport;

import com.alibaba.fastjson.annotation.JSONField;
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
public class JingdongAdsIbgCustomQueryV1ReqBO implements Serializable {

  @ApiModelProperty(value = "被免密访问的pin", example = "")
  private String accessPin = "";

  @ApiModelProperty(value = "授权模式", example = "0")
  private String authType = "0";

  @ApiModelProperty(value = "平台业务类型，DST_JZT：京准通", example = "0")
  private String platformBusinessType = "DST_JZT";

  @ApiModelProperty(value = "开始时间 格式：yyyy-MM-dd", example = "2023-07-01", required = true)
  private String startDay;

  @ApiModelProperty(value = "结束时间 格式：yyyy-MM-dd，最大时间窗口为31天，即结束时间-开始时间小于等于31", example = "2023-07-01", required = true)
  private String endDay;

  @ApiModelProperty(value = "转化周期，当天：0，1天：1，3天：3，7天：7，15天: 15，30天：30", example = "0", required = true)
  private Integer clickOrOrderDay;

  @ApiModelProperty(value = "转化类型，点击口径：0，订单口径：1", example = "0", required = true)
  private Integer clickOrOrderCaliber;

  @ApiModelProperty(value = "订单状态，全部订单：null(不填), 成交订单：1", example = "1")
  private Integer orderStatusCategory;

  @ApiModelProperty(value = "赠品筛选，不含赠品：0；含赠品：null（不传）", example = "0")
  private Integer giftFlag;

  @ApiModelProperty(value = "聚合粒度 0：全部 1：天 2：周 3：月", example = "0", required = true)
  private Integer granularity;

  @ApiModelProperty(value = "页码 最大为50（超过50后会被拦截，建议通过下载方式获取数据）", example = "1", required = true)
  private Integer page;

  @ApiModelProperty(value = "页面大小，最小值为100，最大值为1000（page*pageSize 大于10000时，请求会被拦截，建议通过下载的方式获取数据）", example = "1000", required = true)
  private Integer pageSize;

  @ApiModelProperty(value = "查询pin列表，请传入已授权的pin", example = "test1,test2")
  private String pins;

  @ApiModelProperty(value = "支持的维度参考：京准通-API官网。 多个维度之间以逗号分隔")
  private String dimensions;

  @ApiModelProperty(value = "支持的指标参考：京准通-API官网。 多个指标之间以逗号分隔")
  private String normalMetrics;

  @ApiModelProperty(value = "支持的指标参考：京准通-API官网。多个指标之间以逗号分隔")
  private String orderDetailMetrics;

  @ApiModelProperty(value = "自定义筛选条件，详细参考京准通-API官网")
  private String customFilters;

  @ApiModelProperty(value = "自定义排序，详细参考京准通-API官网")
  private String customOrders;

  @ApiModelProperty(value = "是否下载", required = true)
  @JSONField(name = "isDownload")
  private Boolean download;

}
