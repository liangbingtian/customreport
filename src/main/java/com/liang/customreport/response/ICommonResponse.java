package com.liang.customreport.response;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 通用的RestAPI接口相应接口
 *
 * @author wangweir
 */
public interface ICommonResponse {

  /**
   * 状态码
   */
  String CODE = "code";

  /**
   * 信息
   */
  String MSG = "msg";

  /**
   * 数据
   */
  String DATAS = "datas";

  // 返回码 -----------------------------------------start
  /**
   * 操作成功
   */
  String SUCCESS_CODE = "0000";

  /**
   * 操作MSG
   */
  String SUCCESS_MSG = "success";
  /**
   * 数据不合法，传入参数
   */
  String DATA_INVALID = "1001";
  /**
   * 数据不存在
   */
  String DATA_NOT_EXISTS = "1002";
  /**
   * 数据已经存在
   */
  String DATA_EXISTS = "1004";

  /**
   * 操作频繁 diwork不支持此操作
   */
  String DIWORK_UNSUPPORT = "1005";
  /**
   * 未找到相应税控组织
   */
  String NOT_FOUND_ORG = "2001";

  /**
   * 未找到相应纳税主体
   */
  String NOT_FOUND_TAXBODY = "2002";

  /**
   * 销售方纳税人识别号与税控组织对应的纳税人识别号不匹配
   */
  String XSF_NSRSBH_NOT_MATCH_WITH_ORG = "2003";

  /**
   * 开票组织不匹配
   */
  String NOT_MATCH_WITH_ORG = "2004";

  /**
   * 未找到相应纳税信息
   */
  String NOT_FOUND_TAXINFO = "2005";

  /**
   * 不能唯一确定纳税主体信息
   */
  String NOT_SURE_UNIQUE_TAXBODY = "2006";

  /**
   * 缺少发票明细数据，请检查
   */
  String ITEMS_IS_NULL = "2007";

  /**
   * 对应开票请求未找到相应税控设备
   */
  String NOT_FOUND_TAXMECH = "2008";

  /**
   * 开票组织已停用
   */
  String ORG_DISABLED = "2009";

  /**
   * 发票金额超过最大可开票限额
   */
  String OVER_LIMIT = "2010";

  /**
   * 红字信息表获取失败
   */
  String ACQIURE_REDAPPLY_FAIL = "2011";

  /**
   * 发票已经存在
   */
  String INVOICE_EXISTS_ERROR = "3001";
  /**
   * 发票解析出错
   */
  String INVOICE_PARSE_ERROR = "3002";
  /**
   * 发票查验不合法
   */
  String INVOICE_CHECK_ERROR = "3003";
  /**
   * 发票状态不正确
   */
  String INVOICE_STATUS_ERROR = "3004";
  /**
   * 发票pdf文件上传失败
   */
  String INVOICE_PDF_UPLOADERROR = "3005";
  /**
   * 无电子签章
   */
  String NOT_ELEC_SIGNATURE = "3010";
  /**
   * 电子签名被篡改
   */
  String CA_VERIFY_FALSE = "3011";
  /**
   * 证书链不通过
   */
  String NOT_CERTPATH = "3012";

  /**
   * 购买方纳税人识别号为空
   */
  String GMF_NSRSBH_ISNULL = "3013";
  /**
   * 无销货清单
   */
  String LACK_SALE_LIST = "3014";
  /**
   * 个人用户查验面试次数超上限
   */
  String VERIFY_TRAIL_LIMIT = "3015";

  /**
   * 供应商黑名单
   */
  String SUPPLIER_BLACKLIST = "3016";

  /**
   * 发票连号
   */
  String SEC_NUM = "3017";

  /**
   * 发票连号
   */
  String HOLIDAY = "3018";

  /**
   * 发票已经作废（查验）
   */
  String HAS_INVALID = "3019";

  /**
   * 发票excel导入校验错误
   */
  String APPLY_IMPORT_ERR = "3020";

  /**
   * 密码不匹配，登录失败
   */
  String LOGIN_FAIL_PWD = "4001"; //NOSONAR

  /**
   * 用户不存在
   */
  String LOGIN_FAIL_NOT_EXIST = "4002";

  /**
   * 用户状态不正确
   */
  String LOGIN_FAIL_STATUS = "4003";

  /**
   * 用户类型不正确
   */
  String LOGIN_FAIL_TYPE = "4004";


  /**
   * 税盘当前不可进行签名
   */
  String VATCHECK_NSRSBH_CAN_NOT_SIGN = "5001";

  /**
   * 税盘当前不可进行签名取消
   */
  String VATCHECK_NSRSBH_CAN_NOT_SIGN_CANCEL = "5002";

  /**
   * 税盘不在线
   */
  String VATCHECK_NSRSBH_OFFLINE_FAIL = "5010";

  /**
   * 税盘会话超时
   */
  String VATCHECK_NSRSBH_TOKEN_INVALIDATE_FAIL = "5011";

  /**
   * 税局限流
   */
  String VATCHECK_NSRSBH_RATE_LIMIT_FAIL = "5012";


  /**
   * 税局同步结果warning，比如同步数据为0，警告但不是错误
   */
  String VATCHECK_NSRSBH_SYNC_WARNING_FAIL = "5020";

  /**
   * 助手websocket通道掉线
   */
  String VATCHECK_CLIENT_BRIDGE_OFFLINE = "5060";


  /**
   * 签名失败
   */
  String SIGN_FAIL = "8001";
  /**
   * 非法的api调用
   */
  String INVALIDA_CALL = "8002";
  /**
   * 请求身份不合法
   */
  String INVALID_IDENTIFY = "8003";

  /**
   * appid为空
   */
  String APPID_ISNULL = "8004";

  /**
   * 无法获取appid对应的key
   */
  String NOT_FOUND_APPID_KEY = "8005";

  /**
   * 请求头缺少sign值
   */
  String NO_HEADER_SIGN = "8006";


  /**
   * 签名验证配置信息错误，请联系管理员
   */
  String SIGN_CONFIG_ERROR = "8007";

  /**
   * 验签失败,签名数据缺少requestdatas
   */
  String NO_REQUESTDATAS = "8008";

  /**
   * 验签失败,签名数据被篡改
   */
  String DATA_IS_TAMPER = "8009";

  /**
   * 过期的调用
   */
  String REQUEST_IS_OUTDATE = "8010";

  /**
   * 签名值不正确
   */
  String SIGNVALUE_IS_ERROR = "8011";


  /**
   * 使用纳税申报试用激活码成功激活
   */
  String TAX_DECLARATION_ACTIVE_SUCCESS = "8012";

  /**
   * 许可控制错误
   */
  String ADMISSION_CTRL_ERROR = "8020";

  /**
   * 未知错误
   */
  String FAIL_CODE = "9999";

  /**
   * 请求方法不正确
   */
  String HTTP_METHOD_NOT_SUPPORT = "9405";

  /**
   * 超过限流数
   */
  String HTTP_RATE_LIMIT = "9417";
  /**
   * 微信小程序获取token不成功
   */
  String WX_ERROR = "5001";

  // 61XX的状态码为交互异常状态码; 其它慎用
  /**
   * 发票状态检查
   */
  String INVOICE_STATUS_CHECK = "6101";
  // end 61XX的状态码为交互异常状态码

  // 发票介质不是电子发票

  /**
   * 查验上传到疑票管理
   */
  String UPLOAD_SUSPECTINVOICE = "9001";
  // 返回码 -----------------------------------------end

  /**
   * @return 信息
   */
  String getMsg();

  /**
   * 设置信息
   *
   * @param msg 信息
   */
  void setMsg(String msg);

  /**
   * @return 响应编码
   */
  String getCode();

  /**
   * 设置相应编码
   *
   * @param code 相应编码
   */
  void setCode(String code);

  /**
   * 是否成功
   *
   * @return true if success; success code equal "0000"
   */
  @JsonIgnore
  @JSONField(serialize = false, deserialize = false)
  default boolean isSuccess() {
    return SUCCESS_CODE.equals(getCode());
  }

  @JsonIgnore
  @JSONField(serialize = false, deserialize = false)
  default boolean isFailed() {
    return !isSuccess();
  }



}
