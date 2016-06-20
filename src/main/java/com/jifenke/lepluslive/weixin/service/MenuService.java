package com.jifenke.lepluslive.weixin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jifenke.lepluslive.global.util.WeixinPayUtil;
import com.jifenke.lepluslive.product.domain.entities.Product;
import com.jifenke.lepluslive.weixin.domain.entities.Menu;
import com.jifenke.lepluslive.weixin.repository.MenuRepository;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by zhangwen on 2016/5/25.
 */
@Service
@Transactional(readOnly = true)
public class MenuService {

  @Inject
  private MenuRepository menuRepository;

  @Inject
  private DictionaryService dictionaryService;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<Menu> findAllMenu() {
    return menuRepository.findAllByIsDisabledOrderByParentMenuAscDisplayOrderAsc(0);
  }

//  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
//  public Page findAllMenu(Integer offset) {
//    List<Sort.Order> sortList = new ArrayList<>();
//    Sort s1 = new Sort(Sort.Direction.ASC);
//    Sort s2 = new Sort(Sort.Direction.ASC);
//    sortList.add(s1.getOrderFor("parentMenu"));
//    sortList.add(s2.getOrderFor("displayOrder"));
//    Sort sort = new Sort(sortList);
//    Page<Menu>
//        page =
//        menuRepository.findAll(new PageRequest(offset - 1, 10, sort));
//    return page;
//  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<Menu> findAllParentMenu() {
    return menuRepository.findAllByParentMenuIsNull();
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Menu findMenuById(Long id) {
    return menuRepository.findOne(id);
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteMenu(Long menuId) throws Exception {
    Menu menu = menuRepository.findOne(menuId);
    if (menu != null) {
      menuRepository.delete(menu);
    }
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editMenu(Menu menu) throws Exception {
    Menu origin = null;
    Menu parentMenu = null;
    if (menu.getId() == null) {
      origin = new Menu();
    } else {
      origin = menuRepository.findOne(menu.getId());
    }

    if (origin == null) {
      throw new RuntimeException("不存在的菜单");
    }

    if (menu.getParentMenu() != null) {
      if (menu.getParentMenu().getId() != null) {
        parentMenu = menuRepository.findOne(menu.getParentMenu().getId());
        if (parentMenu == null) {
          throw new RuntimeException("不存在的父菜单");
        }
        origin.setParentMenu(parentMenu);
      }
    }

    try {
      origin.setDisplayOrder(menu.getDisplayOrder());
      origin.setIsDisabled(menu.getIsDisabled());
      origin.setName(menu.getName());
      origin.setTriggerKeyword(menu.getTriggerKeyword());
      origin.setTriggerUrl(menu.getTriggerUrl());
      menuRepository.save(origin);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * 生成菜单
   *
   * @param buttonString 菜单参数
   * @return 状态码
   */
  public Map createWeixinMenu(String buttonString) {

    try {
      // 绑定到请求 Entry
      StringEntity
          se =
          new StringEntity(new String(buttonString.getBytes("utf8"), "iso8859-1"));

      //获取token
      String token = dictionaryService.findDictionaryById(7L).getValue();
      String
          getUrl =
          "https://api.weixin.qq.com//cgi-bin/menu/create?access_token=" + token;
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
      return map;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 删除菜单
   *
   * @return 状态码
   */
  public Map deleteWeixinMenu() {

    try {

      String token = dictionaryService.findDictionaryById(7L).getValue();
      String
          getUrl =
          "https://api.weixin.qq.com//cgi-bin/menu/delete?access_token=" + token;
      CloseableHttpClient httpclient = HttpClients.createDefault();
      HttpPost httpPost = new HttpPost(getUrl);
      httpPost.addHeader("Content-Type", "application/json");
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
      return map;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
