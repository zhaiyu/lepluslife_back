package com.jifenke.lepluslive.yibao.util;

import com.alibaba.fastjson.JSON;
import com.jifenke.lepluslive.global.config.YBConstants;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangwen on 2017/7/13.
 */
public class UploadUtil {


  public String uploadFile(Map<String, Object> params, String baseUrl) {
    if (!params.containsKey("file")) {
      throw new IllegalArgumentException("请上传图片");
    }
    File file = (File) params.get("file");
    if (!file.exists()) {
      throw new IllegalArgumentException("上传图片不存在");
    }
    if (baseUrl == null || baseUrl.trim().length() == 0) {
      throw new IllegalArgumentException("invalid url : " + baseUrl);
    }
    String queryString = mapToQueryString(params, "utf-8");
    int index = baseUrl.indexOf("?");
    if (index > 0) {
      baseUrl += "&" + queryString;
    } else {
      baseUrl += "?" + queryString;
    }
    PostMethod postMethod = new PostMethod(baseUrl);
    try {
      FilePart fp = new FilePart("file", file);
      Part[] parts = {fp};
      MultipartRequestEntity mre = new MultipartRequestEntity(parts, postMethod.getParams());
      postMethod.setRequestEntity(mre);
      HttpClient client = new HttpClient();
      client.getHttpConnectionManager().getParams().setConnectionTimeout(50000);
      //设置连接时间
      int status = client.executeMethod(postMethod);
      if (status == HttpStatus.SC_OK) {
        return postMethod.getResponseBodyAsString();
      } else {
        throw new RuntimeException("上传请求异常");
      }
    } catch (Exception e) {
      throw new RuntimeException("上传请求异常");
    } finally {
// 释放连接
      postMethod.releaseConnection();
    }
  }

  public static String mapToQueryString(Map<String, Object> parameters, String charSet) {
    String queryString = "";
    if (parameters != null && !parameters.isEmpty()) {
      for (String key : parameters.keySet()) {
        try {
          Object value = parameters.get(key);
          if (value instanceof String) {
            queryString += key + "=" +
                           URLEncoder.encode(value == null ? "" : value.toString(), charSet) + "&";
          }
        } catch (UnsupportedEncodingException e) {
          throw new IllegalArgumentException("invalid charset : " + charSet);
        }
      }
      if (queryString.length() > 0) {
        queryString = queryString.substring(0, queryString.length() - 1);
      }
    }
    return queryString;
  }

  /**
   * @param args
   */
  public static void main(String[] args) throws URISyntaxException, MalformedURLException {
//    String[] person =
//        {"ID_CARD_FRONT", "ID_CARD_BACK", "BANK_CARD_FRONT", "PERSON_PHOTO"};
    String[] person =
        {"ID_CARD_FRONT"};
    String customernumber = "10000447996";
    String ledgerno = "10013422494";
//    String ledgerno = "10014282423";
    for (String p : person) {
      String filetype = p;
      String key = "jj3Q1h0H86FZ7CD46Z5Nr35p67L199WdkgETx85920n128vi2125T9KY2hzv";
      String keyForAes = key.substring(0, 16);
      StringBuffer signature = new StringBuffer();
      signature.append(customernumber).append(ledgerno).append(filetype);
      String hmac = Digest.hmacSign(signature.toString(), key);
      System.out.println(hmac);
      Map<String, Object> dataMap = new HashMap<>();
      dataMap.put("customernumber", customernumber);
      dataMap.put("ledgerno", ledgerno);
      dataMap.put("filetype", filetype);
      dataMap.put("hmac", hmac); // hmac 按照 properties 中声明的顺序进行签名
      String dataJsonString = JSON.toJSONString(dataMap); //map 中数据转为 json 格式
      String content = AESUtil.encrypt(dataJsonString, keyForAes);
      Map<String, Object> params = new HashMap<>();
      params.put("customernumber", customernumber);
      params.put("data", content);// 加密 json 格式数据作为 value 赋值给 data 参数
      File file = new File("C:\\Users\\root\\Desktop\\template\\yibao\\picTest\\logo.png");

      params.put("file", file);
      System.out.println(params);
      UploadUtil test = new UploadUtil();
      try {
        String
            data =
            test.uploadFile(params, YBConstants.UPLOAD_LEDGER_QUALIFICATIONS_URL);
        String result =
            AESUtil.decrypt(JSON.parseObject(data).get("data").toString(), key);
        System.out.println(result);
      } catch (Throwable e) {
        e.printStackTrace();
      }
    }
  }
}
