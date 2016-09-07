package com.jifenke.lepluslive.sales.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.sales.domain.entities.SalesStaff;
import com.jifenke.lepluslive.sales.service.SalesService;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

import java.util.HashSet;
import java.util.List;

/**
 * Created by lss on 2016/8/10.
 */
@RestController
@RequestMapping("/manage")
public class SalesController {

  @Inject
  private SalesService salesService;
  @Inject
  private MerchantService merchantService;

  @RequestMapping(value = "/sales")
  public ModelAndView findSalesStaffPage(Model model) {

    List<SalesStaff> salesStaffList = salesService.findAllSaleStaff();
    model.addAttribute("salesStaffList", salesStaffList);

    return MvUtil.go("/sales/salesStaffList");
  }

  @RequestMapping(value = "/salesStaffManageMerchant")
  public ModelAndView salesStaffManageMerchant(Model model, String id) {
    SalesStaff salesStaff = salesService.findSaleStaffById(Long.parseLong(id));
    model.addAttribute("salesStaff", salesStaff);
    List<SalesStaff> salesStaffList = salesService.findAllSaleStaff();
    model.addAttribute("salesStaffList", salesStaffList);
    int salesMerchantCount = merchantService.findSalesMerchantCount(id);
    model.addAttribute("salesMerchantCount", salesMerchantCount);
    return MvUtil.go("/sales/salesStaffManageMerchant");
  }


  @RequestMapping(value = "/merchantMove")
  public ModelAndView merchantMobile(Model model, String id1, String id2) {
    SalesStaff salesStaff1 = salesService.findSaleStaffById(Long.parseLong(id1));
    List<Merchant> merchantList1 = salesStaff1.getMerchant();
    for (int i = 0; i < merchantList1.size(); i++) {
      if (merchantList1.get(i).getSalesStaff().getId() == Long.parseLong(id1)) {
        Merchant merchant = merchantList1.get(i);
        SalesStaff salesStaff = salesService.findSaleStaffById(Long.parseLong(id2));
        merchant.setSalesStaff(salesStaff);
        merchantService.saveMerchant(merchant);

      }
    }
    SalesStaff salesStaff = salesService.findSaleStaffById(Long.parseLong(id1));
    model.addAttribute("salesStaff", salesStaff);
    List<SalesStaff> salesStaffList = salesService.findAllSaleStaff();
    model.addAttribute("salesStaffList", salesStaffList);
    int salesMerchantCount = merchantService.findSalesMerchantCount(id1);
    model.addAttribute("salesMerchantCount", salesMerchantCount);
    return MvUtil.go("/sales/salesStaffManageMerchant");
  }


  @RequestMapping(value = "/addSalespage")
  public ModelAndView addSalesStaffPage(Model model) {
    return MvUtil.go("/sales/addSalesStaff");
  }

  @RequestMapping(value = "/addSales")
  public ModelAndView addSales(Model model, String name) {
    SalesStaff salesStaff = new SalesStaff();
    salesStaff.setName(name);
    salesService.saveStaff(salesStaff);

    return new ModelAndView("redirect:/manage/sales");
  }


  @RequestMapping(value = "/oneMerchantMove")
  public ModelAndView oneMerchantMove(Model model, String merchantId, String salesStaffId1,
                                      String salesStaffId2) {
    Merchant merchant = merchantService.findMerchantById(Long.parseLong(merchantId));
    SalesStaff SalesStaff2 = salesService.findSaleStaffById(Long.parseLong(salesStaffId2));
    merchant.setSalesStaff(SalesStaff2);
    merchantService.saveMerchant(merchant);

    SalesStaff salesStaff = salesService.findSaleStaffById(Long.parseLong(salesStaffId1));
    model.addAttribute("salesStaff", salesStaff);
    List<SalesStaff> salesStaffList = salesService.findAllSaleStaff();
    model.addAttribute("salesStaffList", salesStaffList);
    int salesMerchantCount = merchantService.findSalesMerchantCount(salesStaffId1);
    model.addAttribute("salesMerchantCount", salesMerchantCount);
    return MvUtil.go("/sales/salesStaffManageMerchant");
  }


  @RequestMapping(value = "/newSalesStaffName")
  public ModelAndView newSalesStaffName(Model model, String salesStaffId,
                                        String newSalesStaffName) {
    SalesStaff salesStaff = salesService.findSaleStaffById(Long.parseLong(salesStaffId));
    salesStaff.setName(newSalesStaffName);
    salesService.saveStaff(salesStaff);
    return new ModelAndView("redirect:/manage/sales");
  }

}
