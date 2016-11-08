package com.jifenke.lepluslive.activity.controller;

import com.jifenke.lepluslive.activity.domain.criteria.PhoneOrderCriteria;
import com.jifenke.lepluslive.activity.domain.entities.ActivityPhoneRule;
import com.jifenke.lepluslive.activity.service.ActivityPhoneOrderService;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.activity.service.ActivityPhoneRuleService;
import com.jifenke.lepluslive.weixin.service.DictionaryService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * 手机充值 Created by zhangwen on 2016/10/26.
 */
@RestController
@RequestMapping("/manage/phone")
public class ActivityPhoneController {


  @Inject
  private ActivityPhoneRuleService phoneRuleService;

  @Inject
  private DictionaryService dictionaryService;

  @Inject
  private ActivityPhoneOrderService phoneOrderService;

  /**
   * 话费订单数据统计  16/10/27
   */
  @RequestMapping(value = "/count", method = RequestMethod.GET)
  public LejiaResult count() {
    return LejiaResult.ok(phoneOrderService.orderCount());
  }

  /**
   * 查询或修改每日话费池和特惠限购  16/10/27
   *
   * @param type   类型（1=查询|2=修改）
   * @param worth  type=1时为0，type=2时为要修改的话费池（单位/分）
   * @param limit  特惠限购次数
   * @param update 是否更新特惠活动
   */
  @RequestMapping(value = "/pool", method = RequestMethod.GET)
  public LejiaResult pool(@RequestParam Integer type, @RequestParam Integer worth,
                          @RequestParam Integer limit, @RequestParam Integer update) {

    if (type == 1) {
      String[] o = dictionaryService.findDictionaryById(48L).getValue().split("_");
      Map<Object, Object> result = new HashMap<>();
      result.put("worth", o[0]);
      result.put("limit", o[1]);
      return LejiaResult.ok(result);
    } else {
      if (worth != null && limit != null && update != null) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());

        try {
          dictionaryService.update(48L, worth + "_" + limit + "_" + date);
          return LejiaResult.ok();
        } catch (Exception e) {
          e.printStackTrace();
          return LejiaResult.build(500, "edit error");
        }
      } else {
        return LejiaResult.build(500, "params is null");
      }
    }
  }

  /**
   * 上架或下架话费产品   16/10/27
   *
   * @param id 产品ID
   */
  @RequestMapping(value = "/changeState/{id}", method = RequestMethod.GET)
  public LejiaResult changeState(@PathVariable Long id) {
    try {
      phoneRuleService.changeState(id);
      return LejiaResult.ok();
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "server error");
    }
  }

  /**
   * 新建或修改话费产品  16/10/27
   *
   * @param activityPhoneRule 话费产品信息
   */
  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public LejiaResult edit(@RequestBody ActivityPhoneRule activityPhoneRule) {

    try {
      Map result = phoneRuleService.edit(activityPhoneRule);
      if (!"200".equals("" + result.get("status"))) {
        return LejiaResult.build(500, "" + result.get("msg"));
      }
      return LejiaResult.ok(result.get("data"));
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "edit error");
    }
  }

  /**
   * 查询话费订单列表  16/10/27
   *
   * @param criteria 查询条件
   */
  @RequestMapping(value = "/orderList", method = RequestMethod.POST)
  public LejiaResult orderList(@RequestBody PhoneOrderCriteria criteria) {

    return LejiaResult.ok(phoneOrderService.findByCriteria(criteria));
  }

  /**
   * 查询某个话费产品  16/11/04
   *
   * @param id 话费产品ID
   */
  @RequestMapping(value = "/findRule/{id}", method = RequestMethod.GET)
  public LejiaResult findRule(@PathVariable Long id) {
    return LejiaResult.ok(phoneRuleService.findById(id));
  }

  /**
   * 查询话费产品列表(一次全部取出)  16/10/27
   *
   * @param state 话费产品状态  0=下线|1=上线|-1=全部
   */
  @RequestMapping(value = "/ruleList", method = RequestMethod.GET)
  public LejiaResult ruleList(@RequestParam Integer state) {

    Map map = phoneOrderService.ruleCount();
    Map<Object, Object> result = new HashMap<>();
    result.put("countList", map);
    result.put("ruleList", phoneRuleService.findListByState(state));

    return LejiaResult.ok(result);
  }

  /**
   * 分天获取订单统计数量列表  16/10/31
   *
   * @param begin  开始时间  yyyy-MM-dd
   * @param end    结束时间  yyyy-MM-dd
   * @param worth  面值
   * @param ruleId 规则ID
   */
  @RequestMapping(value = "/orderByDayList", method = RequestMethod.GET)
  public LejiaResult orderDayList(@RequestParam String begin, @RequestParam String end,
                                  @RequestParam Integer worth,
                                  @RequestParam Long ruleId) {

    return LejiaResult.ok(phoneOrderService.orderByDayList(begin, end, worth, ruleId));
  }


}
