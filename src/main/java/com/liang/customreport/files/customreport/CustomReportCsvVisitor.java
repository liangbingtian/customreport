package com.liang.customreport.files.customreport;

import com.alibaba.fastjson.JSONObject;
import com.liang.customreport.bo.ReportInfoBO;
import com.liang.customreport.common.Constants;
import com.liang.customreport.files.ResolveCsvVisitor;
import java.util.Map;

/**
 * @author liangbingtian
 * @date 2023/11/06 上午11:02
 */
public class CustomReportCsvVisitor extends ResolveCsvVisitor<ReportInfoBO> {

  public CustomReportCsvVisitor(String targetPath, ReportInfoBO info, String[] excelHeaders,
      Map<String, String> excelHeadersMapping) {
    super(targetPath, info, excelHeaders, excelHeadersMapping);
  }

  @Override
  protected void processBaseInfo(JSONObject object, ReportInfoBO reportInfoBO) {
    object.put(Constants.USER_NAME, reportInfoBO.getUsername());
    object.put(Constants.START_DATE, reportInfoBO.getStartDate());
    object.put(Constants.END_DATE, reportInfoBO.getEndDate());
    object.put(Constants.TRANS_DAYS, reportInfoBO.getClickOrOrderDay());
    object.put(Constants.CALIBER, reportInfoBO.getClickOrOrderCaliber());
    object.put(Constants.GIFT_FLAG, reportInfoBO.getGiftFlag());
    object.put(Constants.ORDER_STATUS, reportInfoBO.getOrderStatusCategory());
    object.put(Constants.EFFECT, 1);
    object.put(Constants.IS_DAILY, 1);
  }
}
