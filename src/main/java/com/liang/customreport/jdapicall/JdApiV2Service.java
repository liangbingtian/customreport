package com.liang.customreport.jdapicall;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.liang.customreport.jdapicall.bo.ParamInfo;
import com.liang.customreport.jdapicall.bo.common.JdApiV2RequestBo;
import com.liang.customreport.jdapicall.bo.common.JdApiV2ResultBo;
import com.liang.customreport.tools.HttpUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author dyy
 * @version 1.0
 * @date 2022/11/23 14:57
 * @desc 业务方法
 */
@Slf4j
public class JdApiV2Service<T, R> {

  public static final String PLATFORM_SERVER_URL = "https://api.jd.com/routerjson";

  public static final String V = "2.0";

  public R doRequest(T param, Class<R> clazz, ParamInfo info) {
    JdApiV2RequestBo<T> req = new JdApiV2RequestBo<>();
    req.setParam(param);

    String timestamp = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
    req.setTimestamp(timestamp);

    req.setAppSecret(info.getAppSecret());
    req.setAccessToken(info.getAccessToken());
    req.setAppKey(info.getAppKey());
    req.setV(V);

    Map<String, String> paramMap = new HashMap<>();
    paramMap.put("360buy_param_json", JSON.toJSONString(req.getParam()));
    paramMap.put("access_token", req.getAccessToken());
    paramMap.put("app_key", req.getAppKey());
    paramMap.put("method", info.getApi());
    paramMap.put("timestamp", timestamp);
    paramMap.put("v", req.getV());
    try {
      paramMap.put("sign", getSign(req, info));
    } catch (Exception e) {
      throw new RuntimeException("生成京东api签名失败");
    }

    return transform(HttpUtil.doPost(PLATFORM_SERVER_URL, paramMap), clazz);
  }

  protected R transform(String res, Class<R> clazz) {
    if (res.contains("error_response")) {
      log.error(JSON.toJSONString(res));
      throw new RuntimeException(res);
    }
    R r = JSON.parseObject(res, clazz);
    if (r == null) {
      log.error(JSON.toJSONString(res));
      throw new RuntimeException(res);
    }
    return r;
  }

  private String getSign(JdApiV2RequestBo<T> req, ParamInfo info) throws Exception {
    Map<String, String> pmap = new TreeMap<>();
    pmap.put("360buy_param_json", JSON.toJSONString(req.getParam()));
    pmap.put("method", info.getApi());
    pmap.put("access_token", req.getAccessToken());
    pmap.put("app_key", req.getAppKey());
    pmap.put("timestamp", req.getTimestamp());
    pmap.put("v", req.getV());

    StringBuilder sb = new StringBuilder(req.getAppSecret());

    for (Map.Entry<String, String> stringStringEntry : pmap.entrySet()) {
      String name = (String) ((Map.Entry) stringStringEntry).getKey();
      String value = (String) ((Map.Entry) stringStringEntry).getValue();
      if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(value)) {
        sb.append(name).append(value);
      }
    }

    sb.append(req.getAppSecret());
    return StringUtils.upperCase(SecureUtil.md5(sb.toString()));
  }

}