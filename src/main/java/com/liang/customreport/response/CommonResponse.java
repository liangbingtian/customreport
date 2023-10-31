/**
 *
 */
package com.liang.customreport.response;

/**
 * @author wangweir
 */
public class CommonResponse implements ICommonResponse {

  private String code;

  private String msg;

  private Object datas;

  /**
   * @author chenbiao
   */
  public static CommonResponse genSuccessResult(Object datas) {
    return new CommonResponse(
        ICommonResponse.SUCCESS_CODE, "SUCCESS", datas);
  }

  /**
   * 构建泛型的返回结果
   *
   * @param datas 数据
   * @param <T> 类型
   * @return 结果
   */
  public static <T> CommonResponse2<T> genSuccessResult2(T datas) {
    return new CommonResponse2<>(
        ICommonResponse.SUCCESS_CODE, "SUCCESS", datas);
  }

  public static <T> CommonResponse2<T> genSuccessResult2(String msg, T datas) {
    return new CommonResponse2<>(
        ICommonResponse.SUCCESS_CODE, msg, datas);
  }

  public CommonResponse() {
  }

  public CommonResponse(String code, String msg) {
    this.setCode(code);
    this.setMsg(msg);
  }

  public CommonResponse(String code, String msg, Object datas) {
    this.setCode(code);
    this.setMsg(msg);
    this.setDatas(datas);
  }


  /**
   * @param response
   */
  public CommonResponse(ICommonResponse response) {
    this.setCode(response.getCode());
    this.setMsg(response.getMsg());
  }

  /**
   * diwork 不支持此操作
   * @param <T>
   * @return
   */
  public static <T> CommonResponse2<T> unDiworkSupport() {
    return new CommonResponse2<>(
        ICommonResponse.DIWORK_UNSUPPORT, "diwork不支持此操作");
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
  public Object getDatas() {
    return datas;
  }

  /**
   * @param datas the datas to set
   */
  public void setDatas(Object datas) {
    this.datas = datas;
  }

}
