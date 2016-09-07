package com.jifenke.lepluslive.order.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.order.domain.criteria.PosOrderCriteria;
import com.jifenke.lepluslive.order.service.PosOrderService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by wcg on 16/8/30.
 */
@RestController
@RequestMapping("/manage")
public class PosOrderController {

  @Inject
  private PosOrderService posOrderService;


  @RequestMapping(value = "/pos_order", method = RequestMethod.GET)
  public ModelAndView goPosOrderListPage(HttpServletRequest request) {
    return MvUtil.go("/order/posOrderList");
  }

  @RequestMapping(value = "/pos_order", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult getOffLineOrder(@RequestBody PosOrderCriteria posOrderCriteria) {
    if (posOrderCriteria.getOffset() == null) {
      posOrderCriteria.setOffset(1);
    }
    Page page = posOrderService.findOrderByPage(posOrderCriteria, 10);
    return LejiaResult.ok(page);
  }

}
