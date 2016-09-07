package com.jifenke.lepluslive.user.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.service.CityService;
import com.jifenke.lepluslive.order.service.OrderService;
import com.jifenke.lepluslive.partner.domain.entities.Partner;
import com.jifenke.lepluslive.score.domain.entities.ScoreA;
import com.jifenke.lepluslive.score.domain.entities.ScoreB;
import com.jifenke.lepluslive.score.service.ScoreAService;
import com.jifenke.lepluslive.score.service.ScoreBService;
import com.jifenke.lepluslive.user.controller.dto.LeJiaUserDto;
import com.jifenke.lepluslive.user.domain.criteria.LeJiaUserCriteria;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import com.jifenke.lepluslive.user.domain.entities.WeiXinUser;
import com.jifenke.lepluslive.user.service.UserService;
import com.jifenke.lepluslive.weixin.service.DictionaryService;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  @Inject
  private CityService cityService;

  @Inject
  private DictionaryService dictionaryService;


  @RequestMapping(value = "/user", method = RequestMethod.GET)
  public ModelAndView goUserManagePage(Model model) {
    //判断今日是否可以发送消息
    boolean boo = dictionaryService.checkSendMassStatus();
    model.addAttribute("status", boo ? 1 : 0);
    model.addAttribute("cities", cityService.findAllCity());
    return MvUtil.go("/user/userList");
  }

  @RequestMapping(value = "/userList", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult userList(@RequestBody LeJiaUserCriteria leJiaUserCriteria) {
    if (leJiaUserCriteria.getOffset() == null) {
      leJiaUserCriteria.setOffset(1);
    }
    Page page = userService.findLeJiaUserByPage(leJiaUserCriteria, 10);
    List<LeJiaUser> users = page.getContent();
    List<LeJiaUserDto> leJiaUserDtos = users.stream().map(leJiaUser -> {
      LeJiaUserDto leJiaUserDto = new LeJiaUserDto();
      ScoreA scoreA = scoreAService.findScoreAByWeiXinUser(leJiaUser);
      ScoreB scoreB = scoreBService.findScoreBByWeiXinUser(leJiaUser);
      WeiXinUser weiXinUser = leJiaUser.getWeiXinUser();
      try {
        BeanUtils.copyProperties(leJiaUserDto, leJiaUser);
        leJiaUserDto.setScoreA(scoreA.getScore());
        leJiaUserDto.setTotalScoreA(scoreA.getTotalScore());
        leJiaUserDto.setScoreB(scoreB.getScore());
        leJiaUserDto.setTotalScoreB(scoreB.getTotalScore());
        leJiaUserDto.setOnLineCount(orderService.countUserConsumptionTimes(leJiaUser));
        leJiaUserDto.setHeadImageUrl(weiXinUser != null ? weiXinUser.getHeadImageUrl() : null);
        leJiaUserDto.setNickname(weiXinUser != null ? weiXinUser.getNickname() : null);

        leJiaUserDto.setCity(weiXinUser != null ? weiXinUser.getCity() : null);
        leJiaUserDto.setState(weiXinUser != null ? weiXinUser.getState() : -1);
        leJiaUserDto.setSubState(
            weiXinUser != null ? weiXinUser.getSubState() != null ? weiXinUser.getSubState() : -1
                               : -1);
        leJiaUserDto.setMassRemain(
            weiXinUser != null ? weiXinUser.getMassRemain() == null ? 4 : weiXinUser.getMassRemain()
                               : -1);
        Merchant merchant = leJiaUser.getBindMerchant();
        Partner partner = leJiaUser.getBindPartner();
        leJiaUserDto.setMerchantName(merchant != null ? merchant.getName() : "");
        leJiaUserDto.setPartnerName(partner != null ? partner.getName() : "");
      } catch (Exception e) {
        e.printStackTrace();
      }
      return leJiaUserDto;
    }).collect(Collectors.toList());

    Map<String, Object> map = new HashMap<>();
    map.put("content", leJiaUserDtos);
    map.put("totalPages", page.getTotalPages());
    map.put("totalElements", page.getTotalElements());
    return LejiaResult.ok(map);
  }
}
