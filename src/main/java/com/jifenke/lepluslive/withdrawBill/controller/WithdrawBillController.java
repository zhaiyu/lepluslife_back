package com.jifenke.lepluslive.withdrawBill.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantWallet;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantWalletLog;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.merchant.service.MerchantWalletLogService;
import com.jifenke.lepluslive.order.domain.criteria.ShareCriteria;
import com.jifenke.lepluslive.order.service.ShareService;
import com.jifenke.lepluslive.partner.domain.entities.*;
import com.jifenke.lepluslive.partner.service.PartnerService;
import com.jifenke.lepluslive.withdrawBill.controller.view.WithdrawExcel;
import com.jifenke.lepluslive.withdrawBill.domain.criteria.WithdrawBillCriteria;
import com.jifenke.lepluslive.withdrawBill.domain.entities.WithdrawBill;
import com.jifenke.lepluslive.withdrawBill.service.WithdrawBillService;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lss on 2016/8/26.
 */
@RestController
@RequestMapping("/manage")
public class WithdrawBillController {

  @Inject
  private WithdrawBillService withdrawBillService;

  @Inject
  private MerchantService merchantService;

  @Inject
  private MerchantWalletLogService merchantWalletLogService;

  @Inject
  private PartnerService partnerService;

    @Inject
    private ShareService shareService;

    @Inject
    private WithdrawExcel withdrawExcel;


  @RequestMapping("/withdrawBill")
  public ModelAndView withdrawBill() {
    return MvUtil.go("/withdraw/withdrawBillList");
  }

  @RequestMapping(value = "/withdrawBill", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult getWithdrawBill(@RequestBody WithdrawBillCriteria withdrawBillCriteria) {
    Page page = withdrawBillService.findWithdrawBillByPage(withdrawBillCriteria, 10);
    if (withdrawBillCriteria.getOffset() == null) {
      withdrawBillCriteria.setOffset(1);
    }
    return LejiaResult.ok(page);
  }

  @RequestMapping(value = "/reject/{id}", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult reject(@PathVariable Long id, String rejectNote) {
    withdrawBillService.reject(id, rejectNote);
    return LejiaResult.ok();
  }

  @RequestMapping(value = "/withdraw/{id}", method = RequestMethod.GET)
  public
  @ResponseBody
  LejiaResult withdraw(@PathVariable Long id) {
    //withdrawBillService.withdraw(id);
    System.out.println(id);
    WithdrawBill withdrawBill = withdrawBillService.findWithdrawBillById(id);
    //商户提现
    if (withdrawBill.getBillType() == 2) {
      if (withdrawBill.getMerchant() != null) {
        Merchant merchant = withdrawBill.getMerchant();
        MerchantWallet merchantWallet = merchantService.findMerchantWalletByMerchant(merchant);
        Long availableBalance = merchantWallet.getAvailableBalance();
        if (availableBalance == null) {
          availableBalance = Long.parseLong("0");
        }
        Long beforeChangeMoney = availableBalance;//改变前的钱
        Long amount = withdrawBill.getTotalPrice();
        if (amount == null) {
          amount = Long.parseLong("0");
        }
        availableBalance = availableBalance - amount;
        Long afterChangeMoney = availableBalance;//改变后的钱
        merchantWallet.setAvailableBalance(availableBalance);
        if (merchantWallet.getTotalWithdrawals() != null) {
          Long alreadyWithdraw = merchantWallet.getTotalWithdrawals();
          merchantWallet.setTotalWithdrawals(alreadyWithdraw + amount);
        } else {
          merchantWallet.setTotalWithdrawals(amount);
        }
        merchantService.saveMerchantWallet(merchantWallet);//改变商户钱包
        //改变商户钱包日志
        MerchantWalletLog merchantWalletLog = new MerchantWalletLog();
        merchantWalletLog.setAfterChangeMoney(afterChangeMoney);
        merchantWalletLog.setBeforeChangeMoney(beforeChangeMoney);
        merchantWalletLog.setCreateDate(new Date());
        merchantWalletLog.setMerchantId(merchant.getId());
        merchantWalletLog.setOrderSid(withdrawBill.getWithdrawBillSid());
        merchantWalletLog.setType(Long.parseLong("3"));
        merchantWalletLogService.saveMerchantWalletLog(merchantWalletLog);
        //改变订单状态
        withdrawBill.setCompleteDate(new Date());
        withdrawBill.setState(1);
        withdrawBillService.saveWithdrawBill(withdrawBill);
      }
    }
    //合伙人提现
    if (withdrawBill.getBillType() == 1) {
      if (withdrawBill.getPartner() != null) {
        Partner partner = withdrawBill.getPartner();
        PartnerWallet partnerWallet = partnerService.findPartnerWalletByPartner(partner);
        Long availableBalance = partnerWallet.getAvailableBalance();
        if (availableBalance == null) {
          availableBalance = Long.parseLong("0");
        }
        Long beforeChangeMoney = availableBalance;//改变前的钱
        Long amount = withdrawBill.getTotalPrice();
        if (amount == null) {
          amount = Long.parseLong("0");
        }
        availableBalance = availableBalance - amount;
        Long afterChangeMoney = availableBalance;//改变后的钱
        partnerWallet.setAvailableBalance(availableBalance);

        if (partnerWallet.getTotalWithdrawals() != null) {
          Long alreadyWithdraw = partnerWallet.getTotalWithdrawals();
          partnerWallet.setTotalWithdrawals(alreadyWithdraw + amount);
        } else {
          partnerWallet.setTotalWithdrawals(amount);
        }

        partnerService.savePartnerWallet(partnerWallet);//改变合伙人钱包
        //改变合伙人钱包日志
        PartnerWalletLog partnerWalletLog = new PartnerWalletLog();
        partnerWalletLog.setAfterChangeMoney(afterChangeMoney);
        partnerWalletLog.setBeforeChangeMoney(beforeChangeMoney);
        partnerWalletLog.setCreateDate(new Date());
        partnerWalletLog.setPartnerId(partner.getId());
        partnerWalletLog.setOrderSid(withdrawBill.getWithdrawBillSid());
        partnerWalletLog.setType(Long.parseLong("3"));
        partnerService.partnerWalletLog(partnerWalletLog);
        //改变订单状态
        withdrawBill.setCompleteDate(new Date());
        withdrawBill.setState(1);
        withdrawBillService.saveWithdrawBill(withdrawBill);
      }
    }
    //合伙人管理员提现
    if (withdrawBill.getBillType() == 0) {
      if (withdrawBill.getPartnerManager() != null) {
        PartnerManager partnerManager = withdrawBill.getPartnerManager();
        PartnerManagerWallet
            partnerManagerWallet =
            partnerService.findPartnerManagerWalletByPartnerManager(partnerManager);
        Long availableBalance = partnerManagerWallet.getAvailableBalance();
        if (availableBalance == null) {
          availableBalance = Long.parseLong("0");
        }
        Long beforeChangeMoney = availableBalance;//改变前的钱
        Long amount = withdrawBill.getTotalPrice();
        if (amount == null) {
          amount = Long.parseLong("0");
        }
        availableBalance = availableBalance - amount;
        Long afterChangeMoney = availableBalance;//改变后的钱
        partnerManagerWallet.setAvailableBalance(availableBalance);
        if (partnerManagerWallet.getTotalWithdrawals() != null) {
          Long alreadyWithdraw = partnerManagerWallet.getTotalWithdrawals();
          partnerManagerWallet.setTotalWithdrawals(alreadyWithdraw + amount);
        } else {
          partnerManagerWallet.setTotalWithdrawals(amount);
        }
        partnerService.savePartnerManagerWallet(partnerManagerWallet);//改变合伙人管理员的钱包
        //改变合伙人管理员的钱包日志
        PartnerManagerWalletLog partnerManagerWalletLog = new PartnerManagerWalletLog();
        partnerManagerWalletLog.setAfterChangeMoney(afterChangeMoney);
        partnerManagerWalletLog.setBeforeChangeMoney(beforeChangeMoney);
        partnerManagerWalletLog.setCreateDate(new Date());
        partnerManagerWalletLog.setPartnerManagerId(partnerManager.getId());
        partnerManagerWalletLog.setOrderSid(withdrawBill.getWithdrawBillSid());
        partnerManagerWalletLog.setType(Long.parseLong("3"));
        partnerService.savePartnerManagerWalletLog(partnerManagerWalletLog);
        //改变订单状态
        withdrawBill.setCompleteDate(new Date());
        withdrawBill.setState(1);
        withdrawBillService.saveWithdrawBill(withdrawBill);
      }
    }

    return LejiaResult.ok();
  }


    @RequestMapping(value = "/withdrawBill/shareDetailsPage", method = RequestMethod.GET)
    public ModelAndView shareDetailsPage(Long id, Model model) {
        WithdrawBill withdrawBill = withdrawBillService.findWithdrawBillById(id);
        Long commissionBalance = 0l;
        Long onWithdrawal = 0l;
        Long totalWithdrawals = 0l;
        Long totalMoney = 0l;

        model.addAttribute("withdrawBill", withdrawBill);
        if (withdrawBill.getBillType() == 1) {
            model.addAttribute("title", "合伙人");
            model.addAttribute("partner", withdrawBill.getPartner());
            PartnerWallet partnerWallet = partnerService.findPartnerWalletByPartner(withdrawBill.getPartner());
            commissionBalance = partnerWallet.getAvailableBalance();
            totalWithdrawals = partnerWallet.getTotalWithdrawals();
            totalMoney = partnerWallet.getTotalMoney();
            Long onWithdrawal2 = withdrawBillService.findPartnerOnWithdrawalByPartnerId(withdrawBill.getPartner().getId());
            if (onWithdrawal2 != null) {
                onWithdrawal = onWithdrawal2;
            }
        }
        if (withdrawBill.getBillType() == 0) {
            model.addAttribute("title", "合伙人管理员");
            model.addAttribute("partnerManager", withdrawBill.getPartnerManager());
            PartnerManagerWallet partnerManagerWallet = partnerService.findPartnerManagerWalletByPartnerManager(withdrawBill.getPartnerManager());
            commissionBalance = partnerManagerWallet.getAvailableBalance();
            totalWithdrawals = partnerManagerWallet.getTotalWithdrawals();
            totalMoney = partnerManagerWallet.getTotalMoney();
            Long onWithdrawal2 = withdrawBillService.findPartnerManagerOnWithdrawalByPartnerManagerId(withdrawBill.getPartnerManager().getId());
            if (onWithdrawal2 != null) {
                onWithdrawal = onWithdrawal2;
            }
        }
        if (withdrawBill.getBillType() == 2) {
            model.addAttribute("title", "商户");
            model.addAttribute("merchant", withdrawBill.getMerchant());
            MerchantWallet merchantWallet = merchantService.findMerchantWalletByMerchant(withdrawBill.getMerchant());
            commissionBalance = merchantWallet.getAvailableBalance();
            totalWithdrawals = merchantWallet.getTotalWithdrawals();
            totalMoney = merchantWallet.getTotalMoney();
            Long onWithdrawal2 = withdrawBillService.findMerchantOnWithdrawalByMerchantId(withdrawBill.getMerchant().getId());
            if (onWithdrawal2 != null) {
                onWithdrawal = onWithdrawal2;
            }
        }
        model.addAttribute("commissionBalance", commissionBalance);
        model.addAttribute("onWithdrawal", onWithdrawal);
        model.addAttribute("totalWithdrawals", totalWithdrawals);
        model.addAttribute("totalMoney", totalMoney);
        return MvUtil.go("/withdraw/shareDetailsPage");
    }

    @RequestMapping(value = "/withdrawBill/export", method = RequestMethod.POST)
    public ModelAndView exporeExcel(ShareCriteria shareCriteria) {
        if (shareCriteria.getOffset() == null) {
            shareCriteria.setOffset(1);
        }
        Page page = shareService.findShareByPage(shareCriteria, 10000);
        Map map = new HashMap();
        map.put("shareList", page.getContent());
        return new ModelAndView(withdrawExcel, map);
    }


}
