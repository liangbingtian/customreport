package com.liang.customreport.response;

/**
 * CommonResponse 泛型版本接口
 */
public interface ICommonResponse2<T> extends ICommonResponse {

  T getDatas();

  void setDatas(T datas);
}
