package com.jifenke.lepluslive.order.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.global.util.PaginationUtil;
import com.jifenke.lepluslive.order.service.OrderService;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

/**
 * Created by wcg on 16/4/6.
 */
@RestController
@RequestMapping("/manage")
public class OrderController {

  @Inject
  private OrderService orderService;

  @RequestMapping("/order")
  public ModelAndView findOrderByPage(
        @RequestParam(value = "page", required = false) Integer offset,
        Model model) {
      Page
          page =
          orderService.findOrderByPage(offset);
      model.addAttribute("orders", page.getContent());
      model.addAttribute("pages", page.getTotalPages());
      if (offset == null) {
        offset = 1;
      }
      model.addAttribute("currentPage", offset);
      return MvUtil.go("/order/orderList");
  }

  @RequestMapping("/orderCancle/{id}")
  public LejiaResult cancleOrder(@PathVariable Long id){
    orderService.cancleOrder(id);
    return LejiaResult.build(200,"取消订单成功");
  }

  @RequestMapping("/order/delivery/{id}")
  public LejiaResult orderDelivery(@PathVariable Long id){
    orderService.orderDelivery(id);
    return LejiaResult.build(200,"发货成功");
  }

}
