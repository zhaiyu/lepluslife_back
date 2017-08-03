package com.jifenke.lepluslive.yibao.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.merchant.service.MerchantUserService;
import com.jifenke.lepluslive.yibao.controller.view.LedgerSettlementExcel;
import com.jifenke.lepluslive.yibao.domain.criteria.LedgerSettlementCriteria;
import com.jifenke.lepluslive.yibao.domain.entities.LedgerSettlement;
import com.jifenke.lepluslive.yibao.service.LedgerSettlementService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 易宝通道结算单
 * Created by xf on 17-7-13.
 */
@RestController
@RequestMapping("/manage/settlement")
public class LedgerSettlementController {

  @Inject
  private LedgerSettlementService ledgerSettlementService;

  @Inject
  private LedgerSettlementExcel ledgerSettlementExcel;

  @Inject
  private MerchantUserService merchantUserService;

  /**
   * 跳转到列表页面
   * Created by xf on 2017-07-13.
   */
  @RequestMapping(value = "/ledger/list", method = RequestMethod.GET)
  public ModelAndView toListPage() {
    return MvUtil.go("/yibao/ledger/ledgerSettlementList");
  }

  @RequestMapping(value = "/ledger/findByCriteria", method = RequestMethod.POST)
  @ResponseBody
  public LejiaResult findByCriteria(@RequestBody LedgerSettlementCriteria settlementCriteria) {
    if (settlementCriteria.getOffset() == null) {
      settlementCriteria.setOffset(1);
    }
    Page<LedgerSettlement> page = ledgerSettlementService.findByCriteria(settlementCriteria, 10);
    List<LedgerSettlement> settlements = page.getContent();
    List<MerchantUser> merchantUsers = new ArrayList<>();
    for (LedgerSettlement settlement : settlements) {
      Long merchantUserId = settlement.getMerchantUserId();
      MerchantUser mu = merchantUserService.findById(merchantUserId);
      merchantUsers.add(mu);
    }
    Map map = new HashMap();
    map.put("page", page);
    map.put("merchantUsers", merchantUsers);
    return LejiaResult.ok(map);
  }

  /**
   * 结算结果查询 2017/8/3
   *
   * @param settlementId 结算单ID
   */
  @RequestMapping(value = "/querySettlement", method = RequestMethod.GET)
  public Map querySettlement(@RequestParam Long settlementId) {
    return ledgerSettlementService.querySettlement(settlementId);
  }

  /**
   * 导出 Excel
   * Created by xf on 2017-07-14.
   */
  @RequestMapping(value = "/ledger/export", method = RequestMethod.POST)
  public ModelAndView export(LedgerSettlementCriteria settlementCriteria) {
    if (settlementCriteria.getOffset() == null) {
      settlementCriteria.setOffset(1);
    }
    Page<LedgerSettlement> page = ledgerSettlementService.findByCriteria(settlementCriteria, 10000);
    Map map = new HashMap();
    map.put("ledgerSettlementList", page.getContent());
    return new ModelAndView(ledgerSettlementExcel, map);
  }
}
