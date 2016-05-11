package com.jifenke.lepluslive.order.controller;


import com.jifenke.lepluslive.Address.domain.entities.Address;
import com.jifenke.lepluslive.global.util.JsonUtils;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.order.controller.dto.ExpressDto;
import com.jifenke.lepluslive.order.domain.criteria.OrderCriteria;
import com.jifenke.lepluslive.order.domain.entities.ExpressInfo;
import com.jifenke.lepluslive.order.domain.entities.OnLineOrder;
import com.jifenke.lepluslive.order.service.ExpressInfoService;
import com.jifenke.lepluslive.order.service.OrderService;
import com.jifenke.lepluslive.user.domain.entities.WeiXinUser;
import com.jifenke.lepluslive.weixin.service.WxTemMsgService;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.inject.Inject;

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

  @Inject
  private WxTemMsgService wxTemMsgService;

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
    OnLineOrder order = orderService.findOnLineOrderById(onLineOrder.getId());
    //state=1 修改物流信息  0=确认发货
    if (onLineOrder.getState() == 0) {
      orderService
          .orderDelivery(order, onLineOrder.getExpressCompany(), onLineOrder.getExpressNumber());

      //如果用户有对应的微信信息，则异步发送一个模板消息
      WeiXinUser weiXinUser = order.getLeJiaUser().getWeiXinUser();
      if (weiXinUser != null) {
        Address address = order.getAddress();
        String[] keys = new String[4];
        keys[0] = "订单号(" + order.getOrderSid() + ")";
        keys[1] = order.getExpressCompany();
        keys[2] = order.getExpressNumber();
        keys[3] =
            address.getName() + " " + address.getCity() + address.getCounty() + address
                .getLocation();
        wxTemMsgService.sendTemMessage(weiXinUser.getOpenId(), 1L, keys,order.getId());
      }
    } else if (onLineOrder.getState() == 1) {
      orderService
          .orderEditDelivery(order, onLineOrder.getExpressCompany(),
                             onLineOrder.getExpressNumber());
    }

    return LejiaResult.build(200, "成功");
  }


  /**
   * 查看物流信息
   */
  @RequestMapping(value = "/showExpress/{id}", method = RequestMethod.GET)
  public ModelAndView showExpress(@PathVariable Long id, Model model) {

    OnLineOrder order = orderService.findOnLineOrderById(id);

    //调接口获取物流信息，存入数据库
    ExpressInfo expressInfo = expressInfoService.findExpressAndSave(order);

    List<ExpressDto>
        expressDtoList =
        JsonUtils.jsonToList(expressInfo.getContent(), ExpressDto.class);
    model.addAttribute("expressList", expressDtoList);

    model.addAttribute("expressCompany", order.getExpressCompany());
    model.addAttribute("expressNumber", order.getExpressNumber());

    return MvUtil.go("/order/orderExpress");
  }

}
