package com.liang.customreport.response;

/**
 * @author wangweir
 */
public class CommonResponseEnum {

  public final static ICommonResponse2 SUCCESS = new CommonResponse2(
      ICommonResponse.SUCCESS_CODE,
      "操作成功");

  /**
   * 未知错误
   */
  public final static ICommonResponse2 FAIL = new CommonResponse2(
      ICommonResponse.FAIL_CODE, "未知错误");

  /**
   * 验签不成功
   */
  public final static ICommonResponse2 SIGN_FAIL = new CommonResponse2(
      ICommonResponse.SIGN_FAIL,
      "验签不成功，请确认");

  public final static ICommonResponse2 INVALIDA_CALL = new CommonResponse2(
      ICommonResponse.INVALIDA_CALL, "非法的请求");

  public final static ICommonResponse2 INVALID_IDENTITY = new CommonResponse2(
      ICommonResponse.INVALID_IDENTIFY, "请求身份不合法");

  /**
   * 操作限流
   */
  public final static ICommonResponse2 LIMIT_CALL = new CommonResponse2(
      ICommonResponse.HTTP_RATE_LIMIT, "操作太频繁，请稍后再试！");

}
