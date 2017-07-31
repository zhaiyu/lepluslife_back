package com.jifenke.lepluslive.yibao.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.yibao.controller.view.LedgerTransferExcel;
import com.jifenke.lepluslive.yibao.domain.criteria.LedgerTransferCriteria;
import com.jifenke.lepluslive.yibao.domain.entities.LedgerTransfer;
import com.jifenke.lepluslive.yibao.service.LedgerTransferService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.Map;

/**
 * 转账记录
 * Created by xf on 17-7-14.
 */
@RestController
@RequestMapping("/manage/transfer")
public class LedgerTransferController {

  @Inject
  private LedgerTransferService ledgerTransferService;

  @Inject
  private LedgerTransferExcel ledgerTransferExcel;

  /**
   * 手动补单  2017/7/28
   *
   * @param transferId 转账单ID
   */
  @RequestMapping(value = "/retry", method = RequestMethod.POST)
  public Map queryModify(@RequestParam Long transferId) {
    Map<String, String> map = null;
    try {
      map = ledgerTransferService.transferRetry(transferId);
      if (map == null) {
        map = new HashMap<>();
        map.put("code", "1001");
        map.put("msg", "失败时才可补单");
        return map;
      }
      return map;
    } catch (Exception e) {
      e.printStackTrace();
      map = new HashMap<>();
      map.put("code", "1001");
      map.put("msg", "server error");
      return map;
    }
  }

  /**
   * 跳转到列表页面
   * Created by xf on 2017-07-13.
   */
  @RequestMapping(value = "/ledger/list", method = RequestMethod.GET)
  public ModelAndView toListPage() {
    return MvUtil.go("/yibao/ledger/transferList");
  }

  @RequestMapping(value = "/ledger/findByCriteria", method = RequestMethod.POST)
  @ResponseBody
  public LejiaResult findByCriteria(@RequestBody LedgerTransferCriteria transferCriteria) {
    if (transferCriteria.getOffset() == null) {
      transferCriteria.setOffset(1);
    }
    Page<LedgerTransfer> page = ledgerTransferService.findByCriteria(transferCriteria, 10);
    return LejiaResult.ok(page);
  }


  /**
   * 导出 Excel
   * Created by xf on 2017-07-14.
   */
  @RequestMapping(value = "/ledger/export", method = RequestMethod.POST)
  public ModelAndView export(LedgerTransferCriteria transferCriteria) {
    if (transferCriteria.getOffset() == null) {
      transferCriteria.setOffset(1);
    }
    Page<LedgerTransfer> page = ledgerTransferService.findByCriteria(transferCriteria, 10000);
    Map map = new HashMap();
    map.put("ledgerTransferList", page.getContent());
    return new ModelAndView(ledgerTransferExcel, map);
  }

}
