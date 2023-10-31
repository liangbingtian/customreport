/**
 *
 */
package com.liang.customreport.response;

/**
 * 泛型版本的Response，方便JSON转换使用
 *
 * @author wangweir
 */
public class CommonResponse2<T> implements ICommonResponse2<T> {

  private String code;

  private String msg;

  private T datas;

  /**
   *
   */
  public CommonResponse2() {
    this(null, null, null);
  }

  public CommonResponse2(String code, String msg) {
    this(code, msg, null);
  }

  public CommonResponse2(String code, String msg, T datas) {
    this.code = code;
    this.msg = msg;
    this.datas = datas;
  }

  @Override
  public String getMsg() {
    return msg;
  }

  @Override
  public void setMsg(String msg) {
    this.msg = msg;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * @return the datas
   */
  @Override
  public T getDatas() {
    return datas;
  }

  /**
   * @param datas the datas to set
   */
  @Override
  public void setDatas(T datas) {
    this.datas = datas;
  }

}
