/**
 *
 */
package com.liang.customreport.exception;

import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author wangweir
 */
public class BusinessRuntimeException extends RuntimeException {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * 错误编码
   */
  private String code;


  public BusinessRuntimeException(String code, String msg) {
    super(msg);
    this.code = code;
  }

  public BusinessRuntimeException(String code, String msg, Throwable cause) {
    super(msg, cause);
    this.code = code;
  }

  public BusinessRuntimeException(String message) {
    super(message);
  }

  public BusinessRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * @param code the code to set
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * 重写getStackTrace，如果BusinessRuntimeException非原始异常，直接打印完整异常信息，如果是原始异常，只打印com.yonyou
   * @return
   */
  @Override
  public StackTraceElement[] getStackTrace() {
    if (getCause() != null) {
      return super.getStackTrace();
    }
    StackTraceElement[] elements = super.getStackTrace();
    if (ArrayUtils.isEmpty(elements)) {
      return elements;
    }
    return Arrays.stream(elements)
        .filter(t -> t.getClassName().startsWith("com.yonyou")).toArray(StackTraceElement[]::new);
  }

}
