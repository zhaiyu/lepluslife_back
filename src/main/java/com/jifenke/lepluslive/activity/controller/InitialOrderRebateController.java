package com.jifenke.lepluslive.activity.controller;

import com.jifenke.lepluslive.activity.domain.criteria.RebateActivityCriteria;
import com.jifenke.lepluslive.activity.domain.criteria.RebateActivityLogCriteria;
import com.jifenke.lepluslive.activity.service.InitialOrderRebateActivityService;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by wcg on 16/9/12.
 */
@RestController
@RequestMapping("/manage/activity")
public class InitialOrderRebateController {

  @Inject
  private InitialOrderRebateActivityService initialOrderRebateActivityService;

  @RequestMapping(value = "/initial_order_rebate")
  public ModelAndView goFirstOrderRebatePage() {
    return MvUtil.go("/activity/initialOrderRebateList");
  }


  @RequestMapping(value = "/initial_order_rebate", method = RequestMethod.POST)
  public LejiaResult findMerchantOrderRebateByCriteria(
      @RequestBody RebateActivityCriteria criteria) {
    Map results = new HashMap<>();
    List
        content =
        initialOrderRebateActivityService.findMerchantOrderRebateActivityByCriteria(criteria);
    results.put("content", content);
    Long
        totalElements =
        initialOrderRebateActivityService.countMerchantOrderRebateActivityByCriteria(criteria);
    results.put("totalElements", totalElements);
    results.put("pages", (long) Math.ceil(totalElements / 10.0));
    return LejiaResult.ok(results);
  }

  @RequestMapping(value = "/initial_order_rebate/join", method = RequestMethod.POST)
  public LejiaResult joinActivity(@RequestBody Map<String, String> map) {
    initialOrderRebateActivityService.merchantJoinActivity(map);

    return LejiaResult.ok();
  }

  @RequestMapping(value = "/initial_order_rebate/{sid}", method = RequestMethod.GET)
  public LejiaResult findInitialOrderRebateActivityByMerchant(@PathVariable String sid) {
    return LejiaResult
        .ok(initialOrderRebateActivityService.findInitialOrderRebateActivityByMerchant(sid));
  }

  @RequestMapping(value = "/initial_order_rebate/quit/{sid}", method = RequestMethod.GET)
  public LejiaResult quitInitialOrderRebateActivity(@PathVariable String sid) {
    initialOrderRebateActivityService.quitActivity(sid);
    return LejiaResult
        .ok();
  }

  @RequestMapping(value = "/initial_order_rebate_log", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult getOffLineOrder(@RequestBody RebateActivityLogCriteria criteria) {
    return LejiaResult
        .ok(initialOrderRebateActivityService.findAllRebateActivityLogByCriteria(criteria, 10));
  }

}
