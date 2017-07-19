package com.jifenke.lepluslive.order.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.order.controller.view.ScanCodeOrderViewExcel;
import com.jifenke.lepluslive.order.domain.criteria.ScanCodeOrderCriteria;
import com.jifenke.lepluslive.order.service.ScanCodeOrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * 渠道支付订单表 Created by zhangwen on 2016/12/6.
 */
@RestController
@RequestMapping("/manage/scanCodeOrder")
public class ScanCodeOrderController {

  private static Logger log = LoggerFactory.getLogger(ScanCodeOrderController.class);

  @Inject
  private ScanCodeOrderService orderService;

  @Inject
  private ScanCodeOrderViewExcel scanCodeOrderViewExcel;

  /**
   * 跳转到富友扫码订单页面  16/12/20
   */
  @RequestMapping("/goOrderPage")
  public ModelAndView goOrderPage() {
    return MvUtil.go("/order/scanCodeOrderList");
  }

  /**
   * 富友扫码订单分页条件查询  16/12/20
   *
   * @param criteria 查询条件
   */
  @RequestMapping(value = "/orderList", method = RequestMethod.POST)
  public LejiaResult orderList(@RequestBody ScanCodeOrderCriteria criteria) {
    return LejiaResult.ok(orderService.findOrderByPage(criteria, 10));
  }

  /**
   * 导出扫码订单表格  16/12/20
   *
   * @param criteria 导出条件
   */
  @RequestMapping(value = "/export", method = RequestMethod.POST)
  public ModelAndView exportExcel(ScanCodeOrderCriteria criteria) {
    Page page = orderService.findOrderByPage(criteria, 10000);
    Map<String, Object> map = new HashMap<>();
    map.put("orderList", page.getContent());
    return new ModelAndView(scanCodeOrderViewExcel, map);
  }

  /**
   * 点击退款  2016/12/22
   *
   * @param id 订单号
   */
  @RequestMapping(value = "/refund/{id}", method = RequestMethod.GET)
  public LejiaResult create(@PathVariable String id) {
    Map<String, Object> result = orderService.getRefundInfo(id);
    return LejiaResult.ok(result);
  }

  /**
   * 退款提交   2016/12/23
   *
   * @param orderSid 要退款的订单号
   * @param force    是否强制退款
   */
  @RequestMapping(value = "/refundSubmit", method = RequestMethod.POST)
  public Map refundSubmit(@RequestParam String orderSid, @RequestParam Integer force) {
    return orderService.refundSubmit(orderSid, force);
  }

}
