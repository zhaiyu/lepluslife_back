package com.jifenke.lepluslive.fuyou.controller;

import com.jifenke.lepluslive.fuyou.controller.view.ScanCodeSettleOrderViewExcel;
import com.jifenke.lepluslive.fuyou.domain.criteria.ScanCodeSettleOrderCriteria;
import com.jifenke.lepluslive.fuyou.domain.entities.ScanCodeSettleOrder;
import com.jifenke.lepluslive.fuyou.service.ScanCodeStatementService;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * 富友扫码结算单 Created by zhangwen on 2016/12/30.
 */
@RestController
@RequestMapping("/manage/statement")
public class ScanCodeStatementController {

  @Inject
  private ScanCodeSettleOrderViewExcel settleOrderViewExcel;

  @Inject
  private ScanCodeStatementService statementService;

  /**
   * 跳转到富友扫码结算单页面  16/12/30
   */
  @RequestMapping("/goStatementPage")
  public ModelAndView goStatementPage() {
    return MvUtil.go("/fuyou/statementList");
  }

  /**
   * 富友扫码结算单分页条件查询  16/12/30
   *
   * @param criteria 查询条件
   */
  @RequestMapping(value = "/orderList", method = RequestMethod.POST)
  public LejiaResult orderList(@RequestBody ScanCodeSettleOrderCriteria criteria) {
    return LejiaResult.ok(statementService.findOrderByPage(criteria, 10));
  }

  /**
   * 导出结算单表格  17/01/03
   *
   * @param criteria 导出条件
   */
  @RequestMapping(value = "/export", method = RequestMethod.POST)
  public ModelAndView exportExcel(ScanCodeSettleOrderCriteria criteria) {
    Page page = statementService.findOrderByPage(criteria, 10000);
    Map<String, Object> map = new HashMap<>();
    map.put("orderList", page.getContent());
    return new ModelAndView(settleOrderViewExcel, map);
  }

  /**
   * 设为已转账  2017/01/03
   *
   * @param id 结算单ID
   */
  @RequestMapping(value = "/setTrade/{id}", method = RequestMethod.POST)
  public LejiaResult setTrade(@PathVariable Long id) {
    ScanCodeSettleOrder settleOrder = statementService.findById(id);
    try {
      statementService.setTrade(settleOrder);
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "save error");
    }
    return LejiaResult.ok();
  }

  /**
   * 批量转账  2017/01/04
   */
  @RequestMapping(value = "/batchTransfer", method = RequestMethod.POST)
  public LejiaResult batchTransfer() {
    try {
      statementService.batchTransfer();
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "save error");
    }
    return LejiaResult.ok();
  }


}
