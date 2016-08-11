package com.jifenke.lepluslive.activity.controller;

import com.jifenke.lepluslive.activity.domain.entities.ActivityCodeBurse;
import com.jifenke.lepluslive.activity.service.ActivityCodeBurseService;
import com.jifenke.lepluslive.activity.service.ActivityJoinLogService;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.user.domain.criteria.LeJiaUserCriteria;

import org.springframework.data.domain.Page;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

/**
 * Created by wcg on 16/3/17.
 */
@RestController
@RequestMapping("/manage")
public class ActivityCodeBurseController {


  @Inject
  private ActivityCodeBurseService activityCodeBurseService;

  @Inject
  private ActivityJoinLogService activityJoinLogService;

  @RequestMapping(value = "/codeBurse", method = RequestMethod.GET)
  public ModelAndView goShowCodeBursePage() {
    return MvUtil.go("/activity/codeBurseList");
  }

  @RequestMapping(value = "/codeBurse/ajaxList", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult getCodeBurses(@RequestParam Integer offset) {
    if (offset == null) {
      offset = 1;
    }
    Page page = activityCodeBurseService.findByPage(offset, 10);

    return LejiaResult.ok(page);
  }

  @RequestMapping(value = "/codeBurse/changeState/{id}", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult changeState(@PathVariable Long id) {
    activityCodeBurseService.changeState(id);
    return LejiaResult.ok();
  }

  @RequestMapping(value = "/codeBurse/find/{id}", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult findCodeBurse(@PathVariable Long id) {
    ActivityCodeBurse codeBurse = activityCodeBurseService.findCodeBurseById(id);
    return LejiaResult.ok(codeBurse);
  }

  @RequestMapping(value = "/codeBurse/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult editCodeBurse(@RequestBody ActivityCodeBurse codeBurse) {
    int status = activityCodeBurseService.editCodeBurse(codeBurse);
    return LejiaResult.build(status, "ok");
  }

  //永久二维码送红包活动参加人员列表
  @RequestMapping(value = "/codeJoin/{id}", method = RequestMethod.GET)
  public ModelAndView goCodeJoinLogPage(@PathVariable Long id, Model model) {
    model.addAttribute("id", id);
    return MvUtil.go("/activity/codeJoinList");
  }

  //永久二维码送红包活动参加人员列表异步数据
  @RequestMapping(value = "/codeJoin/ajaxList/{id}", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult getCodeJoinUsers(@PathVariable Long id,
                               @RequestBody LeJiaUserCriteria leJiaUserCriteria) {
    if (leJiaUserCriteria.getOffset() == null) {
      leJiaUserCriteria.setOffset(1);
    }
    Page page = activityJoinLogService.findByPage(id,leJiaUserCriteria, 10);

    return LejiaResult.ok(page);
  }

}
