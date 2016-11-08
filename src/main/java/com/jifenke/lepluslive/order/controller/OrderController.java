package com.jifenke.lepluslive.order.controller;


import com.jifenke.lepluslive.Address.domain.entities.Address;
import com.jifenke.lepluslive.global.util.JsonUtils;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.criteria.MerchantCriteria;
import com.jifenke.lepluslive.order.controller.dto.ExpressDto;
import com.jifenke.lepluslive.order.controller.view.OnLineOrderViewExcel;
import com.jifenke.lepluslive.order.domain.criteria.OrderCriteria;
import com.jifenke.lepluslive.order.domain.entities.ExpressInfo;
import com.jifenke.lepluslive.order.domain.entities.OnLineOrder;
import com.jifenke.lepluslive.order.service.ExpressInfoService;
import com.jifenke.lepluslive.order.service.OrderService;
import com.jifenke.lepluslive.user.domain.entities.WeiXinUser;
import com.jifenke.lepluslive.weixin.domain.entities.Dictionary;
import com.jifenke.lepluslive.weixin.service.DictionaryService;
import com.jifenke.lepluslive.weixin.service.WxTemMsgService;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  @Inject
  private DictionaryService dictionaryService;

  @Inject
  private OnLineOrderViewExcel orderViewExcel;

  @RequestMapping(value = "/order", method = RequestMethod.GET)
  public ModelAndView findOrderByPage(Model model) {
    model.addAttribute("state", 5);
    model.addAttribute("backA", dictionaryService.findDictionaryById(3L).getValue());
    return MvUtil.go("/order/onLineOrderList");
  }

  /**
   * ajax获取线上订单数据 16/09/26
   *
   * @param orderCriteria 筛选条件及分页
   */
  @RequestMapping(value = "/order/onLineOrderList", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult userList(@RequestBody OrderCriteria orderCriteria) {
    Page page = orderService.findOrderByPage(orderCriteria, 10);
    return LejiaResult.ok(page);

  }

  @RequestMapping("/orderCancle/{id}")
  public LejiaResult cancleOrder(@PathVariable Long id) {
    orderService.cancleOrder(id);
    return LejiaResult.build(200, "取消订单成功");
  }

  /**
   * 线上自提订单的确认完成  16/09/26
   */
  @RequestMapping("/finishOrder/{id}")
  public LejiaResult finishOrder(@PathVariable Long id) {
    orderService.finishOrder(id);
    return LejiaResult.build(200, "确认订单成功");
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
        wxTemMsgService.sendTemMessage(weiXinUser.getOpenId(), 1L, keys, order.getId());
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
    ExpressInfo expressInfo = null;
    //调接口获取物流信息，存入数据库
    if (order.getExpressNumber() != null && !"".equals(order.getExpressNumber())) {
      expressInfo = expressInfoService.findExpressAndSave(order);
    }

    if (expressInfo != null) {
      List<ExpressDto>
          expressDtoList =
          JsonUtils.jsonToList(expressInfo.getContent(), ExpressDto.class);
      model.addAttribute("expressList", expressDtoList);
    }

    model.addAttribute("expressCompany", order.getExpressCompany());
    model.addAttribute("expressNumber", order.getExpressNumber());

    return MvUtil.go("/order/orderExpress");
  }

  @RequestMapping(value = "/order/export", method = RequestMethod.GET)
  public ModelAndView exporeExcel(@RequestParam String condition) {
    OrderCriteria criteria = JsonUtils.jsonToPojo(condition, OrderCriteria.class);
    if (criteria.getOffset() == null) {
      criteria.setOffset(1);
    }
    Page page = orderService.findOrderByPage(criteria, 10000);
    Map map = new HashMap();
    map.put("orderList", page.getContent());
    return new ModelAndView(orderViewExcel, map);
  }
}
