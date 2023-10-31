package com.liang.customreport.exception;

import com.google.common.base.Preconditions;
import com.liang.customreport.common.Constants;
import com.liang.customreport.response.CommonResponse;
import com.liang.customreport.response.CommonResponse2;
import com.liang.customreport.response.ICommonResponse;
import java.sql.SQLIntegrityConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.validation.BindingResult;


/**
 * 不依赖iuap相关类的异常处理工具类
 *
 * @author gaotx
 */
public class BusiExceptionUtils {

  private BusiExceptionUtils() {
  }

  private static Logger logger = LoggerFactory.getLogger(BusiExceptionUtils.class);

  /**
   * 异常拆箱
   */
  public static Throwable unmarsh(Throwable ex) {
    Throwable cause = ex.getCause();
    if (cause != null) {
      cause = unmarsh(cause);
    } else {
      cause = ex;
    }
    return cause;
  }

  /**
   * 创装BusinessRuntimeException
   */
  public static void marshException(Exception e) {
    marshException(ICommonResponse.FAIL_CODE, e.getMessage(), e);
  }

  /**
   * 创装BusinessRuntimeException
   */
  public static void marshException(String code, String msg, Exception e) {
    Throwable cause = unmarsh(e);
    logger.error(cause.getMessage(), cause);
    if (cause instanceof BusinessRuntimeException) {
      throw (BusinessRuntimeException) cause;
    }

    throw new BusinessRuntimeException(code, msg, e);
  }

  public static <T> CommonResponse2<T> responseException2(Exception e, String msg) {
    Throwable cause = unmarsh(e);
    logger.error("<{}> " + msg + ":" + cause.getMessage(), e.getClass().getSimpleName(), cause);
    if (cause instanceof BusinessRuntimeException) {
      BusinessRuntimeException be = (BusinessRuntimeException) cause;
      if (StringUtils.isEmpty(be.getCode())) {
        return new CommonResponse2<>(ICommonResponse.FAIL_CODE, be.getMessage());
      }
      return new CommonResponse2<>(be.getCode(), be.getMessage());
    }
    if(cause instanceof SQLIntegrityConstraintViolationException){
      return new CommonResponse2<>(ICommonResponse.FAIL_CODE, "发票已存在");
    }
    String completeMsg = msg;
    String requestId = MDC.get(Constants.REQUEST_ID_KEY);
    if(StringUtils.isNotEmpty(requestId)){
      completeMsg = msg + "[requestId:" + requestId + "]";
    }
    return new CommonResponse2<>(ICommonResponse.FAIL_CODE, completeMsg);
  }

  /**
   * 如果e是businessException，返回业务异常信息，否则按照msg返回错误信息，并在后台打印log
   */
  public static ICommonResponse responseException(Throwable e, String msg) {
    Throwable cause = unmarsh(e);
    logger.error("<{}> " + msg + ":" + cause.getMessage(), e.getClass().getSimpleName(), cause);
    if (cause instanceof BusinessRuntimeException) {
      BusinessRuntimeException be = (BusinessRuntimeException) cause;
      if (StringUtils.isEmpty(be.getCode())) {
        return new CommonResponse(ICommonResponse.FAIL_CODE, be.getMessage());
      }
      return new CommonResponse(be.getCode(), be.getMessage());
    }
    String completeMsg = msg;
    String requestId = MDC.get(Constants.REQUEST_ID_KEY);
    if(StringUtils.isNotEmpty(requestId)){
      completeMsg = msg + "[requestId:" + requestId + "]";
    }
    return new CommonResponse(ICommonResponse.FAIL_CODE, completeMsg);
  }

  /**
   * 抛出业务异常信息
   */
  public static void wrapBusiException(String msg) {
    wrapBusiException(ICommonResponse.FAIL_CODE, msg);
  }

  public static void wrapBusiException(String code, String msg) {
    throw new BusinessRuntimeException(code, msg);
  }


  /*
   * @Dscp 使用@Valid校验参数后通过此方法检查校验结果
   * @Author dingjz
   * @Date 15:29 2019/2/20
   */

  public static void validBindResult(BindingResult bindResult) {
    Preconditions.checkNotNull(bindResult, "参数校验失败");
    if (bindResult.hasErrors()) {
      throw new BusinessRuntimeException(ICommonResponse.FAIL_CODE,
          bindResult.getAllErrors().get(0).getDefaultMessage());
    }
  }
}
