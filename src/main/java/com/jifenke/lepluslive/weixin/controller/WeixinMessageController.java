package com.jifenke.lepluslive.weixin.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.user.domain.criteria.LeJiaUserCriteria;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import com.jifenke.lepluslive.user.domain.entities.WeiXinUser;
import com.jifenke.lepluslive.user.service.UserService;
import com.jifenke.lepluslive.user.service.WeiXinUserService;
import com.jifenke.lepluslive.weixin.domain.criteria.MessageCriteria;
import com.jifenke.lepluslive.weixin.service.DictionaryService;
import com.jifenke.lepluslive.weixin.service.WeixinMessageService;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 群发接口 Created by zhangwen on 2016/7/4.
 */
@RestController
@RequestMapping("/manage/weixin")
public class WeixinMessageController {

  @Inject
  private WeixinMessageService weixinMessageService;

  @Inject
  private UserService userService;

  @Inject
  private WeiXinUserService weiXinUserService;

  @Inject
  private DictionaryService dictionaryService;

  /**
   * 获取图文素材消息页面
   */
  @RequestMapping(value = "/news/list")
  public ModelAndView list(@RequestParam Integer totalElements,
                           @RequestParam Object leJiaUserCriteria,
                           Model model) {
    model.addAttribute("type", 1);
    model.addAttribute("totalElements", totalElements);
    model.addAttribute("leJiaUserCriteria", leJiaUserCriteria);
    return MvUtil.go("/weixin/newsList");
  }

  /**
   * 获取图文素材消息页面 给所有人群发
   */
  @RequestMapping(value = "/news/allList")
  public ModelAndView allList(Model model) {
    //获取所有的粉丝数量
    Map map = weiXinUserService.countSubWxUsers();
    model.addAttribute("type", 2);
    model.addAttribute("totalElements", map.get("total"));
    return MvUtil.go("/weixin/newsList");
  }

  @RequestMapping(value = "/news/newsList", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult newsList(@RequestParam Integer offset) {
    if (offset == null) {
      offset = 1;
    }
    Map map = weixinMessageService.getAllNews("news", offset, 20);

    return LejiaResult.build(200, "ok", map);
  }

  /**
   * 群发图文消息
   */
  @RequestMapping(value = "/news/sendNews", method = RequestMethod.POST)
  public LejiaResult sendNews(
      @RequestBody LeJiaUserCriteria leJiaUserCriteria, @RequestParam String mediaId,
      @RequestParam String title) {

    Page page = userService.findAllLeJiaUserByCriteria(leJiaUserCriteria);
    List<LeJiaUser> users = page.getContent();
    List<String> openIds = new ArrayList<>();
    List<WeiXinUser> weiXinUserList = new ArrayList<>();
    for (LeJiaUser user : users) {
      WeiXinUser weiXinUser = user.getWeiXinUser();
      if (weiXinUser != null) {
        openIds.add(weiXinUser.getOpenId());
        weiXinUserList.add(weiXinUser);
      }
    }

//    openIds.add("o-1BZwOW9WOrDWlj4YyfPCHH7obQ");
//    openIds.add("o-1BZwOWFmWA3bcuWPitVbmPvkEw");

    Map map = weixinMessageService.sendNews(openIds, mediaId);

    //成功的话将发送的图文消息保存，用于接收事件补全数据并展示
    if ((Integer) map.get("errcode") == 0) {
      try {
        weixinMessageService.saveNews(map, title);

        new Thread(() -> {//对每个发送消息会员的当月数量减 1   异步处理
          weiXinUserService.editMassRemain(weiXinUserList);
        }).start();

        return LejiaResult.build(200, "发送成功");
      } catch (Exception e) {
        e.printStackTrace();
        return LejiaResult.build(201, "保存失败");
      }
    } else {
      return LejiaResult.build((Integer) map.get("errcode"), "发送失败");
    }
  }

  /**
   * 群发图文消息给所有人 is_to_all=true
   */
  @RequestMapping(value = "/news/sendNewsToAll", method = RequestMethod.POST)
  public LejiaResult sendNewsToAll(@RequestParam String mediaId, @RequestParam String title) {

    Map map = weixinMessageService.sendNewsToAll(mediaId);

    //成功的话将发送的图文消息保存，用于接收事件补全数据并展示
    if ((Integer) map.get("errcode") == 0) {
      try {
        weixinMessageService.saveNews(map, title);
        //将is_to_all=true群发时间和剩余次数更新到dictionary
        dictionaryService.updateMassTime(16L, 17L);

        //对每个发送消息会员的当月数量减 1   异步处理
        weiXinUserService.editAllMassRemain();

        return LejiaResult.build(200, "发送成功");
      } catch (Exception e) {
        e.printStackTrace();
        return LejiaResult.build(201, "保存失败");
      }
    } else {
      return LejiaResult.build((Integer) map.get("errcode"), "发送失败");
    }
  }

  @RequestMapping(value = "/imageText", method = RequestMethod.GET)
  public ModelAndView goImageTextPage() {
    return MvUtil.go("/weixin/imageTextList");
  }

  @RequestMapping(value = "/imageTextList", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult getOffLineOrder(@RequestBody MessageCriteria messageCriteria) {
    if (messageCriteria.getOffset() == null) {
      messageCriteria.setOffset(1);
    }
    Page page = weixinMessageService.findImageTextByPage(messageCriteria, 10);
    return LejiaResult.ok(page);
  }


}
