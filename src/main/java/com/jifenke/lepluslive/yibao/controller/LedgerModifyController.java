package com.jifenke.lepluslive.yibao.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.yibao.domain.criteria.LedgerModifyCriteria;
import com.jifenke.lepluslive.yibao.service.LedgerModifyService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * 易宝子商户修改记录
 * Created by zhangwen on 2017/7/19.
 */
@RestController
@RequestMapping("/manage/modify")
public class LedgerModifyController {

  @Inject
  private LedgerModifyService ledgerModifyService;

  /**
   * 跳转到易宝子商户修改列表页面  2017/7/19
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public ModelAndView list() {
    return MvUtil.go("/yibao/ledger/modifyList");
  }


  /**
   * ajax分页条件获取易宝子商户修改记录列表 2017/7/19
   *
   * @param criteria 查询条件
   */
  @RequestMapping(value = "/ajaxList", method = RequestMethod.POST)
  public LejiaResult ajaxList(@RequestBody LedgerModifyCriteria criteria) {
    return LejiaResult.ok(ledgerModifyService.findAllByCriteria(criteria));
  }

  /**
   * 查询修改商户记录结果  2017/7/19
   *
   * @param requestId 查询唯一请求号
   */
  @RequestMapping(value = "/queryModify", method = RequestMethod.POST)
  public Map queryModify(@RequestParam String requestId) {
    Map<String, String> map = null;
    try {
      map = ledgerModifyService.queryModify(requestId);
      System.out.println("修改结果查询=" + map);
      return map;
    } catch (Exception e) {
      e.printStackTrace();
      map = new HashMap<>();
      map.put("code", "408");
      map.put("msg", e.getMessage());
      return map;
    }
  }


}
