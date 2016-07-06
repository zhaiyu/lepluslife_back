package com.jifenke.lepluslive.weixin.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.user.domain.criteria.LeJiaUserCriteria;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import com.jifenke.lepluslive.user.domain.entities.WeiXinUser;
import com.jifenke.lepluslive.user.service.UserService;
import com.jifenke.lepluslive.weixin.service.WeixinMessageService;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

  /**
   * 获取图文素材消息列表
   */
  @RequestMapping(value = "/news/list")
  public ModelAndView list(@RequestParam(value = "page", required = false) Integer offset,
                           @RequestParam Integer totalElements,
                           @RequestParam Object leJiaUserCriteria,
                           Model model) {
    if (offset == null) {
      offset = 0;
    }
    Map map = weixinMessageService.getAllNews("news", offset, 20);
    model.addAttribute("news", map);
    model.addAttribute("totalElements", totalElements);
    model.addAttribute("leJiaUserCriteria", leJiaUserCriteria);
    return MvUtil.go("/weixin/newsList");
  }

  /**
   * 群发图文消息
   */
  @RequestMapping(value = "/news/sendNews", method = RequestMethod.POST)
  public LejiaResult sendNews(
      @RequestBody LeJiaUserCriteria leJiaUserCriteria, @RequestParam String mediaId) {

    Page page = userService.findAllLeJiaUserByCriteria(leJiaUserCriteria);
    List<LeJiaUser> users = page.getContent();
    List<String> openIds = new ArrayList<>();
//    for (LeJiaUser user : users) {
//      WeiXinUser weiXinUser = user.getWeiXinUser();
//      openIds.add(weiXinUser.getOpenId());
//    }

    openIds.add("o-1BZwOW9WOrDWlj4YyfPCHH7obQ");
    openIds.add("o-1BZwOWFmWA3bcuWPitVbmPvkEw");

    Map map = weixinMessageService.sendNews(openIds, mediaId);

    //成功的话将发送的图文消息保存，用于接收事件补全数据并展示
    if (Integer.valueOf((String) map.get("errcode")) == 0) {
      try {
        weixinMessageService.saveNews(map);
        //对每个发送消息会员的当月数量减一

        return LejiaResult.build(200, "发送成功");
      } catch (Exception e) {
        e.printStackTrace();
        return LejiaResult.build(201, "保存失败");
      }
    } else {
      return LejiaResult.build(Integer.valueOf((String) map.get("errcode")), "发送失败");
    }
  }

}
