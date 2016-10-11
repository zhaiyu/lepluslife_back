package com.jifenke.lepluslive.datacenter.controller;

import com.jifenke.lepluslive.datacenter.domain.criteria.TransactionAnalysisCriteria;
import com.jifenke.lepluslive.datacenter.service.MemberDataService;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.order.service.OffLineOrderService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by lss on 2016/9/27.
 */
@RestController
@RequestMapping("/manage")
public class transactionAnalysisController {

  @Inject
  private MemberDataService memberDataService;
  @Inject
  private OffLineOrderService offLineOrderService;

  @RequestMapping("/transactionAnalysisPage")
  public ModelAndView userData() {
    return MvUtil.go("/datacenter/transactionAnalysis");
  }

  //画折线图
  @RequestMapping(value = "/getTransactionAnalysisByAjax", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult getTransactionAnalysisByAjax(
      @RequestBody TransactionAnalysisCriteria transactionAnalysisCriteria) {
    Map<String, List> map = new HashMap<String, List>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    List<Date> dateList = new ArrayList<Date>();
    String startDate = transactionAnalysisCriteria.getStartDate();
    String endDate = transactionAnalysisCriteria.getEndDate();
    if ("nullValue".equals(endDate)) {
      try {
        Calendar calendarDate = Calendar.getInstance();
        Date date = new Date();
        dateList.add(date);
        calendarDate.setTime(date);
        for (int i = 1; i < 7; i++) {
          calendarDate.set(Calendar.DATE, calendarDate.get(Calendar.DATE) - 1);
          dateList.add(sdf.parse(sdf.format(calendarDate.getTime())));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {

      Calendar cal = Calendar.getInstance();
      try {
        Date dBegin = sdf.parse(startDate);
        Date dEnd = sdf.parse(endDate);
        dateList = getDatesBetweenTwoDate(dBegin, dEnd);
        Collections.reverse(dateList);
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
    List<Double> wxCommissionlist = new ArrayList<Double>();
    List<Double> offlineWxTruePaylist = new ArrayList<Double>();
    List<Double> onlineWxTruePaylist = new ArrayList<Double>();
    List<Double> wxTruePayList = new ArrayList<Double>();
    List<String> dateStrList = new ArrayList<String>();
    List<Object[]> offlineWxTruePayObjList = memberDataService.findOfflineWxTruePayAndDate();
    Map<String, Long> offlineWxTruePayDayMap = new HashMap<String, Long>();
    for (int i = 0; i < offlineWxTruePayObjList.size(); i++) {
      String day = offlineWxTruePayObjList.get(i)[1].toString();
      Long offlineWxTruePay = Long.parseLong(offlineWxTruePayObjList.get(i)[0].toString());
      offlineWxTruePayDayMap.put(day, offlineWxTruePay);
    }
    List<Object[]> onlineWxTruePayObjList = memberDataService.findOnlineWxTruePayAndDate();
    Map<String, Long> onlineWxTruePayMap = new HashMap<String, Long>();
    for (int i = 0; i < onlineWxTruePayObjList.size(); i++) {
      String day = onlineWxTruePayObjList.get(i)[1].toString();
      Long onlineWxTruePay = Long.parseLong(onlineWxTruePayObjList.get(i)[0].toString());
      onlineWxTruePayMap.put(day, onlineWxTruePay);
    }
    List<Object[]> offlineWxCommissionObjList = memberDataService.findOfflineWxCommissionAndDate();
    Map<String, Double> offlineWxCommissionMap = new HashMap<String, Double>();
    for (int i = 0; i < offlineWxCommissionObjList.size(); i++) {
      String day = offlineWxCommissionObjList.get(i)[1].toString();
      Double
          offlineWxCommission =
          Double.parseDouble(offlineWxCommissionObjList.get(i)[0].toString());
      offlineWxCommissionMap.put(day, offlineWxCommission);
    }
    List<Object[]> onlineWxCommissionObjList = memberDataService.findOnlineWxCommissionAndDate();
    for (Date date : dateList) {
      String str = sdf.format(date);
      String[] stringArray = str.split("-");
      String dateStr = stringArray[0] + stringArray[1] + stringArray[2];
      Long offlineWxTruePay = 0L;
      if (offlineWxTruePayDayMap.get(dateStr) != null) {
        offlineWxTruePay = offlineWxTruePayDayMap.get(dateStr);
      }
      Long onlineWxTruePay = 0L;
      if (onlineWxTruePayMap.get(dateStr) != null) {
        onlineWxTruePay = onlineWxTruePayMap.get(dateStr);
      }
      Double wxCommission = 0D;
      Double offlineWxCommission = 0D;
      if (offlineWxCommissionMap.get(dateStr) != null) {
        offlineWxCommission = offlineWxCommissionMap.get(dateStr) / 100;
      }
      double onlineWxCommission = 0.0;
      for (int i = 0; i < onlineWxCommissionObjList.size(); i++) {
        Double commission = 0D;
        if (onlineWxCommissionObjList.get(i)[1].toString() == dateStr) {
          commission = new Double(onlineWxCommissionObjList.get(i)[0].toString());
        }
        commission = commission / 100.0 * 0.006;
        double result = Math.round(commission * 100) / 100;
        onlineWxCommission += result;
      }
      wxCommission = offlineWxCommission + onlineWxCommission;
      wxCommissionlist.add(wxCommission);
      if (offlineWxTruePay == null) {
        offlineWxTruePay = 0L;
      }
      if (onlineWxTruePay == null) {
        onlineWxTruePay = 0L;
      }
      wxTruePayList.add((double) (offlineWxTruePay + onlineWxTruePay) / 100.0);
      offlineWxTruePaylist.add((double) offlineWxTruePay / 100.0);
      onlineWxTruePaylist.add((double) onlineWxTruePay / 100.0);
      dateStrList.add(dateStr);
    }
    map.put("offlineWxTruePaylist", offlineWxTruePaylist);
    map.put("onlineWxTruePaylist", onlineWxTruePaylist);
    map.put("dateStrList", dateStrList);
    map.put("wxTruePayList", wxTruePayList);
    map.put("wxCommissionlist", wxCommissionlist);
    List<Map> mapList = new ArrayList<Map>();
    return LejiaResult.ok(map);
  }

  //画表格
  @RequestMapping(value = "/getTransactionAnalysisFormByAjax", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult getTransactionAnalysisFormByAjax(
      @RequestBody TransactionAnalysisCriteria transactionAnalysisCriteria) {
    Integer offset = transactionAnalysisCriteria.getOffset();
    Map<String, List> map = new HashMap<String, List>();
    Calendar calendarDate = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    List<Date> dateList = new ArrayList<Date>();
    List<Date> dateList2 = new ArrayList<Date>();
    String startDate = transactionAnalysisCriteria.getStartDate();
    String endDate = transactionAnalysisCriteria.getEndDate();
    if ("nullValue".equals(endDate)) {
      try {
        Date date = new Date();
        dateList.add(date);
        calendarDate.setTime(date);
        for (int i = 1; i < 7; i++) {
          calendarDate.set(Calendar.DATE, calendarDate.get(Calendar.DATE) - 1);
          dateList.add(sdf.parse(sdf.format(calendarDate.getTime())));
        }
        dateList2 = dateList;
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      Calendar cal = Calendar.getInstance();
      try {
        Date dBegin = sdf.parse(startDate);
        Date dEnd = sdf.parse(endDate);
        dateList = getDatesBetweenTwoDate(dBegin, dEnd);
        dateList2 = dateList;
        int qian = (offset - 1) * 10;
        //分页
        if ((qian + 10) >= dateList2.size()) {
          dateList = dateList.subList(qian, dateList2.size());
        } else {
          dateList = dateList.subList(qian, qian + 10);
        }
        Collections.reverse(dateList);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    List<Double> wxCommissionlist = new ArrayList<Double>();
    List<Double> offlineWxTruePaylist = new ArrayList<Double>();
    List<Double> onlineWxTruePaylist = new ArrayList<Double>();
    List<Double> wxTruePayList = new ArrayList<Double>();
    List<String> dateStrList = new ArrayList<String>();
    List<Object[]> offlineWxTruePayObjList = memberDataService.findOfflineWxTruePayAndDate();
    Map<String, Long> offlineWxTruePayDayMap = new HashMap<String, Long>();
    for (int i = 0; i < offlineWxTruePayObjList.size(); i++) {
      String day = offlineWxTruePayObjList.get(i)[1].toString();
      Long offlineWxTruePay = Long.parseLong(offlineWxTruePayObjList.get(i)[0].toString());
      offlineWxTruePayDayMap.put(day, offlineWxTruePay);
    }

    List<Object[]> onlineWxTruePayObjList = memberDataService.findOnlineWxTruePayAndDate();
    Map<String, Long> onlineWxTruePayMap = new HashMap<String, Long>();
    for (int i = 0; i < onlineWxTruePayObjList.size(); i++) {
      String day = onlineWxTruePayObjList.get(i)[1].toString();
      Long onlineWxTruePay = Long.parseLong(onlineWxTruePayObjList.get(i)[0].toString());
      onlineWxTruePayMap.put(day, onlineWxTruePay);
    }
    List<Object[]> offlineWxCommissionObjList = memberDataService.findOfflineWxCommissionAndDate();
    Map<String, Double> offlineWxCommissionMap = new HashMap<String, Double>();
    for (int i = 0; i < offlineWxCommissionObjList.size(); i++) {
      String day = offlineWxCommissionObjList.get(i)[1].toString();
      Double
          offlineWxCommission =
          Double.parseDouble(offlineWxCommissionObjList.get(i)[0].toString());
      offlineWxCommissionMap.put(day, offlineWxCommission);
    }
    List<Object[]> onlineWxCommissionObjList = memberDataService.findOnlineWxCommissionAndDate();
    for (Date date : dateList) {
      String str = sdf.format(date);
      String[] stringArray = str.split("-");
      String dateStr = stringArray[0] + stringArray[1] + stringArray[2];
      Long offlineWxTruePay = 0L;
      if (offlineWxTruePayDayMap.get(dateStr) != null) {
        offlineWxTruePay = offlineWxTruePayDayMap.get(dateStr);
      }
      Long onlineWxTruePay = 0L;
      if (onlineWxTruePayMap.get(dateStr) != null) {
        onlineWxTruePay = onlineWxTruePayMap.get(dateStr);
      }
      Double wxCommission = 0D;
      Double offlineWxCommission = 0D;
      if (offlineWxCommissionMap.get(dateStr) != null) {
        offlineWxCommission = offlineWxCommissionMap.get(dateStr) / 100;
      }
      double onlineWxCommission = 0.0;
      for (int i = 0; i < onlineWxCommissionObjList.size(); i++) {
        Double commission = 0D;
        if (onlineWxCommissionObjList.get(i)[1].toString() == dateStr) {
          commission = new Double(onlineWxCommissionObjList.get(i)[0].toString());
        }
        commission = commission / 100.0 * 0.006;
        double result = Math.round(commission * 100) / 100;
        onlineWxCommission += result;
      }
      wxCommission = offlineWxCommission + onlineWxCommission;
      wxCommissionlist.add(wxCommission);

      if (offlineWxTruePay == null) {
        offlineWxTruePay = 0L;
      }
      if (onlineWxTruePay == null) {
        onlineWxTruePay = 0L;
      }
      wxTruePayList.add((double) (offlineWxTruePay + onlineWxTruePay) / 100.0);
      offlineWxTruePaylist.add((double) offlineWxTruePay / 100.0);
      onlineWxTruePaylist.add((double) onlineWxTruePay / 100.0);
      dateStrList.add(dateStr);
    }
    map.put("offlineWxTruePayFormList", offlineWxTruePaylist);
    map.put("onlineWxTruePayFormList", onlineWxTruePaylist);
    map.put("dateStrFormList", dateStrList);
    map.put("wxTruePayFormList", wxTruePayList);
    map.put("wxCommissionFormList", wxCommissionlist);
    List pageList = new ArrayList<>();
    int dateStrListSize = dateList2.size();
    Double db = Math.ceil(dateStrListSize / 10);
    Double d_s = new Double(db);
    int totalPages = d_s.intValue();
    if (dateList2.size() % 10 == 0) {
      pageList.add(totalPages);//总页数
    } else {
      pageList.add(totalPages + 1);//总页数
    }
    pageList.add(dateList2.size());//总条数
    map.put("pageList", pageList);
    return LejiaResult.ok(map);
  }


  public List getDatesBetweenTwoDate(Date beginDate, Date endDate) {
    List lDate = new ArrayList();
    lDate.add(beginDate);//把开始时间加入集合
    Calendar cal = Calendar.getInstance();
    //使用给定的 Date 设置此 Calendar 的时间
    cal.setTime(beginDate);
    boolean bContinue = true;
    while (bContinue) {
      //根据日历的规则，为给定的日历字段添加或减去指定的时间量
      cal.add(Calendar.DAY_OF_MONTH, 1);
      // 测试此日期是否在指定日期之后
      if (endDate.after(cal.getTime())) {
        lDate.add(cal.getTime());
      } else {
        break;
      }
    }
    lDate.add(endDate);//把结束时间加入集合
    return lDate;
  }


}

