package com.jifenke.lepluslive.user.controller;

import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.order.service.OrderService;
import com.jifenke.lepluslive.score.domain.entities.ScoreA;
import com.jifenke.lepluslive.score.domain.entities.ScoreB;
import com.jifenke.lepluslive.score.service.ScoreAService;
import com.jifenke.lepluslive.score.service.ScoreBService;
import com.jifenke.lepluslive.user.controller.dto.LeJiaUserDto;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import com.jifenke.lepluslive.user.service.UserService;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.inject.Inject;

/**
 * Created by wcg on 16/4/14.
 */
@RestController
@RequestMapping("/manage")
public class UserController {

  @Inject
  private UserService userService;

  @Inject
  private ScoreAService scoreAService;

  @Inject
  private ScoreBService scoreBService;

  @Inject
  private OrderService orderService;


  @RequestMapping(value = "/user", method = RequestMethod.GET)
  public ModelAndView goUserManagePage(
      @RequestParam(value = "page", required = false) Integer offset,
      Model model) {
    Page page = userService.findUserByPage(offset);
    List<LeJiaUser> users = page.getContent();
    List<LeJiaUserDto> leJiaUserDtos =  users.stream().map(leJiaUser -> {
      LeJiaUserDto leJiaUserDto = new LeJiaUserDto();
      ScoreA scoreA = scoreAService.findScoreAByWeiXinUser(leJiaUser);
      ScoreB scoreB = scoreBService.findScoreBByWeiXinUser(leJiaUser);
      try {
        BeanUtils.copyProperties(leJiaUserDto,leJiaUser);
        leJiaUserDto.setScoreA(scoreA.getScore());
        leJiaUserDto.setTotalScoreA(scoreA.getTotalScore());
        leJiaUserDto.setScoreB(scoreB.getScore());
        leJiaUserDto.setTotalScoreB(scoreB.getTotalScore());
        leJiaUserDto.setOnLineCount(orderService.countUserConsumptionTimes(leJiaUser));
      } catch (Exception e) {
        e.printStackTrace();
      }
      return leJiaUserDto;
    }).collect(Collectors.toList());

    model.addAttribute("users", leJiaUserDtos);
    model.addAttribute("pages", page.getTotalPages());
    if (offset == null) {
      offset = 1;
    }
    model.addAttribute("currentPage", offset);
    return MvUtil.go("/user/userList");
  }
}
