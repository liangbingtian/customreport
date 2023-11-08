package com.liang.customreport.common;

import java.util.HashMap;
import java.util.Map;

public class Constants {

  /* 默认分页个数 */
  public final static int DEFAULT_PAGE_SIZE = 20;

  //管理员不能被取消的两个菜单
  public static final int ROLE_MANAGE_FUNC_ID = 6;//角色管理的菜单主键
  public static final int USER_MANAGE_FUNC_ID = 8;//用户管理的菜单主键
  public static final String ENT_ADMIN_ROLE_CODE = "SYS_020";

  public final static String USER_CODE = "usercode";

  public static final String CSV = ".csv";

  public static String[] CSV_HEADER1 = new String[]{"日期",	"账户名称",	"产品线",	"计划类型",	"计划ID",	"推广计划",	"单元ID",	"推广单元",	"创意ID",	"推广创意",	"品牌ID",	"品牌名称",	"类目ID"	,"类目名称",	"跟单SKU ID",	"人群ID",	"人群名称",	"广告定向类型",	"展现数"	,"点击数"	,"总费用",	"直接订单行",	"直接订单金额",	"间接订单行",	"间接订单金额",	"总订单行",	"总订单金额"	,"直接购物车数"	,"间接购物车数"	,"总购物车数",	"点赞数",	"评论数",	"分享数",	"关注数",	"互动数",	"互动率",	"预售订单行",	"预售订单金额",	"下单新客数",	"预约数",	"领劵数",	"商品关注数",	"店铺关注数"};

  public static final Map<String , String> CSV_HEADER_MAP1 = new HashMap<>();

  static {
    CSV_HEADER_MAP1.put("日期", "date_str");
    CSV_HEADER_MAP1.put("账户名称", "pin");
    CSV_HEADER_MAP1.put("产品线", "business");
    CSV_HEADER_MAP1.put("计划类型", "campaign_type");
    CSV_HEADER_MAP1.put("计划ID", "campaign_id");
    CSV_HEADER_MAP1.put("推广计划", "campaign_name");
    CSV_HEADER_MAP1.put("单元ID", "group_id");
    CSV_HEADER_MAP1.put("推广单元", "group_name");
    CSV_HEADER_MAP1.put("创意ID", "ad_id");
    CSV_HEADER_MAP1.put("推广创意", "ad_name");
    CSV_HEADER_MAP1.put("品牌ID", "sku_brand_id");
    CSV_HEADER_MAP1.put("品牌名称", "sku_brand_name");
    CSV_HEADER_MAP1.put("展现数", "impressions");
    CSV_HEADER_MAP1.put("点击数", "clicks");
    CSV_HEADER_MAP1.put("总费用", "all_cost");
    CSV_HEADER_MAP1.put("直接订单行", "dir_ord_cnt");
    CSV_HEADER_MAP1.put("直接订单金额", "dir_ord_sum");
    CSV_HEADER_MAP1.put("间接订单行", "ind_ord_cnt");
    CSV_HEADER_MAP1.put("间接订单金额", "ind_ord_sum");
    CSV_HEADER_MAP1.put("总订单行", "tot_ord_cnt");
    CSV_HEADER_MAP1.put("总订单金额", "tot_ord_sum");
    CSV_HEADER_MAP1.put("直接购物车数", "dir_cart_cnt");
    CSV_HEADER_MAP1.put("间接购物车数", "ind_cart_cnt");
    CSV_HEADER_MAP1.put("总购物车数", "tot_cart_cnt");
    CSV_HEADER_MAP1.put("下单新客数", "new_customer");
  }

  public static final Map<String , String> CSV_HEADER_STR_MAP1 = new HashMap<>();

  static {
    CSV_HEADER_MAP1.put("日期", "date_str");
    CSV_HEADER_MAP1.put("账户名称", "pin");
    CSV_HEADER_MAP1.put("产品线", "business");
    CSV_HEADER_MAP1.put("计划类型", "campaign_type");
    CSV_HEADER_MAP1.put("推广计划", "campaign_name");
    CSV_HEADER_MAP1.put("推广单元", "group_name");
    CSV_HEADER_MAP1.put("推广创意", "ad_name");
    CSV_HEADER_MAP1.put("品牌名称", "sku_brand_name");

  }

  public static final Map<String , String> CSV_HEADER_NUMBER_MAP1 = new HashMap<>();

  static {
    CSV_HEADER_MAP1.put("计划ID", "campaign_id");
    CSV_HEADER_MAP1.put("单元ID", "group_id");
    CSV_HEADER_MAP1.put("创意ID", "ad_id");
    CSV_HEADER_MAP1.put("品牌ID", "sku_brand_id");
    CSV_HEADER_MAP1.put("展现数", "impressions");
    CSV_HEADER_MAP1.put("点击数", "clicks");
    CSV_HEADER_MAP1.put("总费用", "all_cost");
    CSV_HEADER_MAP1.put("直接订单行", "dir_ord_cnt");
    CSV_HEADER_MAP1.put("直接订单金额", "dir_ord_sum");
    CSV_HEADER_MAP1.put("间接订单行", "ind_ord_cnt");
    CSV_HEADER_MAP1.put("间接订单金额", "ind_ord_sum");
    CSV_HEADER_MAP1.put("总订单行", "tot_ord_cnt");
    CSV_HEADER_MAP1.put("总订单金额", "tot_ord_sum");
    CSV_HEADER_MAP1.put("直接购物车数", "dir_cart_cnt");
    CSV_HEADER_MAP1.put("间接购物车数", "ind_cart_cnt");
    CSV_HEADER_MAP1.put("总购物车数", "tot_cart_cnt");
    CSV_HEADER_MAP1.put("下单新客数", "new_customer");

  }

  public final static String USER_NAME = "username";

  public final static String START_DATE = "start_date";

  public final static String END_DATE = "end_date";

  public static final String TRANS_DAYS = "trans_days";

  public static final String CALIBER = "caliber";

  public static final String GIFT_FLAG = "gift_flag";

  public static final String ORDER_STATUS = "order_status";

  public static final String EFFECT = "effect";

  public static final String IS_DAILY = "is_daily";
  /**
   * 生成token前缀
   */
  public final static String TOKEN_IDENTIFY = "TOKEN:";
  /**
   * 建立userid-token关系时，为userid加前缀
   */
  public final static String USERID_IDENTIFY = "USERID:";

  /**
   * 登录设置的过期时长（分钟）
   */
  public final static long MOBILE__TIMEOUTS = 5;
  /**
   * zuul转发-用户id
   */
  public final static String ZUUL_HEADER_USERID = "zuul-userid";
  /**
   * 友互通原始token
   */
  public final static String ZUUL_HEADER_YHTTOKEN = "zuul-yht-token";

  /**
   * u8c环境中的serviceCode
   */
  public final static String ZUUL_HEADER_SERVICECODE = "zuul-service-code";
  /**
   * zuul转发-用户code
   */
  public final static String ZUUL_HEADER_USERCODE = "zuul-usercode";
  /**
   * zuul转发-用户名
   */
  public final static String ZUUL_HEADER_USERNAME = "zuul-username";
  /**
   * zuul转发-公司ID
   */
  public final static String ZUUL_HEADER_CORPID = "zuul-corpid";
  /**
   * zuul转发-公司名
   */
  public final static String ZUUL_HEADER_CORPNAME = "zuul-corpname";

  /**
   * @deprecated zuul转发-请求ID,弃用，统一换为REQUEST_ID_KEY
   */
  @Deprecated
  public final static String ZUUL_HEADER_REQID = "zuul-request-id";

  /**
   * @deprecated zuul转发-认证成功 替换为请求网关来源
   */
  @Deprecated
  public final static String ZUUL_HEADER_AUTH_SUCCESS = "zuul-auth-success";

  /**
   * zuul转发-请求网关来源
   */
  public final static String ZUUL_HEADER_GATEWAY_FROM = "zuul-gateway-from";

  /**
   * URL上面的appid
   */
  public final static String PATH_APPID = "appid";

  /**
   * MDC-参数userid
   */
  public static final String CUR_USER_ID_KEY = "current-user-id";

  /**
   * MDC-请求ID
   */
  public static final String REQUEST_ID_KEY = "request-id";

  /**
   * 慢日志常量
   */
  public static final String SLOW_LOGGER = "com.yonyou.einvoice.slow";

  /**
   * gateway记录开始时间
   */
  public static final String TIME_START = "time-start";

  /**
   * 接口版本信息，用于恢复发布版本话
   */
  public static final String TAX_VERSION = "x-tax-version";

  /**
   * 平台管理员角色
   */
  public static final String ROLE_ADMIN = "admin";

  /**
   * pdf发票上传文件数量限制
   */
  public static final int PDF_UPLOAD_NUM = 20;

  /**
   * pdf发票上传单个文件大小限制
   */
  public static final int PDF_FILE_SIZE = 1 * 1024 * 1024;

  public static final String TRACE_ID = "traceId";


  public static final String INFO_LIST = "[\n"
      + "  {\n"
      + "    \"id\": 1,\n"
      + "    \"username\": \"FTSJDZY\",\n"
      + "    \"access_token\": \"1fb2353257524bf88d3a067195bdba82mzi1\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:01\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 2,\n"
      + "    \"username\": \"hks17177104\",\n"
      + "    \"access_token\": \"f2bfb04233b74c9d87fa230c77af6c29jjiz\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 3,\n"
      + "    \"username\": \"jdangfa\",\n"
      + "    \"access_token\": \"c72afbe7de184f9da2d249118d2f7d1bzizt\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 4,\n"
      + "    \"username\": \"PGBaylineFC\",\n"
      + "    \"access_token\": \"0b3ddc93ebd642f59a1c915186497346zgrj\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 5,\n"
      + "    \"username\": \"PGBFTampax\",\n"
      + "    \"access_token\": \"88f27f406ee144eb8a07a935a39ffbbdmnda\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 6,\n"
      + "    \"username\": \"RTB代理2\",\n"
      + "    \"access_token\": \"a8fc01f5629f483d8dda22331f7a4fcbxzju\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 7,\n"
      + "    \"username\": \"丝蕴自营旗舰店投放\",\n"
      + "    \"access_token\": \"79356123e7ee45c1839a940190d3d37emjnl\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 8,\n"
      + "    \"username\": \"施华蔻专业投放\",\n"
      + "    \"access_token\": \"8f74dafa208140c58abb2438f9a81b61zmey\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 9,\n"
      + "    \"username\": \"施华蔻旗舰店\",\n"
      + "    \"access_token\": \"7a4c1f31f2b64d07875877d0a39494530yzf\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 10,\n"
      + "    \"username\": \"施华蔻旗舰店2022\",\n"
      + "    \"access_token\": \"070e4131ea014203b0d1549d04c8e31cqxmz\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 11,\n"
      + "    \"username\": \"施华蔻自营投放\",\n"
      + "    \"access_token\": \"c119cae287b6401cb8d344dcdbc13c31yzex\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 12,\n"
      + "    \"username\": \"澳芝曼投放\",\n"
      + "    \"access_token\": \"c2e8ee76e3324a018b50ad461d2c51d0zjlo\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 13,\n"
      + "    \"username\": \"爱敬_自营1\",\n"
      + "    \"access_token\": \"220c9b870c4f411c8590a3b0cc6472c4jiwy\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 14,\n"
      + "    \"username\": \"资生堂个人护理旗舰店\",\n"
      + "    \"access_token\": \"7ade956561484c2c9e2e134408983d2dkztk\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 16,\n"
      + "    \"username\": \"宫中秘策推广\",\n"
      + "    \"access_token\": \"09b217c816d54b6f916d2bb5533a5636yzgx\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 17,\n"
      + "    \"username\": \"宫中秘策-推广\",\n"
      + "    \"access_token\": \"cbe95589c5754244a07bff5606dbddc4otu1\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 18,\n"
      + "    \"username\": \"POP直营店-投放\",\n"
      + "    \"access_token\": \"18747a1f0d874f0184ebf89b2a4f6ce6hmwy\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 21,\n"
      + "    \"username\": \"aupres_pop\",\n"
      + "    \"access_token\": \"ac5867d9928f4a71b38ae0e8cb794973dy3z\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 22,\n"
      + "    \"username\": \"Shiseido_子账号1\",\n"
      + "    \"access_token\": \"f6d0d1da1bdb4f9d98d3f5543b19cf78gqxz\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 23,\n"
      + "    \"username\": \"安热沙123\",\n"
      + "    \"access_token\": \"f7078b4f58ec41829239a809eedbf207hing\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 25,\n"
      + "    \"username\": \"oupolai\",\n"
      + "    \"access_token\": \"66358ddb60cf4f9ea59f3f6dc5d0a5efthkz\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 26,\n"
      + "    \"username\": \"塞巴斯汀pop店\",\n"
      + "    \"access_token\": \"33fc8317386d4a649526ce81b668610fnmyz\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 27,\n"
      + "    \"username\": \"vivess716\",\n"
      + "    \"access_token\": \"d898b460e73e4cbab0ba1c7701ec4bea0njb\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 28,\n"
      + "    \"username\": \"开云POP投放\",\n"
      + "    \"access_token\": \"760b16de5ae5410892903f0087c1ffa1je2z\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 29,\n"
      + "    \"username\": \"MUFF2020\",\n"
      + "    \"access_token\": \"5328fff1bd5843c2a20b1eea26e3774fmyog\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 30,\n"
      + "    \"username\": \"Guerlain_投放\",\n"
      + "    \"access_token\": \"d412f0c05f6947cfa881c85d6002064exmmy\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 31,\n"
      + "    \"username\": \"妮维雅_男士自营1\",\n"
      + "    \"access_token\": \"0c2d37abb46c4b859894d6cf00a93735dm3y\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 32,\n"
      + "    \"username\": \"妮维雅_女士自营1\",\n"
      + "    \"access_token\": \"13240d2a92e64318bb1fb5c87af4b7aamynd\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 33,\n"
      + "    \"username\": \"GIVENCHY投放\",\n"
      + "    \"access_token\": \"b7d830e3cafb4360ad28e8b56735fec5mwzt\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 34,\n"
      + "    \"username\": \"玫珂菲投放\",\n"
      + "    \"access_token\": \"2430c7a7680d4e3494b8cee85acdfb3czmgm\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 35,\n"
      + "    \"username\": \"强生美妆投放鲲驰账号\",\n"
      + "    \"access_token\": \"098fbc3a9afb455e9047f0e6105b6cb2m2e5\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 36,\n"
      + "    \"username\": \"娇兰自营官方旗舰店\",\n"
      + "    \"access_token\": \"8870e989f2de4efab17985742b7700e55odl\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 38,\n"
      + "    \"username\": \"ELIXIR怡丽丝尔\",\n"
      + "    \"access_token\": \"073337f64298421bbeb32947d53f67b1m3zj\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 39,\n"
      + "    \"username\": \"NAXISUO999\",\n"
      + "    \"access_token\": \"aef8736dec28411abdbe3b65ebddf0fcznmr\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 40,\n"
      + "    \"username\": \"SANZHAIYISHENG587\",\n"
      + "    \"access_token\": \"21adf0e4ae34429bbf76246144e6b5e7mjfh\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 41,\n"
      + "    \"username\": \"施华蔻自营投放2022\",\n"
      + "    \"access_token\": \"fc9c32a763a94b588421cfab35a0ea9dzmyy\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 42,\n"
      + "    \"username\": \"贝玲妃投放2021\",\n"
      + "    \"access_token\": \"1cb3ebf37ef1451f89c8c275c2edf7ff2viz\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 43,\n"
      + "    \"username\": \"菲婷丝-惠润\",\n"
      + "    \"access_token\": \"951093c758b442ebad127e5d28e108f2mdkz\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 44,\n"
      + "    \"username\": \"鲲智网络2020\",\n"
      + "    \"access_token\": \"714b00cbeda94187af110ee0c22c5ed5nze0\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 45,\n"
      + "    \"username\": \"菲婷丝-可悠然-投放\",\n"
      + "    \"access_token\": \"76ff022178474a289e7057450c839bb8jaym\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 46,\n"
      + "    \"username\": \"惠润投放专用\",\n"
      + "    \"access_token\": \"1f5b1ff5fbeb4ab49b55370eea44034czjvm\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 47,\n"
      + "    \"username\": \"yhx_vx999\",\n"
      + "    \"access_token\": \"acc71ceb82094c998b1fa616b9812d1bzfjz\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 48,\n"
      + "    \"username\": \"yhx-wtg888\",\n"
      + "    \"access_token\": \"a4b569b42d2142629fa40dc39558e762inty\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 49,\n"
      + "    \"username\": \"yhx-vx箱包\",\n"
      + "    \"access_token\": \"fa5554d8a1f44b4aaa7da01d988737bb1ntu\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 50,\n"
      + "    \"username\": \"wanbaolong2022投放\",\n"
      + "    \"access_token\": \"5f9872e565394e8b92fb8cb637377e32ztu2\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 51,\n"
      + "    \"username\": \"妮维雅_CPD_CPS1\",\n"
      + "    \"access_token\": \"c550769c0727415581431c3fd188ee00mdc2\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 52,\n"
      + "    \"username\": \"妮维雅_联合活动\",\n"
      + "    \"access_token\": \"7b3b4e3af759463a89f5e2945a2853c5yjrl\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 53,\n"
      + "    \"username\": \"Shiseido_联合活动3\",\n"
      + "    \"access_token\": \"225d23583c814375a9ddf5735f361a84ji1z\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 54,\n"
      + "    \"username\": \"妮维雅_联合活动2\",\n"
      + "    \"access_token\": \"f588342b6c5c40ef915e8e78ce805373odm0\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 55,\n"
      + "    \"username\": \"kunchi_2011\",\n"
      + "    \"access_token\": \"987fa88cc9de4b0bb9b5285f9dd422254ogn\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 56,\n"
      + "    \"username\": \"jahwa-gaofu\",\n"
      + "    \"access_token\": \"46305160cac74a54891633c459d0a335duxn\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 57,\n"
      + "    \"username\": \"POLA海外旗舰店\",\n"
      + "    \"access_token\": \"2ad2b0246abe419da9f45193c6d2c629kmmi\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 58,\n"
      + "    \"username\": \"丝蕴自营投放2022\",\n"
      + "    \"access_token\": \"0ff03b71044d4a7181100f7ff8c40014nzew\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 59,\n"
      + "    \"username\": \"kunchisy\",\n"
      + "    \"access_token\": \"c00c0a781faa4d2f8c343d65ebbcf0b6xyza\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 60,\n"
      + "    \"username\": \"Amore-LV\",\n"
      + "    \"access_token\": \"3dd6c14789e24da9997b8684a0068340mxnd\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 61,\n"
      + "    \"username\": \"鲲驰2021\",\n"
      + "    \"access_token\": \"569aba14a0f84aa59efe8c838d95aeccwjhm\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 62,\n"
      + "    \"username\": \"美琦客2021\",\n"
      + "    \"access_token\": \"3ee2c9e30058437da478faaff8ac51285ztm\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 63,\n"
      + "    \"username\": \"常熟鲲美新零售2021\",\n"
      + "    \"access_token\": \"43b103e60918488aafc39214f53919f6nimt\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 64,\n"
      + "    \"username\": \"上海银河系2021\",\n"
      + "    \"access_token\": \"7f6c60a412f546a2a58d48eae0cc5b6cytqx\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 65,\n"
      + "    \"username\": \"BSD彭艾云\",\n"
      + "    \"access_token\": \"fe39a91cff0148e79982c0201b1825605mwn\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 66,\n"
      + "    \"username\": \"jd_nvbsd\",\n"
      + "    \"access_token\": \"85bee87536ee4efc87f177fe960e52aczwu4\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 67,\n"
      + "    \"username\": \"bvp06121666\",\n"
      + "    \"access_token\": \"f1eb73396ce14d8ab216d9d0c09758e3zjfl\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 68,\n"
      + "    \"username\": \"BSD-户外代理\",\n"
      + "    \"access_token\": \"2ec19186203a4ecca0ca3a679e815f81xody\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 69,\n"
      + "    \"username\": \"BSD-奥莱代理\",\n"
      + "    \"access_token\": \"3e352204e73844348366633b5fc235cdymdr\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 70,\n"
      + "    \"username\": \"BSD-童装代理\",\n"
      + "    \"access_token\": \"3803786624cb4ffc90180db5deeaaf144njy\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 71,\n"
      + "    \"username\": \"BSD奥莱店铺推广\",\n"
      + "    \"access_token\": \"3100e312824242439233c006a9b5854bmzew\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 72,\n"
      + "    \"username\": \"BSD户外店铺推广\",\n"
      + "    \"access_token\": \"f24dad4f773441bc8ec26b1c0075c593ji0z\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 73,\n"
      + "    \"username\": \"波司登童装自营\",\n"
      + "    \"access_token\": \"819f342b41d84dc48b0e5973b7c37fe1ode5\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 74,\n"
      + "    \"username\": \"wenger-TG\",\n"
      + "    \"access_token\": \"bcc3090b440c4df3b9b670831dbc291bza5m\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-22 17:15:01\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 75,\n"
      + "    \"username\": \"jdwkun-2023\",\n"
      + "    \"access_token\": \"30d6f2e1fcd44b219a9fcf61477ecb3dmmzb\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-06-26 19:01:30\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 90,\n"
      + "    \"username\": \"kunchicasio\",\n"
      + "    \"access_token\": \"4c80d61f75c949aca43838c51a619068gq2m\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-06-28 14:00:19\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 92,\n"
      + "    \"username\": \"Longines-2021\",\n"
      + "    \"access_token\": \"3813dffb075446899e0dc95634dfe81fmzmi\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-06-28 14:00:21\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 103,\n"
      + "    \"username\": \"格力高glico2019\",\n"
      + "    \"access_token\": \"54229500b3114146b9eb285dbb9a5313ymjk\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-06-28 14:00:37\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 125,\n"
      + "    \"username\": \"Zenith真力时POP-代理\",\n"
      + "    \"access_token\": \"05d41c2dc2844858b83119ad5c85a0e5ndfj\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-08-24 10:53:43\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 126,\n"
      + "    \"username\": \"PGBaylineFC-POP\",\n"
      + "    \"access_token\": \"355a950b18b041128cfb32abd622feeeytk1\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-08-29 19:00:14\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  }\n"
      + "]\n";

  public static final String INFO1 = "[{\n"
      + "    \"id\": 65,\n"
      + "    \"username\": \"BSD彭艾云\",\n"
      + "    \"access_token\": \"fe39a91cff0148e79982c0201b1825605mwn\",\n"
      + "    \"appkey\": \"A1D3C721A3E382FF4915BE266B4294F6\",\n"
      + "    \"app_secret\": \"8d08db8de0ec468ebe234dcfdc1c3dca\",\n"
      + "    \"tf_open\": 1,\n"
      + "    \"create_date\": \"2023-05-09 18:25:13\",\n"
      + "    \"update_date\": \"2023-10-18 13:00:02\"\n"
      + "  }]";
}
