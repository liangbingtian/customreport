package com.liang.customreport.tools;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wjs
 * @date 2020/12/24 18:56
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory
        .getLogger(HttpUtil.class);

    private static final String CHAR_SET = "UTF-8";

    /**
     * post请求（用于请求json格式的参数）
     * @param url url
     * @param params params
     */
    public static String doPost(String url, String params){

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity(params, CHAR_SET);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;

        try {
            logger.debug("doPost: {}", url);
//            RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000).setSocketTimeout(5000).build();
//            httpPost.setConfig(requestConfig);
            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String s = EntityUtils.toString(responseEntity, CHAR_SET);
                logger.debug("responseEntity: {}", s);
                return s;
            } else{
                logger.error("请求返回:"+state+"("+url+")");
                throw new RuntimeException("请求返回:"+state+"("+url+")");
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * post请求（用于请求json格式的参数）header信息
     * @param url url
     * @param params params
     * @param headerMap header信息
     * @return str
     * @throws Exception e
     */
    public static String doPost(
        String url, String params, Map<String, String> headerMap) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        if (headerMap != null) {
            for (Map.Entry<String, String> map : headerMap.entrySet()) {
                httpPost.setHeader(map.getKey(), map.getValue());
            }
        }
        StringEntity entity = new StringEntity(params, CHAR_SET);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            logger.debug("doPost: {}", url);
            logger.debug("params: {}", params);
            logger.debug("headerMap: {}", JSON.toJSONString(headerMap));
            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String s = EntityUtils.toString(responseEntity, CHAR_SET);
                logger.debug("responseEntity: {}", s);
                return s;
            } else{
                String errorMsg = "请求返回:"+state+"("+url+")";
                logger.error(errorMsg);
                throw new Exception(errorMsg);
            }
        }
        finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String doPost(String url, Map<String, String> paramMap) {
        
      CloseableHttpClient httpClient = HttpClients.createDefault();
      CloseableHttpResponse response = null;
      String resultString = "";
      try {
          logger.debug("doPost: {}", url);
          logger.debug("params: {}", paramMap);
          HttpPost httpPost = new HttpPost(url);
          httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
          if (paramMap != null) {
            List<NameValuePair> paramList = new ArrayList<>();
              for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                  String value =  entry.getValue();
                  if(value != null){
                      paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                  }
              }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,CHAR_SET);
            httpPost.setEntity(entity);
          }
          response = httpClient.execute(httpPost);
          logger.debug("出参状态: {}", response.getStatusLine());
          resultString = EntityUtils.toString(response.getEntity(), CHAR_SET);
          logger.debug("出参: {}", resultString);
      } catch (Exception e) {
          throw new RuntimeException(e);
      } finally {
          try {
              if (response != null) {
                  response.close();
              }
          } catch (IOException e) {
            e.printStackTrace();
          }
      }
      return resultString;
    }

    /**
     * get 请求
     * @param url url
     * @return str
     */
    public static String doGet(String url) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            logger.debug("doGet: [{}]", url);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                String s = EntityUtils.toString(response.getEntity(), CHAR_SET);
                logger.debug("response: {}", s);
                return s;
            }
            String errorMsg = ":请求返回:" + statusCode + "(" + url + ")";
            logger.error(errorMsg);
            throw new Exception(errorMsg);
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * get 请求
     * @param url url
     * @param logMaxLen 日志长度限制(日志截断显示)
     * @return String
     */
    public static String doGet(String url, int logMaxLen) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            logger.debug("doGet: [{}]", url);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                String s = EntityUtils.toString(response.getEntity(), CHAR_SET);
                logger.debug("response: {}", StringUtils.left(s, logMaxLen));
                return s;
            }
            String errorMsg = ":请求返回:" + statusCode + "(" + url + ")";
            logger.error(errorMsg);
            throw new Exception(errorMsg);
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e);
        }
    }

}
