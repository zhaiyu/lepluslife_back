package com.jifenke.lepluslive.yibao.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.service.MerchantUserService;
import com.jifenke.lepluslive.yibao.domain.criteria.MerchantUserLedgerCriteria;
import com.jifenke.lepluslive.yibao.domain.entities.MerchantUserLedger;
import com.jifenke.lepluslive.yibao.service.LedgerModifyService;
import com.jifenke.lepluslive.yibao.service.MerchantUserLedgerService;
import com.jifenke.lepluslive.yibao.util.YbRequestUtils;
import com.jifenke.lepluslive.yibao.util.ZGTUtils;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 易宝子商户
 * Created by zhangwen on 2017/7/13.
 */
@RestController
@RequestMapping("/manage/ledger")
public class MerchantUserLedgerController {

  @Inject
  private MerchantUserLedgerService merchantUserLedgerService;

  @Inject
  private MerchantUserService merchantUserService;

  @Inject
  private LedgerModifyService ledgerModifyService;

  /**
   * 跳转到易宝子商户编辑页面  2017/7/13
   *
   * @param merchantUserId 乐加商户ID
   * @param ledgerId       易宝子商户ID
   */
  @RequestMapping(value = "/edit", method = RequestMethod.GET)
  public ModelAndView edit(Model model, @RequestParam Long merchantUserId,
                           @RequestParam Long ledgerId) {

    model.addAttribute("m", merchantUserService.findById(merchantUserId));
    model.addAttribute("ledgerId", ledgerId);
    if (ledgerId != null && ledgerId != 0) {
      model.addAttribute("l", merchantUserLedgerService.findById(ledgerId));
    }

    return MvUtil.go("/yibao/ledger/edit");
  }

  /**
   * 易宝子商户编辑提交  2017/7/14
   *
   * @param merchantUserLedger 易宝子商户
   */
  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public Map edit(@RequestBody MerchantUserLedger merchantUserLedger) {
    return merchantUserLedgerService.edit(merchantUserLedger);
  }

  /**
   * 易宝子商户修改结算费用承担方  2017/7/17
   *
   * @param ledgerId 易宝子商户ID
   * @param costSide 结算费用承担方  0=积分客（主商户）|1=子商户
   */
  @RequestMapping(value = "/editCostSide", method = RequestMethod.POST)
  public LejiaResult editCostSide(@RequestParam Long ledgerId, @RequestParam Integer costSide) {
    merchantUserLedgerService.editCostSide(ledgerId, costSide);
    return LejiaResult.ok();
  }

  /**
   * 跳转到易宝子商户列表页面(所有)  2017/7/19
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public ModelAndView list() {
    return MvUtil.go("/yibao/ledger/ledgerList");
  }

  /**
   * ajax分页条件获取易宝子商户列表 2017/7/17
   *
   * @param criteria 查询条件
   */
  @RequestMapping(value = "/ajaxList", method = RequestMethod.POST)
  public LejiaResult ajaxList(@RequestBody MerchantUserLedgerCriteria criteria) {
    return LejiaResult.ok(merchantUserLedgerService.findAllByCriteria(criteria));
  }

  /**
   * 易宝子商户修改回调地址 17/7/16
   */
  @RequestMapping(value = "/modifyCallBack")
  public void modifyCallBack(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    //UTF-8编码
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String data = request.getParameter("data");
    System.out.println(data);
    //解密
    Map<String, String> map = ZGTUtils.decryptData(data);
    System.out.println("易宝的异步响应：" + data);
    System.out.println("data解密后明文：" + map.toString());
    //验证签名
    if (ZGTUtils.checkHmac(map, ZGTUtils.QUERYMODIFYREQUESTAPI_RESPONSE_HMAC_ORDER)) {
      //业务处理
      ledgerModifyService.modifyCallBack(map);
      out.println("SUCCESS");
    } else {
      System.out.println("验签失败");
      out.println("FAIL");
    }
    //第四步 回写SUCCESS
    out.flush();
    out.close();
  }

  /**
   * 易宝子商户资质审核异步通知地址 17/7/19
   */
  @RequestMapping(value = "/checkCallBack")
  public void checkCallBack(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    //UTF-8编码
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String data = request.getParameter("data");
    System.out.println(data);
    //解密
    Map<String, String> map = ZGTUtils.decryptData(data);
    System.out.println("易宝的异步响应：" + data);
    System.out.println("data解密后明文：" + map.toString());
    //验证签名
    if (ZGTUtils.checkHmac(map, ZGTUtils.QUERYRECORDAPI_RESPONSE_HMAC_ORDER)) {
      //业务处理
      merchantUserLedgerService.checkCallBack(map);
      out.println("SUCCESS");
    } else {
      System.out.println("验签失败");
      out.println("FAIL");
    }
    //第四步 回写SUCCESS
    out.flush();
    out.close();
  }

  /**
   * 易宝子商户余额查询 2017/7/17
   *
   * @param ledgerNo 易宝的子商户号
   */
  @RequestMapping(value = "/queryBalance", method = RequestMethod.GET)
  public Map queryBalance(@RequestParam String ledgerNo) {
    return YbRequestUtils.queryBalance(ledgerNo);
  }

  /**
   * 分账方审核结果查询 2017/7/17
   *
   * @param ledgerId 易宝子商户ID
   */
  @RequestMapping(value = "/queryCheckRecord", method = RequestMethod.GET)
  public Map queryCheckRecord(@RequestParam Long ledgerId) {
    return merchantUserLedgerService.queryCheckRecord(ledgerId);
  }

}
