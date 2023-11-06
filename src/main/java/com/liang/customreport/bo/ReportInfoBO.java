package com.liang.customreport.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangbingtian
 * @date 2023/11/06 上午10:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportInfoBO {

  private String username;

  private String startDate;

  private String endDate;

  @ApiModelProperty(value = "转化周期，当天：0，1天：1，3天：3，7天：7，15天: 15，30天：30", example = "0", required = true)
  private Integer clickOrOrderDay;

  @ApiModelProperty(value = "转化类型，点击口径：0，订单口径：1", example = "0", required = true)
  private Integer clickOrOrderCaliber;

  @ApiModelProperty(value = "订单状态，下单订单：null(不填), 成交订单：1", example = "1")
  private Integer orderStatusCategory;

  @ApiModelProperty(value = "赠品筛选，不含赠品：0；含赠品：null（不传）", example = "0")
  private Integer giftFlag;

}
