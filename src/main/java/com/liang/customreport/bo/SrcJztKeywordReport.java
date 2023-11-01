package com.liang.customreport.bo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangbingtian
 * @date 2023/11/01 下午5:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SrcJztKeywordReport {

  private String username;

  @JSONField(name = "start_date")
  private String startDate;

  @JSONField(name = "end_date")
  private String endDate;

  private String period;

  @JSONField(name = "trans_days")
  private Integer transDays;

  private Integer caliber;

  @JSONField(name = "gift_flag")
  private String giftFlag;

  @JSONField(name = "date_str")
  private String dateStr;

  @JSONField(name = "order_status")
  private Integer orderStatus;

  @ApiModelProperty(value = "效果，0-展现效果，1-点击效果")
  private Integer effect;

  @ApiModelProperty(value = "分日报告 0-不分日，1-分日")
  @JSONField(name = "is_daily")
  private Integer isDaily;

  private String pin;

  private String business;

  @JSONField(name = "campaign_type")
  private String campaignType;

  @JSONField(name = "kw_id")
  private String kwId;

  private String keyword;

  @JSONField(name = "campaign_id")
  private String campaignId;

  @JSONField(name = "campaign_name")
  private String campaignName;

  @JSONField(name = "group_id")
  private String groupId;

  @JSONField(name = "group_name")
  private String groupName;

  @JSONField(name = "ad_id")
  private String adId;

  @JSONField(name = "ad_name")
  private String adName;

  @JSONField(name = "sku_brand_id")
  private String skuBrandId;

  @JSONField(name = "sku_brand_name")
  private String skuBrandName;

  private Integer impressions;

  private Integer clicks;

  @JSONField(name = "all_cost")
  private BigDecimal allCost;

  @JSONField(name = "dir_ord_cnt")
  private Integer dirOrdCnt;

  @JSONField(name = "dir_ord_sum")
  private BigDecimal dirOrdSum;

  @JSONField(name = "ind_ord_cnt")
  private Integer indOrdCnt;

  @JSONField(name = "ind_ord_sum")
  private BigDecimal indOrdSum;

  @JSONField(name = "tot_ord_cnt")
  private Integer totOrdCnt;

  @JSONField(name = "tot_ord_sum")
  private BigDecimal totOrdSum;

  @JSONField(name = "dir_cart_cnt")
  private Integer dirCartCnt;

  @JSONField(name = "ind_cart_cnt")
  private Integer indCartCnt;

  @JSONField(name = "tot_cart_cnt")
  private Integer totCartCnt;

  @JSONField(name = "new_customer")
  private Integer newCustomer;

  @JSONField(name = "search_term")
  @ApiModelProperty(name = "搜索词")
  private String searchTerm;

}
