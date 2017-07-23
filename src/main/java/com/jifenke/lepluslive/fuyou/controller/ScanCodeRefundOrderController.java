package com.jifenke.lepluslive.fuyou.controller;

import com.jifenke.lepluslive.fuyou.controller.view.ScanCodeRefundOrderViewExcel;
import com.jifenke.lepluslive.fuyou.domain.criteria.ScanCodeRefundOrderCriteria;
import com.jifenke.lepluslive.order.domain.entities.ScanCodeOrder;
import com.jifenke.lepluslive.fuyou.domain.entities.ScanCodeRefundOrder;
import com.jifenke.lepluslive.order.service.ScanCodeOrderService;
import com.jifenke.lepluslive.fuyou.service.ScanCodeRefundOrderService;
import com.jifenke.lepluslive.fuyou.service.ScanCodeStatementService;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.order.domain.entities.OffLineOrderShare;
import com.jifenke.lepluslive.order.service.ShareService;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 富友扫码退款单 Created by zhangwen on 2016/12/22.
 */
@RestController
@RequestMapping("/manage/refund")
public class ScanCodeRefundOrderController {

  @Inject
  private ScanCodeRefundOrderService scanCodeRefundOrderService;

  @Inject
  private ScanCodeOrderService orderService;

  @Inject
  private ScanCodeRefundOrderViewExcel scanCodeRefundOrderViewExcel;

  @Inject
  private ShareService shareService;

  @Inject
  private ScanCodeStatementService statementService;

  private static final Logger log = LoggerFactory.getLogger(ScanCodeRefundOrderController.class);

  /**
   * 查看退款详情  2016/12/27
   *
   * @param id 订单号
   */
  @RequestMapping(value = "/refundInfo/{id}", method = RequestMethod.GET)
  public LejiaResult refundInfo(@PathVariable String id) {
    Map<String, Object> result = new HashMap<>();
    ScanCodeOrder order = orderService.findByOrderSid(id);
    result.put("order", order);
    OffLineOrderShare orderShare = shareService.findByScanCodeOrder(order.getId());
    if (orderShare != null) {
      result.put("share", orderShare);
    }
    LeJiaUser leJiaUser = order.getLeJiaUser();
    result.put("user", leJiaUser);
    ScanCodeRefundOrder refundOrder = scanCodeRefundOrderService.findByScanCodeOrder(order);

    result.put("refundOrder", refundOrder);
    return LejiaResult.ok(result);
  }


  /**
   * 跳转到富友扫码退款单页面  16/12/20
   */
  @RequestMapping("/goRefundPage")
  public ModelAndView goRefundPage() {
    return MvUtil.go("/fuyou/refundList");
  }

  /**
   * 富友扫码退款单分页条件查询  16/12/20
   *
   * @param criteria 查询条件
   */
  @RequestMapping(value = "/orderList", method = RequestMethod.POST)
  public LejiaResult orderList(@RequestBody ScanCodeRefundOrderCriteria criteria) {
    return LejiaResult.ok(scanCodeRefundOrderService.findOrderByPage(criteria, 10));
  }

  /**
   * 导出退款单表格  16/12/28
   *
   * @param criteria 导出条件
   */
  @RequestMapping(value = "/export", method = RequestMethod.POST)
  public ModelAndView exportExcel(ScanCodeRefundOrderCriteria criteria) {
    Page page = scanCodeRefundOrderService.findOrderByPage(criteria, 10000);
    Map<String, Object> map = new HashMap<>();
    map.put("orderList", page.getContent());
    return new ModelAndView(scanCodeRefundOrderViewExcel, map);
  }

  @RequestMapping(value = "/test", method = RequestMethod.GET)
  public String test() {
    String currDay = "20161227%";
    String currDay2 = "20161227";

    List<Object[]> o1 = orderService.countTransferGroupByMerchantNum(currDay);
    List<Object[]> o2 = orderService.countTransferGroupByMerchant(currDay);

    for (Object[] object : o1) {
      try {
        statementService.createScanCodeSettleOrder(object, currDay2);
      } catch (Exception e) {
        log.error("商户号为" + object[0] + "的统计出现问题");
      }
    }

    for (Object[] object : o2) {
      try {
        statementService.createScanCodeMerchantStatement(object, currDay2);
      } catch (Exception e) {
        log.error("商户ID为" + object[0] + "的商户结算单统计出现问题");
      }
    }

    //根据昨天的已完成退款单冲正两种结算
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(sdf.parse(currDay2));
      calendar.set(Calendar.HOUR_OF_DAY, 0);
      calendar.set(Calendar.MINUTE, 0);
      calendar.set(Calendar.SECOND, 0);
      Date start = calendar.getTime();
      calendar.add(Calendar.DAY_OF_MONTH, 1);
      calendar.add(Calendar.SECOND, -1);
      Date end = calendar.getTime();
      statementService.reverseStatement(start, end, currDay2);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return "ok";
  }

}
