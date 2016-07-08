package com.jifenke.lepluslive.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jifenke.lepluslive.global.util.DataUtils;
import com.jifenke.lepluslive.user.domain.entities.WeiXinUser;
import com.jifenke.lepluslive.user.repository.WeiXinUserRepository;
import com.jifenke.lepluslive.weixin.service.DictionaryService;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * Created by wcg on 16/4/15.
 */
@Service
@Transactional(readOnly = true)
public class WeiXinUserService {

  @Inject
  private WeiXinUserRepository weiXinUserRepository;

  @Inject
  private DictionaryService dictionaryService;

  //按条件批量修改群发余额
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editMassRemain(List<WeiXinUser> userList) {

    Date date = new Date();
    for (WeiXinUser user : userList) {
      Integer remain = user.getMassRemain();
      Date lastSendDate = user.getSendMassDate();
      if (remain == null || lastSendDate == null) {
        user.setMassRemain(3);
        user.setSendMassDate(date);
        weiXinUserRepository.saveAndFlush(user);
      } else if (remain > 0) {
        //判断今天是否已经给该用户发送过消息
        boolean isSameDay = DataUtils.isSameDay(date, user.getSendMassDate());
        if (!isSameDay) {
          user.setMassRemain(remain - 1);
          weiXinUserRepository.saveAndFlush(user);
        }
      }
    }
  }

  //批量修改群发余额
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editAllMassRemain() {

    int number = weiXinUserRepository.editAllMassRemain();
  }

  //获取乐加关注用户数
  public Map countSubWxUsers() {
    try {
      //获取token
      String token = dictionaryService.findDictionaryById(7L).getValue();

      String
          getUrl =
          "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + token;
      CloseableHttpClient httpclient = HttpClients.createDefault();
      HttpGet httpGet = new HttpGet(getUrl);
      CloseableHttpResponse response = null;

      response = httpclient.execute(httpGet);
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

}
