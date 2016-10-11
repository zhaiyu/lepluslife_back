package com.jifenke.lepluslive.datacenter.controller;

import com.jifenke.lepluslive.datacenter.domain.criteria.MerchantCriteriaEx;
import com.jifenke.lepluslive.datacenter.service.MerchantDataService;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.entities.City;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantType;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.order.service.OffLineOrderService;
import com.jifenke.lepluslive.sales.domain.entities.SalesStaff;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xf on 2016/9/20.
 */
@RestController
@RequestMapping("/manage")
public class MerchantDataController {

  @Inject
  private MerchantDataService merchantDataService;
  @Inject
  private MerchantService merchantService;

  @RequestMapping("/merchant_data")
  public ModelAndView merchantDataPage(Model model) {
    //  下拉列表数据
    List<City> cityList = merchantDataService.findAllCitys();
    List<SalesStaff> staffList = merchantDataService.findAllStaffList();
    List<MerchantType> merchantTypeList = merchantDataService.findAllMerchantTypes();
    model.addAttribute("cityList", cityList);
    model.addAttribute("staffList", staffList);
    model.addAttribute("merchantTypeList", merchantTypeList);
    return MvUtil.go("/datacenter/merchantCenter");
  }

  @RequestMapping(value = "/merchant_data/search")
  public LejiaResult merchantDataSearch(@RequestBody MerchantCriteriaEx merchantCriteria) {
    //  初始化分页数
    if (merchantCriteria.getOffset() == null) {
      merchantCriteria.setOffset(1);
    }
    //  设置默认有效金额
    if (merchantCriteria.getValidAmount() == null) {
      merchantCriteria.setValidAmount(10L);           // 设置默认有效金额为 10 元
    }
    // 获取查询条件中的时间
    String startDate = merchantCriteria.getStartDate();
    String endDate = merchantCriteria.getEndDate();
    if (startDate == null && endDate == null) {
      Date date = new Date();
      SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
      String today = format.format(date);
      startDate = today + " 00:00:00";
      endDate = today + " 23:59:59";
      //  设置数据统计的时间范围  (设置默认时间为当天)
      merchantCriteria.setStartDate(startDate);
      merchantCriteria.setEndDate(endDate);
    }
    //  列表所需数据
    Map<String, Object> model = new HashMap<>();
    Map page = new HashMap<>();                           // 分页查询用户信息
    Map<String, List> merchantMap =
        merchantDataService.findMerchantAndCountByCriteria(merchantCriteria);
    List<Merchant> merchants = merchantMap.get("merchants");
    List<BigInteger> detail = merchantMap.get("totalElements");
    if (detail != null && detail.size() > 0) {
      int totalElements = detail.get(0).intValue();
      double dbCount = totalElements;
      long totalPages = Math.round(dbCount / 10.0);
      page.put("totalPages", totalPages);
      page.put("totalElements", totalElements);
      page.put("content", merchants);

      List<Long> orderNum = merchantMap.get("orderNum");                                 // 有效订单数
      List<Double> orderTotal = merchantMap.get("orderTotal");                           // 有效订单流水

      List<SalesStaff> staffs = merchantDataService.findStaffByMerchant(merchants);
      List<Integer> binds = merchantService.findBindLeJiaUsers(merchants);       // 查找锁定用户
      List<Integer>
          timeBinds =
          merchantDataService
              .findBindLeJiaUsersByDate(merchants, merchantCriteria);        // 时间段锁定用户

      Map<String, List> map = merchantDataService.findLeadOrder(merchants, merchantCriteria);
      List<Long> leadOrderNum = map.get("leadOrderNum");        // 导流订单数
      List<Double> leadTotal = map.get("leadTotal");           // 导流流水
      List<Double> leadCommission = map.get("leadCommission"); // 导流佣金

      model.put("binds", binds);
      model.put("staffs", staffs);
      model.put("timeBinds", timeBinds);
      model.put("orderNum", orderNum);
      model.put("orderTotal", orderTotal);
      model.put("leadOrderNum", leadOrderNum);
      model.put("leadCommission", leadCommission);
      model.put("leadTotal", leadTotal);
    } else {
      page.put("totalPages", 0);
      page.put("totalElements", 0);
      page.put("content", null);
    }
    //  存储 model 用于页面展示
    model.put("page", page);
    return LejiaResult.ok(model);
  }

  //  @RequestMapping(value = "/merchant_data/merchantDataExport", method = RequestMethod.POST)
//  public String merchantDataExport(MerchantCriteriaEx merchantCriteria,
//                                   HttpServletResponse response) {
//    //  初始化分页数
//    if (merchantCriteria.getOffset() == null) {
//      merchantCriteria.setOffset(1);
//    }
//    //  设置默认有效金额
//    if (merchantCriteria.getValidAmount() == null) {
//      merchantCriteria.setValidAmount(10L);           // 设置默认有效金额为 10 元
//    }
//    // 获取查询条件中的时间
//    String startDate = merchantCriteria.getStartDate();
//    String endDate = merchantCriteria.getEndDate();
//    //  设置默认时间为当天
//    if (startDate == null && endDate == null) {
//      Date date = new Date();
//      SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//      String today = format.format(date);
//      startDate = today + " 00:00:00";
//      endDate = today + " 23:59:59";
//      //  设置数据统计的时间范围
//      merchantCriteria.setStartDate(startDate);
//      merchantCriteria.setEndDate(endDate);
//    }
//    //  表格所需数据
//    Map<String, List> merchantMap =
//        merchantDataService.findMerchantAndCountByCriteria(merchantCriteria);
//    List<Merchant> merchants = merchantMap.get("merchants");
//    ServletOutputStream outputStream = null;
//    try {
//      Date date = new Date();
//      SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHssmm");
//      String time = format.format(date);
//      String filename = new String(("MerchantData-" + time).getBytes(), "ISO8859_1");
//      response.setContentType("application/binary;charset=ISO8859_1");
//      response.setHeader("Content-disposition", "attachment; filename=" + filename + ".xls");
//      //  获取需要导出的数据
//      List<SalesStaff> staffs = merchantDataService.findStaffByMerchant(merchants);
//      List<Integer> binds = merchantService.findBindLeJiaUsers(merchants);       // 查找锁定用户
//      List<Integer> timeBinds = merchantDataService
//          .findBindLeJiaUsersByDate(merchants, merchantCriteria);        // 时间段锁定用户
//      List<Long> orderNum = merchantMap.get("orderNum");                                 // 有效订单数
//      List<Double> orderTotal = merchantMap.get("orderTotal");                           // 有效订单流水
//      Map<String, List> map = merchantDataService.findLeadOrder(merchants, merchantCriteria);
//      List<Long> leadOrderNum = map.get("leadOrderNum");     // 导流订单数
//      List<Double> leadTotal = map.get("leadTotal");           // 导流流水
//      List<Double> leadCommission = map.get("leadCommission"); // 导流佣金
//      //  调用导出方法
//      String[] titles =
//          {"销售名称", "商户名称", "商户类型", "当前锁定情况", "时段内锁定", "流水额", "有效订单量", "导流订单数", "导流订单流水", "导流佣金"};
//      outputStream = response.getOutputStream();
//      merchantDataService.exportExcel(staffs, merchants,null, binds,null,timeBinds,null,orderNum, orderTotal,
//                                      leadOrderNum,
//                                      leadTotal, leadCommission, titles, outputStream);
//    } catch (Exception e) {
//      e.printStackTrace();
//    } finally {
//      try {
//        outputStream.close();
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
//    }
//    return null;
//  }
  @RequestMapping(value = "/merchant_data/merchantDataExport", method = RequestMethod.POST)
  public String merchantDataExport(MerchantCriteriaEx merchantCriteria,
                                   HttpServletResponse response) {
    //  初始化分页数
    if (merchantCriteria.getOffset() == null) {
      merchantCriteria.setOffset(1);
    }
    //  设置默认有效金额
    if (merchantCriteria.getValidAmount() == null) {
      merchantCriteria.setValidAmount(10L);           // 设置默认有效金额为 10 元
    }
    // 获取查询条件中的时间
    String startDate = merchantCriteria.getStartDate();
    String endDate = merchantCriteria.getEndDate();
    //  设置默认时间为当天
    if (startDate == null && endDate == null) {
      Date date = new Date();
      SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
      String today = format.format(date);
      startDate = today + " 00:00:00";
      endDate = today + " 23:59:59";
      //  设置数据统计的时间范围
      merchantCriteria.setStartDate(startDate);
      merchantCriteria.setEndDate(endDate);
    }
    //  表格所需数据
    Map<String, List> merchantMap =
        merchantDataService.findMerchantAndCountByCriteriaForExcel(merchantCriteria);
    List<Merchant> merchants = merchantMap.get("merchants");
    ServletOutputStream outputStream = null;
    try {
      Date date = new Date();
      SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHssmm");
      String time = format.format(date);
      String filename = new String(("MerchantData-" + time).getBytes(), "ISO8859_1");
      response.setContentType("application/binary;charset=ISO8859_1");
      response.setHeader("Content-disposition", "attachment; filename=" + filename + ".xls");
      //  获取需要导出的数据
      List<SalesStaff> staffs = merchantDataService.findStaffByMerchant(merchants);
      List<Integer> binds = merchantService.findBindLeJiaUsers(merchants);       // 查找锁定用户
      List<Integer> timeBinds = merchantDataService
          .findBindLeJiaUsersByDate(merchants, merchantCriteria);        // 时间段锁定用户
      List<Long> orderNum = merchantMap.get("orderNum");                                 // 有效订单数
      List<Double> orderTotal = merchantMap.get("orderTotal");                           // 有效订单流水
      Map<String, List> map = merchantDataService.findLeadOrder(merchants, merchantCriteria);
      List<Long> leadOrderNum = map.get("leadOrderNum");     // 导流订单数
      List<Double> leadTotal = map.get("leadTotal");           // 导流流水
      List<Double> leadCommission = map.get("leadCommission"); // 导流佣金
      //  调用导出方法
      String[] titles =
          {"销售名称", "商户名称", "商户类型", "当前锁定情况", "时段内锁定", "流水额", "有效订单量", "导流订单数", "导流订单流水", "导流佣金",
           "商户编号", "商户创建日期"};
      outputStream = response.getOutputStream();
      merchantDataService
          .exportExcel(staffs, merchants, null, binds, null, timeBinds, null, orderNum, orderTotal,
                       leadOrderNum,
                       leadTotal, leadCommission, titles, outputStream);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        outputStream.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @RequestMapping(value = "/merchant_data/merchantDataExportAll", method = RequestMethod.POST)
  public String merchantDataExportAll(MerchantCriteriaEx merchantCriteria,
                                      HttpServletResponse response) {
    //  导出所有-清空分页数
    if (merchantCriteria.getOffset() != null) {
      merchantCriteria.setOffset(null);
    }
    //  设置默认有效金额
    if (merchantCriteria.getValidAmount() == null) {
      merchantCriteria.setValidAmount(10L);           // 设置默认有效金额为 10 元
    }
    // 获取查询条件中的时间
    String startDate = merchantCriteria.getStartDate();
    String endDate = merchantCriteria.getEndDate();
    //  设置默认时间为当天
    if (startDate == null && endDate == null) {
      Date date = new Date();
      SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
      String today = format.format(date);
      startDate = today + " 00:00:00";
      endDate = today + " 23:59:59";
      //  设置数据统计的时间范围
      merchantCriteria.setStartDate(startDate);
      merchantCriteria.setEndDate(endDate);
    }
    //  表格所需数据
    Map<String, List> merchantMap =
        merchantDataService.findMerchantAndCountByCriteria(merchantCriteria);
    List<Merchant> merchants = merchantMap.get("merchants");
    //  查出其他用户 , 并入用户列表
    List<Merchant> otherMerchants = merchantDataService.findMerchantNotInOffLineOrder(merchants);
    ServletOutputStream outputStream = null;
    try {
      Date date = new Date();
      SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHssmm");
      String time = format.format(date);
      String filename = new String(("MerchantData-" + time).getBytes(), "ISO8859_1");
      response.setContentType("application/binary;charset=ISO8859_1");
      response.setHeader("Content-disposition", "attachment; filename=" + filename + ".xls");
      //  获取需要导出的数据
      List<SalesStaff> staffs = merchantDataService.findStaffByMerchant(merchants);
      List<Integer> binds = merchantService.findBindLeJiaUsers(merchants);       // 查找锁定用户
      List<Integer> otherBinds = merchantService.findBindLeJiaUsers(otherMerchants);
      List<Integer> timeBinds = merchantDataService
          .findBindLeJiaUsersByDate(otherMerchants, merchantCriteria);        // 时间段锁定用户
      List<Integer> otherTimeBinds = merchantDataService
          .findBindLeJiaUsersByDate(otherMerchants, merchantCriteria);
      List<Long> orderNum = merchantMap.get("orderNum");                                 // 有效订单数
      List<Double> orderTotal = merchantMap.get("orderTotal");                           // 有效订单流水
      Map<String, List> map = merchantDataService.findLeadOrder(merchants, merchantCriteria);
      List<Long> leadOrderNum = map.get("leadOrderNum");     // 导流订单数
      List<Double> leadTotal = map.get("leadTotal");           // 导流流水
      List<Double> leadCommission = map.get("leadCommission"); // 导流佣金
      //  调用导出方法
      String[] titles =
          {"销售名称", "商户名称", "商户类型", "当前锁定情况", "时段内锁定", "流水额", "有效订单量", "导流订单数", "导流订单流水", "导流佣金"};
      outputStream = response.getOutputStream();
      merchantDataService
          .exportExcel(staffs, merchants, otherMerchants, binds, otherBinds, timeBinds,
                       otherTimeBinds, orderNum, orderTotal,
                       leadOrderNum,
                       leadTotal, leadCommission, titles, outputStream);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        outputStream.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
