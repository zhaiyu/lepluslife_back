package com.jifenke.lepluslive.activity.controller;

import com.jifenke.lepluslive.activity.domain.criteria.RebateOrderCriteria;
import com.jifenke.lepluslive.activity.service.InitialOrderRebateActivityService;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;

import org.apache.shiro.crypto.hash.Hash;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
  public LejiaResult findMerchantOrderRebateByCriteria(@RequestBody RebateOrderCriteria criteria) {
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
  public LejiaResult joinActivity(@RequestBody Map map) {


    return LejiaResult.ok();
  }

}
