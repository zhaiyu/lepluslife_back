package com.jifenke.lepluslive.weixin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jifenke.lepluslive.weixin.domain.entities.Menu;
import com.jifenke.lepluslive.weixin.repository.MenuRepository;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by zhangwen on 2016/5/25.
 */
@Service
@Transactional(readOnly = true)
public class WeiXinService {

  @Inject
  private DictionaryService dictionaryService;


  /**
   * 生成永久二维码
   *
   * @return 状态码
   */
  public Map createForeverQrCode(String parameter) {

    // 先封装一个 JSON 对象
//    JSONObject param = new JSONObject();
//    HashMap<String, String> action_name = new HashMap<>();
//    HashMap<String, Object> action_info = new HashMap<>();
//    HashMap<String, Object> scene = new HashMap<>();
//    HashMap<String, String> scene_str = new HashMap<>();
//    action_name.put("action_name", "QR_LIMIT_STR_SCENE");
//    scene_str.put("scene_str", parameter);
//    scene.put("scene", scene_str);
//    param.put("action_name", action_name);
//    param.put("action_info", action_info);

    String
        str =
        "{\"action_name\":\"QR_LIMIT_STR_SCENE\",\"action_info\":{\"scene\":{\"scene_str\":\""
        + parameter + "\"}}}";
    try {
      // 绑定到请求 Entry
      StringEntity
          se =
          new StringEntity(new String(str.getBytes("utf8"), "iso8859-1"));

      //获取token
      String token = dictionaryService.findDictionaryById(7L).getValue();
      String
          postUrl =
          "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;
      CloseableHttpClient httpclient = HttpClients.createDefault();
      HttpPost httpPost = new HttpPost(postUrl);
      httpPost.addHeader("Content-Type", "application/json");
      httpPost.setEntity(se);

      CloseableHttpResponse response = httpclient.execute(httpPost);
      HttpEntity entity = response.getEntity();
      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object>
          map =
          mapper.readValue(
              new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8")),
              Map.class);
      EntityUtils.consume(entity);
      response.close();
      return map;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
