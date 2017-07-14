package com.jifenke.lepluslive.yibao.util;

import com.jifenke.lepluslive.global.config.YBConstants;
import com.jifenke.lepluslive.global.util.JsonUtils;

import java.util.Map;

/**
 * Created by zhangwen on 2017/7/13.
 */
public class DataUtil {

  // data 生成代码
  public static String getData(Map<String, String> dataMap, String[] stringArray) {

// 生成hmac
    String hmac = getHmac(stringArray, YBConstants.SECRET);
    dataMap.put("hmac", hmac);
// 将map转换成一个json串
    String dataJsonString = JsonUtils.objectToJson(dataMap);

    System.out.println("json字符串======" + dataJsonString);

    //生成data
    return AESUtil.encrypt(dataJsonString, YBConstants.SECRET_16);
  }

  private static String getHmac(String[] arr, String keyValue) {
    String hmac = "";
    StringBuffer stringValue = new StringBuffer();
    for (int i = 0; i < arr.length; i++) {
      stringValue.append((arr[i] == null) ? "" : arr[i].trim());
    }
    hmac = Digest.hmacSign(stringValue.toString(), keyValue);
    return (hmac);
  }

}
