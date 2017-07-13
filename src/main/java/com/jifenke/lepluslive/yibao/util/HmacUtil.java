package com.jifenke.lepluslive.yibao.util;

import com.jifenke.lepluslive.global.util.JsonUtils;

import java.util.Map;

/**
 * Created by zhangwen on 2017/7/13.
 */
public class HmacUtil {

  // data 生成代码示例-Java
  public static String getData(Map<String, String> dataMap, String[] stringArray) {
//    String payproducttype = "SALES";
// keyForHmac–商户密钥
    String keyForHmac = "jj3Q1h0H86FZ7CD46Z5Nr35p67L199WdkgETx85920n128vi2125T9KY2hzv";
// AESUtil加密与解密的密钥 ，截取商户密钥的前16位
    String keyForAes = keyForHmac.substring(0, 16);

// 生成hmac
    String hmac = getHmac(stringArray, keyForHmac);
    dataMap.put("hmac", hmac);
// 将map转换成一个json串
    String dataJsonString = JsonUtils.objectToJson(dataMap);
// 生成data
    return AESUtil.encrypt(dataJsonString, keyForAes);
  }

  public static String getHmac(String[] arr, String keyValue) {
    String hmac = "";
    StringBuffer stringValue = new StringBuffer();
    for (String s : arr) {
      stringValue.append((s == null) ? "" : s.trim());
    }
    hmac = HMacMD5.getHmacMd5Str(keyValue, stringValue.toString());
    return (hmac);
  }

}
