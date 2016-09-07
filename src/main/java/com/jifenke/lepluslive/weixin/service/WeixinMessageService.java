package com.jifenke.lepluslive.weixin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jifenke.lepluslive.weixin.domain.criteria.MessageCriteria;
import com.jifenke.lepluslive.weixin.domain.entities.WeixinMessage;
import com.jifenke.lepluslive.weixin.repository.WeixinMessageRepository;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by zhangwen on 2016/7/4.
 */
@Service
@Transactional(readOnly = true)
public class WeixinMessageService {

  @Inject
  private DictionaryService dictionaryService;

  @Inject
  private WeixinMessageRepository weixinMessageRepository;

  public Page findImageTextByPage(MessageCriteria messageCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "createDate");
    return weixinMessageRepository
        .findAll(getWhereClause(messageCriteria),
                 new PageRequest(messageCriteria.getOffset() - 1, limit, sort));
  }

  public static Specification<WeixinMessage> getWhereClause(MessageCriteria messageCriteria) {
    return new Specification<WeixinMessage>() {
      @Override
      public Predicate toPredicate(Root<WeixinMessage> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();

        if (messageCriteria.getStartDate() != null && (!""
            .equalsIgnoreCase(messageCriteria.getStartDate()))) {
          predicate.getExpressions().add(
              cb.between(r.get("createDate"), new Date(messageCriteria.getStartDate()),
                         new Date(messageCriteria.getEndDate())));
        }
        return predicate;
      }
    };
  }

  /**
   * 获取素材消息列表
   *
   * @param type   素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
   * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
   * @param count  返回素材的数量，取值在1到20之间
   * @return json字符串
   */
  public Map getAllNews(String type, Integer offset, Integer count) {

    try {
      // 绑定到请求 Entry
      // 先封装一个 JSON 对象
      JSONObject param = new JSONObject();
      param.put("type", type);
      param.put("offset", offset - 1);
      param.put("count", count);
      StringEntity
          se =
          new StringEntity(new String(param.toString().getBytes("utf8"), "iso8859-1"));

      //获取token
      String token = dictionaryService.findDictionaryById(7L).getValue();

      String
          getUrl =
          "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=" + token;
      CloseableHttpClient httpclient = HttpClients.createDefault();
      HttpPost httpPost = new HttpPost(getUrl);
      httpPost.addHeader("Content-Type", "application/json");
      httpPost.setEntity(se);
      CloseableHttpResponse response = null;

      response = httpclient.execute(httpPost);
      HttpEntity entity = response.getEntity();
      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object>
          map =
          mapper.readValue(
              new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8")),
              Map.class);
      EntityUtils.consume(entity);
      response.close();
      System.out.println(map.toString());
      return map;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Map sendNews(List<String> openIds, String mediaId) {
    try {
      // 绑定到请求 Entry
      // 先封装一个 JSON 对象
      JSONObject param = new JSONObject();
      HashMap<String, Object> mpnews = new HashMap<>();
      mpnews.put("media_id", mediaId);
      param.put("touser", openIds);
      param.put("mpnews", mpnews);
      param.put("msgtype", "mpnews");
      StringEntity
          se =
          new StringEntity(new String(param.toString().getBytes("utf8"), "iso8859-1"));

      //获取token
      String token = dictionaryService.findDictionaryById(7L).getValue();

      String
          getUrl =
          "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=" + token;
      CloseableHttpClient httpclient = HttpClients.createDefault();
      HttpPost httpPost = new HttpPost(getUrl);
      httpPost.addHeader("Content-Type", "application/json");
      httpPost.setEntity(se);
      CloseableHttpResponse response = null;

      response = httpclient.execute(httpPost);
      HttpEntity entity = response.getEntity();
      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object>
          map =
          mapper.readValue(
              new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8")),
              Map.class);
      EntityUtils.consume(entity);
      response.close();
      System.out.println(map.toString());
      return map;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Map sendNewsToAll(String mediaId) {
    try {
      // 绑定到请求 Entry
      // 先封装一个 JSON 对象
      JSONObject param = new JSONObject();
      HashMap<String, Object> mpnews = new HashMap<>();
      HashMap<String, Object> filter = new HashMap<>();
      mpnews.put("media_id", mediaId);
      filter.put("is_to_all", true);
      filter.put("group_id", "1");
      param.put("filter", filter);
      param.put("mpnews", mpnews);
      param.put("msgtype", "mpnews");
      StringEntity
          se =
          new StringEntity(new String(param.toString().getBytes("utf8"), "iso8859-1"));

      //获取token
      String token = dictionaryService.findDictionaryById(7L).getValue();

      String
          getUrl =
          "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=" + token;
      CloseableHttpClient httpclient = HttpClients.createDefault();
      HttpPost httpPost = new HttpPost(getUrl);
      httpPost.addHeader("Content-Type", "application/json");
      httpPost.setEntity(se);
      CloseableHttpResponse response = null;

      response = httpclient.execute(httpPost);
      HttpEntity entity = response.getEntity();
      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object>
          map =
          mapper.readValue(
              new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8")),
              Map.class);
      EntityUtils.consume(entity);
      response.close();
      System.out.println(map.toString());
      return map;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveNews(Map map, String title) throws Exception {

    try {
      WeixinMessage weixinMessage = new WeixinMessage();
      weixinMessage.setType("news");
      weixinMessage.setTitle(title);
      weixinMessage.setStatus("wait");
      weixinMessage.setMsgID(String.valueOf(map.get("msg_id")));
      weixinMessage.setMsgDataId(String.valueOf(map.get("msg_data_id")));
      weixinMessageRepository.save(weixinMessage);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
