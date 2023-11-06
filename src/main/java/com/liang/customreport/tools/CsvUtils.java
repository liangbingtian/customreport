package com.liang.customreport.tools;

import com.liang.customreport.bo.ReportInfoBO;
import com.liang.customreport.common.Constants;
import com.liang.customreport.files.customreport.CustomReportCsvVisitor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liangbingtian
 * @date 2023/11/01 下午5:29
 */
@Slf4j
public class CsvUtils {

  public static void processCsvToJson(String source, String target) throws IOException {
    final ReportInfoBO infoBO = ReportInfoBO.builder()
        .username("123")
        .startDate("2023-10-01")
        .endDate("2023-10-31")
        .clickOrOrderCaliber(0)
        .clickOrOrderDay(15)
        .orderStatusCategory(null)
        .giftFlag(1).build();
    CustomReportCsvVisitor visitor = new CustomReportCsvVisitor(null, infoBO, Constants.CSV_HEADER1,
        Constants.CSV_HEADER_MAP1);
    Files.walkFileTree(Paths.get(source), visitor);
    log.info("目录:{},里的csv文件转换为json文件完毕", source);
  }

}
