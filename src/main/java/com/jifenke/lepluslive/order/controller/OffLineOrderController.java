package com.jifenke.lepluslive.order.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantWeiXinUser;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.merchant.service.MerchantWeiXinUserService;
import com.jifenke.lepluslive.order.controller.view.FinancialViewExcel;
import com.jifenke.lepluslive.order.controller.view.OrderViewExcel;
import com.jifenke.lepluslive.order.domain.criteria.FinancialCriteria;
import com.jifenke.lepluslive.order.domain.criteria.OLOrderCriteria;
import com.jifenke.lepluslive.order.domain.entities.FinancialStatistic;
import com.jifenke.lepluslive.order.domain.entities.OffLineOrder;
import com.jifenke.lepluslive.order.service.OffLineOrderService;
import com.jifenke.lepluslive.weixin.service.WxTemMsgService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wcg on 16/5/9.
 */
@RestController
@RequestMapping("/manage")
public class OffLineOrderController {


  @Inject
  private OrderViewExcel orderViewExcel;

  @Inject
  private OffLineOrderService offLineOrderService;

  @Inject
  private FinancialViewExcel financialViewExcel;

  @Inject
  private WxTemMsgService wxTemMsgService;

  @Inject
  private MerchantService merchantService;

  @Inject
  private MerchantWeiXinUserService merchantWeiXinUserService;

  private StringBuffer sb = new StringBuffer();


  @RequestMapping("/offLineOrder")
  public ModelAndView offLineOrder() {
    return MvUtil.go("/order/offLineOrderList");
  }

  @RequestMapping(value = "/offLineOrder", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult getOffLineOrder(@RequestBody OLOrderCriteria olOrderCriteria) {
    Page page = offLineOrderService.findOrderByPage(olOrderCriteria, 10);
    if (olOrderCriteria.getOffset() == null) {
      olOrderCriteria.setOffset(1);
    }
    return LejiaResult.ok(page);
  }

  //查询详情跳页
  @RequestMapping(value = "/offLineOrder/messageDetailsPage", method = RequestMethod.GET)
  public ModelAndView goDetailsPage(@RequestParam String messageDetailsStr, Model model) {

    model.addAttribute("messageDetailsStr", messageDetailsStr);
    return MvUtil.go("/order/offLineMessageDetails");
  }

  @RequestMapping(value = "/offLineOrder/transferRecordDetailsPage", method = RequestMethod.GET)
  public ModelAndView transferRecordDetailsPage(@RequestParam String messageDetailsStr,
                                                Model model) {
    Map<String, String> messageDetailsMap = new HashMap<String, String>();
    messageDetailsMap.put("messageDetailsMap", messageDetailsStr);
    model.addAttribute("messageDetailsMap", messageDetailsMap);
    return MvUtil.go("/order/transferRecordDetailsPage");
  }

  //查看详情
  @RequestMapping(value = "/offLineOrder/messageDetails", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult getOffLineOrderDetails(@RequestBody OLOrderCriteria olOrderCriteria,
                                     String messageDetailsStr) {
    sb = new StringBuffer("");
    sb.append(messageDetailsStr);
    String[] stringArray = sb.toString().split("@");
    String startDate1 = stringArray[0] + " " + "00:00:00";
    String endDate1 = stringArray[0] + " " + " 23:59:59";
    String endDate = endDate1.replace("-", "/");
    String startDate = startDate1.replace("-", "/");
    olOrderCriteria.setMerchant(stringArray[1]);
    olOrderCriteria.setState(1);
    olOrderCriteria.setStartDate(startDate);
    olOrderCriteria.setEndDate(endDate);
    Page page = offLineOrderService.findOrderByPage(olOrderCriteria, 10);

    if (olOrderCriteria.getOffset() == null) {
      olOrderCriteria.setOffset(1);
    }
    return LejiaResult.ok(page);
  }

  //导出表格
  @RequestMapping(value = "/offLineOrderDetails/exportDetails", method = RequestMethod.GET)
  public ModelAndView exportDetails(String messageDetails, OLOrderCriteria olOrderCriteria) {
    if (olOrderCriteria.getOffset() == null) {
      olOrderCriteria.setOffset(1);
    }
    olOrderCriteria.setState(1);
    sb = new StringBuffer("");
    sb.append(messageDetails);
    String[] stringArray = sb.toString().split("@");
    String startDate1 = stringArray[0] + " " + "00:00:00";
    String endDate1 = stringArray[0] + " " + " 23:59:59";
    String endDate = endDate1.replace("-", "/");
    String startDate = startDate1.replace("-", "/");
    String merchant = stringArray[1];
    olOrderCriteria.setStartDate(startDate);
    olOrderCriteria.setEndDate(endDate);
    olOrderCriteria.setMerchant(stringArray[0]);
    olOrderCriteria.setMerchant(merchant);
    Page page = offLineOrderService.findOrderByPage(olOrderCriteria, 10000);
   // List<OffLineOrder> list = page.getContent();
    Map detailsMap = new HashMap();
    detailsMap.put("orderList", page.getContent());
    return new ModelAndView(orderViewExcel, detailsMap);
  }

  @RequestMapping(value = "/offLineOrder/{id}", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult changeOrderStateToPaid(@PathVariable Long id) {
    offLineOrderService.changeOrderStateToPaid(id);
    return LejiaResult.ok();
  }

  @RequestMapping(value = "/offLineOrder/export", method = RequestMethod.POST)
  public ModelAndView exporeExcel(OLOrderCriteria olOrderCriteria) {
    if (olOrderCriteria.getOffset() == null) {
      olOrderCriteria.setOffset(1);
    }
    Page page = offLineOrderService.findOrderByPage(olOrderCriteria, 10000);
    Map map = new HashMap();
    map.put("orderList", page.getContent());
    return new ModelAndView(orderViewExcel, map);
  }

  @RequestMapping(value = "/financial", method = RequestMethod.GET)
  public ModelAndView financialList() {

    return MvUtil.go("/order/financialList");
  }


  @RequestMapping(value = "/financial", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult searchFinancialBycriterial(@RequestBody FinancialCriteria financialCriteria) {
    if (financialCriteria.getOffset() == null) {
      financialCriteria.setOffset(1);
    }
    Page page = offLineOrderService.findFinancialByCirterial(financialCriteria, 10);
    return LejiaResult.ok(page);
  }


  @RequestMapping(value = "/financial/{id}", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult changeFinancialStateToTransfer(@PathVariable Long id) {
    //改变统计单状态并发送模版消息
    changefinancialTransfer(id);
    return LejiaResult.ok();
  }

  @RequestMapping(value = "/financial/hover/{id}", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult changeFinancialStateToHover(@PathVariable Long id) {
    //改变统计单状态并发送模版消息
    offLineOrderService.changeFinancialStateToHover(id);
    return LejiaResult.ok();
  }

  @RequestMapping(value = "/financial/export", method = RequestMethod.POST)
  public ModelAndView exporeExcel(FinancialCriteria financialCriteria) {
    if (financialCriteria.getOffset() == null) {
      financialCriteria.setOffset(1);
    }
    Page page = offLineOrderService.findFinancialByCirterial(financialCriteria, 10000);
    Map map = new HashMap();
    map.put("financialList", page.getContent());
    return new ModelAndView(financialViewExcel, map);
  }

  @RequiresPermissions("financial:transfer")
  @RequestMapping(value = "/financial/batchTransfer", method = RequestMethod.POST)
  public LejiaResult batchTransfer() {
    List<FinancialStatistic>
        statistics =
        offLineOrderService.findAllNonTransferFinancialStatistic();
    for (FinancialStatistic financialStatistic : statistics) {
      changefinancialTransfer(financialStatistic.getId());
    }
    return LejiaResult.ok();
  }

  @RequiresPermissions("financial:transfer")
  private void changefinancialTransfer(Long id) {
    FinancialStatistic financialStatistic = offLineOrderService.changeFinancialStateToTransfer(id);
    String s = financialStatistic.getMerchant().getMerchantBank().getBankNumber();
    String[] keys = new String[4];
    StringBuffer sb = new StringBuffer(s.substring(s.length() - 4, s.length()));
    for (int i = 0; i < s.length() - 4; i++) {
      sb.insert(0, "*");
    }
    keys[0] = sb.toString();
    sb.setLength(0);
    keys[1] = sb.append(financialStatistic.getTransferPrice() / 100.0).toString();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    keys[2] = dateFormat.format(financialStatistic.getTransferDate());
    HashMap<String, Object> mapRemark = new HashMap<>();
    sb.setLength(0);
    sb.append("点击查看详情");
    mapRemark.put("value", sb.toString());
    mapRemark.put("color", "#173177");
    HashMap<String, Object> map2 = new HashMap<>();
    map2.put("remark", mapRemark);
    List<MerchantUser>
        merchantUsers =
        merchantService.findMerchantUserByMerchant(financialStatistic
                                                       .getMerchant());
    for (MerchantUser merchantUser : merchantUsers) {
      List<MerchantWeiXinUser>
          merchantWeiXinUsers =
          merchantWeiXinUserService.findMerchantWeiXinUserByMerchantUser(merchantUser);
      for (MerchantWeiXinUser merchantWeiXinUser : merchantWeiXinUsers) {
        wxTemMsgService.sendTemMessage(merchantWeiXinUser.getOpenId(), 4L, keys,
                                       financialStatistic.getStatisticId(), map2);
      }
    }
  }

  @RequestMapping(value = "/billingDownload", method = RequestMethod.GET)
  public ModelAndView billingDownload() {

    return MvUtil.go("/order/billingDownload");
  }
  @RequestMapping(value = "/reconciliationDifferences", method = RequestMethod.GET)
  public ModelAndView reconciliationDifferences() {

      return MvUtil.go("/order/reconciliationDifferences");
}

}
