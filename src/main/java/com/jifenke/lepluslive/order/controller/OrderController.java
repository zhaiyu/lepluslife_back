package com.jifenke.lepluslive.order.controller;

import com.jifenke.lepluslive.global.config.Constants;
import com.jifenke.lepluslive.global.util.CookieUtils;
import com.jifenke.lepluslive.global.util.JsonUtils;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.global.util.PaginationUtil;
import com.jifenke.lepluslive.order.controller.dto.ExpressDto;
import com.jifenke.lepluslive.order.domain.criteria.OrderCriteria;
import com.jifenke.lepluslive.order.domain.entities.ExpressInfo;
import com.jifenke.lepluslive.order.domain.entities.OnLineOrder;
import com.jifenke.lepluslive.order.service.ExpressInfoService;
import com.jifenke.lepluslive.order.service.OffLineOrderService;
import com.jifenke.lepluslive.order.service.OrderService;
import com.jifenke.lepluslive.product.domain.entities.ProductSpec;
import com.sun.javafx.sg.prism.NGShape;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wcg on 16/4/6.
 */
@RestController
@RequestMapping("/manage")
public class OrderController {

  @Inject
  private OrderService orderService;

  @Inject
  private ExpressInfoService expressInfoService;

  @RequestMapping("/order")
  public ModelAndView findOrderByPage(
      @RequestParam(value = "page", required = false) Integer offset,
      @RequestParam(value = "orderSid", required = false) String orderSid,
      @RequestParam(required = false) Integer state,
      Model model) {
    OrderCriteria orderCriteria = new OrderCriteria();

    if (state != null) {
      orderCriteria.setState(state);
    }
    if (orderSid != null) {
      orderCriteria.setOrderSid(orderSid);
    }
    if (offset == null) {
      offset = 1;
    }
    Page
        page =
        orderService.findOrderByPage(offset, orderCriteria);
    model.addAttribute("orders", page.getContent());
    model.addAttribute("pages", page.getTotalPages());
    model.addAttribute("currentPage", offset);
    model.addAttribute("orderCriteria", orderCriteria);
    return MvUtil.go("/order/orderList");
  }

  @RequestMapping("/orderCancle/{id}")
  public LejiaResult cancleOrder(@PathVariable Long id) {
    orderService.cancleOrder(id);
    return LejiaResult.build(200, "取消订单成功");
  }

  @RequestMapping(value = "/order/delivery", method = RequestMethod.POST)
  public LejiaResult orderDelivery(@RequestBody OnLineOrder onLineOrder) {
    orderService.orderDelivery(onLineOrder);
    return LejiaResult.build(200, "成功");
  }


  /**
   * 查看物流信息
   */
  @RequestMapping(value = "/showExpress/{id}", method = RequestMethod.GET)
  public ModelAndView showExpress(@PathVariable Long id, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

    OnLineOrder order = orderService.findOnLineOrderById(id);
    String expressNumber = order.getExpressNumber();

    //获取cookie中的物流信息
    String expresses = CookieUtils.getCookieValue(request, "expressList");
    List<ExpressInfo> expressInfoList = null;
    if (expresses != null) {
      expressInfoList = JsonUtils.jsonToList(expresses, ExpressInfo.class);
      boolean tag = false;
      for (ExpressInfo expressInfo : expressInfoList) {

        if (expressInfo.getExpressNumber().equals(expressNumber)) {
          List<ExpressDto>
              expressDtoList =
              JsonUtils.jsonToList(expressInfo.getContent(), ExpressDto.class);
          model.addAttribute("expressList", expressDtoList);
          tag = true;
          break;
        }
      }
      if (!tag) {
        //调接口获取物流信息，如果已完成，存入数据库，并存入cookie
        ExpressInfo expressInfo = expressInfoService.findExpressAndSave(order);
        expressInfoList.add(expressInfo);
        List<ExpressDto>
            expressDtoList =
            JsonUtils.jsonToList(expressInfo.getContent(), ExpressDto.class);
        model.addAttribute("expressList", expressDtoList);
        CookieUtils
            .setCookie(request, response, "expressList", JsonUtils.objectToJson(expressInfoList),
                       Constants.COOKIE_DISABLE_TIME);
      }
    } else {
      expressInfoList = new ArrayList<ExpressInfo>();
      //调接口获取物流信息，如果已完成，存入数据库，并存入cookie
      ExpressInfo expressInfo = expressInfoService.findExpressAndSave(order);
      expressInfoList.add(expressInfo);
      List<ExpressDto>
          expressDtoList =
          JsonUtils.jsonToList(expressInfo.getContent(), ExpressDto.class);
      model.addAttribute("expressList", expressDtoList);
      CookieUtils
          .setCookie(request, response, "expressList", JsonUtils.objectToJson(expressInfoList),
                     Constants.COOKIE_DISABLE_TIME);
    }

    return MvUtil.go("/order/orderExpress");
  }

}
