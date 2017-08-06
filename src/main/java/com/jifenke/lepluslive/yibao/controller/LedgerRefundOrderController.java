package com.jifenke.lepluslive.yibao.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MD5Util;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.weixin.service.DictionaryService;
import com.jifenke.lepluslive.yibao.controller.view.LedgerRefundOrderExcel;
import com.jifenke.lepluslive.yibao.domain.criteria.LedgerRefundOrderCriteria;
import com.jifenke.lepluslive.yibao.domain.entities.LedgerRefundOrder;
import com.jifenke.lepluslive.yibao.service.LedgerRefundOrderService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.Map;

/**
 * 退款单记录
 * Created by xf on 17-7-14.
 */
@RestController
@RequestMapping("/manage/refund")
public class LedgerRefundOrderController {

  @Inject
  private LedgerRefundOrderService ledgerRefundOrderService;

  @Inject
  private LedgerRefundOrderExcel ledgerSettlementExcel;

  @Inject
  private DictionaryService dictionaryService;

  /**
   * 跳转到列表页面
   * Created by xf on 2017-07-13.
   */
  @RequestMapping(value = "/ledger/list", method = RequestMethod.GET)
  public ModelAndView toListPage() {
    return MvUtil.go("/yibao/refund/refundList");
  }

  @RequestMapping(value = "/ledger/findByCriteria", method = RequestMethod.POST)
  @ResponseBody
  public LejiaResult findByCriteria(@RequestBody LedgerRefundOrderCriteria refundCriteria) {
    if (refundCriteria.getOffset() == null) {
      refundCriteria.setOffset(1);
    }
    Page<LedgerRefundOrder> page = ledgerRefundOrderService.findByCriteria(refundCriteria, 10);
    return LejiaResult.ok(page);
  }

  /**
   * 跳转到申请退款页面
   * Created by zhangwen on 2017-8-3.
   */
  @RequestMapping(value = "/ledger/refund", method = RequestMethod.GET)
  public ModelAndView refund() {
    return MvUtil.go("/yibao/refund/refund");
  }

  /**
   * 易宝 点击退款  2017/8/3
   *
   * @param orderSid 订单号
   */
  @RequestMapping(value = "/refund/{orderSid}", method = RequestMethod.GET)
  public Map create(@PathVariable String orderSid) {

    return ledgerRefundOrderService.getRefundInfo(orderSid);
  }

  /**
   * 退款提交   2016/12/23
   *
   * @param orderSid         要退款的订单号
   * @param shareRecoverType 0： 追回至余额0为止。1： 全额追回，无法追回则无法退款。
   * @param pwd              密钥
   */
  @RequestMapping(value = "/refundSubmit", method = RequestMethod.POST)
  public Map refundSubmit(@RequestParam String orderSid, @RequestParam Integer shareRecoverType,
                          @RequestParam String pwd) {
    String secret = dictionaryService.findDictionaryById(67L).getValue();
    if (MD5Util.MD5Encode(pwd.trim(), "utf-8").equals(secret)) {
      return ledgerRefundOrderService.refundCommit(orderSid, shareRecoverType);
    } else {
      Map<String, Object> map = new HashMap<>();
      map.put("status", 2000);
      map.put("msg", "密码错误");
      return map;
    }

  }

  /**
   * 导出 Excel
   * Created by xf on 2017-07-14.
   */
  @RequestMapping(value = "/ledger/export", method = RequestMethod.POST)
  public ModelAndView export(LedgerRefundOrderCriteria refundCriteria) {
    if (refundCriteria.getOffset() == null) {
      refundCriteria.setOffset(1);
    }
    Page<LedgerRefundOrder> page = ledgerRefundOrderService.findByCriteria(refundCriteria, 10000);
    Map map = new HashMap();
    map.put("ledgerRefundOrderList", page.getContent());
    return new ModelAndView(ledgerSettlementExcel, map);
  }
}
