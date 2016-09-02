package com.jifenke.lepluslive.order.controller;

import com.jifenke.lepluslive.global.util.MvUtil;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wcg on 16/8/30.
 */
@RestController
@RequestMapping("/manage")
public class PosOrderController {

  @RequestMapping(value = "/pos_order", method = RequestMethod.GET)
  public ModelAndView goPosOrderListPage(HttpServletRequest request) {
    return MvUtil.go("/order/posOrderList");
  }

}
