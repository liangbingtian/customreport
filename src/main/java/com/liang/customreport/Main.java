package com.liang.customreport;


import com.liang.customreport.job.CustomReportDataJob;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liangbingtian
 * @date 2023/10/31 下午4:33
 */
@Slf4j
public class Main {

  public static void main(String[] args) {
    CustomReportDataJob dataJob = new CustomReportDataJob();
    dataJob.runMainJob();
  }



}
