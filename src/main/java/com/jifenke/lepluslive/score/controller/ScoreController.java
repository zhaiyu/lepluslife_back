package com.jifenke.lepluslive.score.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.score.domain.criteria.ScoreCriteria;
import com.jifenke.lepluslive.score.domain.entities.ScoreA;

import com.jifenke.lepluslive.score.domain.entities.ScoreB;

import com.jifenke.lepluslive.score.service.ScoreAService;
import com.jifenke.lepluslive.score.service.ScoreBService;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import com.jifenke.lepluslive.user.service.UserService;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import javax.inject.Inject;


/**
 * 积分和红包的查询 Created by zhangwen on 2016/5/11.
 */
@Controller
@RequestMapping("/manage/score")
public class ScoreController {

  @Inject
  private ScoreAService scoreAService;

  @Inject
  private ScoreBService scoreBService;

  @Inject
  private UserService leJiaUserService;

  @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
  public ModelAndView listA(@PathVariable Long id, Model model) {
    LeJiaUser leJiaUser = leJiaUserService.findUserById(id);
    //累计获得红包和积分
    ScoreA scoreA = scoreAService.findScoreAByWeiXinUser(leJiaUser);
    ScoreB scoreB = scoreBService.findScoreBByWeiXinUser(leJiaUser);
    model.addAttribute("user", leJiaUser);
    model.addAttribute("id", id);
    model.addAttribute("scoreA", scoreA != null ? scoreA.getTotalScore() : -1);
    model.addAttribute("scoreAID", scoreA != null ? scoreA.getId() : -1);
    model.addAttribute("scoreB", scoreB != null ? scoreB.getTotalScore() : -1);
    model.addAttribute("scoreBID", scoreB != null ? scoreB.getId() : -1);
    return MvUtil.go("/score/scoreList");
  }

  @RequestMapping(value = "/listA", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult listA(@RequestBody ScoreCriteria scoreCriteria) {
    if (scoreCriteria.getOffset() == null) {
      scoreCriteria.setOffset(1);
    }
    Page page = scoreAService.findScoreADetailByPage(scoreCriteria, 10);
    return LejiaResult.ok(page);
  }

  @RequestMapping(value = "/listB", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult listB(@RequestBody ScoreCriteria scoreCriteria) {
    if (scoreCriteria.getOffset() == null) {
      scoreCriteria.setOffset(1);
    }
    Page page = scoreBService.findScoreBDetailByPage(scoreCriteria, 10);
    return LejiaResult.ok(page);
  }


}
