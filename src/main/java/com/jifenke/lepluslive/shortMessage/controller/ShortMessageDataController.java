package com.jifenke.lepluslive.shortMessage.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.shortMessage.domain.criteria.ShortMessageDataCriteria;
import com.jifenke.lepluslive.shortMessage.domain.entities.ShortMessageScene;
import com.jifenke.lepluslive.shortMessage.service.ShortMessageDataService;
import com.jifenke.lepluslive.shortMessage.service.ShortMessageSceneService;

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


@RestController
@RequestMapping("/manage")
public class ShortMessageDataController {

  @Inject
  private ShortMessageDataService shortMessageDataService;

  @Inject
  private ShortMessageSceneService shortMessageSceneService;

  @RequestMapping(value = "/shortMessage/shortMessageBillboards", method = RequestMethod.GET)
  public ModelAndView goTest() {
    return MvUtil.go("/shortMessage/shortMessageBillboards");
  }

  //画折线图
  @RequestMapping(value = "/getShortMessagedataByAjax", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult getShortMessagedata(
      @RequestBody ShortMessageDataCriteria shortMessageDataCriteria) {

    Map<String, List> map = new HashMap<String, List>();
    //创建时期格式
    //SimpleDateFormat 是一个以与语言环境有关的方式来格式化和解析日期的具体类。它允许进行格式化（日期 -> 文本）、解析（文本 -> 日期）和规范化。
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //创建日期集合
    List<Date> dateList = new ArrayList<Date>();
    //从条件中获取  起始时间 ,结束时间
    String startDate = shortMessageDataCriteria.getStartDate();
    String endDate = shortMessageDataCriteria.getEndDate();
    // 判断结束时间是否为空
    if ("nullValue".equals(endDate)) {
      //如果为空
      try {
        //获取今天的星期值
        Calendar calendarDate = Calendar.getInstance();
        Date date = new Date();
        dateList.add(date);
        calendarDate.setTime(date);
        for (int i = 1; i < 7; i++) {
          //java里面Calendar定义的一个常量，相当于用来取出日期的key
          calendarDate.set(Calendar.DATE, calendarDate.get(Calendar.DATE) - 1);
          //parse()返回的是一个Date类型数据，format返回的是一个StringBuffer类型的数据
          //SimpleDateFormat中的parse方法可以把String型的字符串转换成特定格式的date类型
          dateList.add(sdf.parse(sdf.format(calendarDate.getTime())));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      startDate = startDate.replaceAll("/", "-");
      endDate = endDate.replace("/", "-");
      Calendar cal = Calendar.getInstance();
      try {
        Date dBegin = sdf.parse(startDate);
        Date dEnd = sdf.parse(endDate);
        dateList = getDatesBetweenTwoDate(dBegin, dEnd);
        //将list集合反转输出
        Collections.reverse(dateList);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    List<Long> sendCountAndDateList = new ArrayList<Long>();
    List<Long> sendSuccessedCountList = new ArrayList<Long>();
    List<Long> dayReceiveCountList = new ArrayList<Long>();
    List<Long> findUserTDList = new ArrayList<Long>();
    List<String> dateStrList = new ArrayList<String>();

    //发送条数
    List<Object[]> sendCountAndDateobjList = shortMessageDataService.findSendCountAndDate();

    Map<String, Long> sendCountAndDateMap = new HashMap<String, Long>();
    for (int i = 0; i < sendCountAndDateobjList.size(); i++) {
      String day = sendCountAndDateobjList.get(i)[1].toString();
      Long sendcount = Long.parseLong(sendCountAndDateobjList.get(i)[0].toString());
      sendCountAndDateMap.put(day, sendcount);
    }

    //触达客户
    List<Object[]> SendSuccessedCountobjList = shortMessageDataService.findSendSuccessedCount();
    Map<String, Long> SendSuccessedCountMap = new HashMap<String, Long>();
    for (int i = 0; i < SendSuccessedCountobjList.size(); i++) {
      String day = SendSuccessedCountobjList.get(i)[1].toString();
      Long sendsuccessedcount = Long.parseLong(SendSuccessedCountobjList.get(i)[0].toString());
      SendSuccessedCountMap.put(day, sendsuccessedcount);
    }

    //日均接收
    List<Object[]> dayReceiveCountobjList = shortMessageDataService.findDayReceiveCount();
    Map<String, Long> dayReceiveCountMap = new HashMap<String, Long>();
    for (int i = 0; i < dayReceiveCountobjList.size(); i++) {
      String day = dayReceiveCountobjList.get(i)[1].toString();
      Long dayreceive = Long.parseLong(dayReceiveCountobjList.get(i)[0].toString());
      dayReceiveCountMap.put(day, dayreceive);
    }

    //退订数
    List<Object[]> findUserTDobjList = shortMessageDataService.findUserTD();
    Map<String, Long> UserTDMap = new HashMap<String, Long>();
    for (int i = 0; i < findUserTDList.size(); i++) {
      String day = findUserTDobjList.get(i)[1].toString();
      Long usertd = Long.parseLong(findUserTDobjList.get(i)[0].toString());
      UserTDMap.put(day, usertd);
    }
//对map中的day进行判断
    //日期
    for (Date date : dateList) {
      String str = sdf.format(date);
      String[] stringArray = str.split("-");
      String dateStr = stringArray[0] + stringArray[1] + stringArray[2];
      //发送数量
      Long sendcount = 0L;
      if (sendCountAndDateMap.get(dateStr) != null) {
        sendcount = sendCountAndDateMap.get(dateStr);
      }
      //触达数量
      Long sendsuccessedcount = 0L;
      if (SendSuccessedCountMap.get(dateStr) != null) {
        sendsuccessedcount = SendSuccessedCountMap.get(dateStr);
      }
      //日均接收
      Long dayreceive = 0L;
      if (dayReceiveCountMap.get(dateStr) != null) {
        dayreceive = dayReceiveCountMap.get(dateStr);
      }
      //退订数量
      Long usertd = 0L;
      if (UserTDMap.get(dateStr) != null) {
        usertd = UserTDMap.get(dateStr);
      }
      sendCountAndDateList.add(sendcount);
      sendSuccessedCountList.add(sendsuccessedcount);
      dayReceiveCountList.add(dayreceive);
      findUserTDList.add(usertd);
      dateStrList.add(dateStr);
    }
    map.put("sendCountAndDateList", sendCountAndDateList);
    map.put("DayReceiveCountList", dayReceiveCountList);
    map.put("dateStrList", dateStrList);
    map.put("findUserTDList", findUserTDList);
    map.put("SendSuccessedCountList", sendSuccessedCountList);
    return LejiaResult.ok(map);
  }

  //画表格
  @RequestMapping(value = "/getShortMessagedataFormByAjax", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult getShortMessagedataFormByAjax(
      @RequestBody ShortMessageDataCriteria ShortMessageDataCriteria) {

    //创建map集合

    Map<String, Object> map = new HashMap<String, Object>();

    Calendar calendarDate = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    //创建日期的list集合
    List<Date> dateList = new ArrayList<Date>();

    //接受到的参数 起始日期 和 结束日期
    String startDate = ShortMessageDataCriteria.getStartDate();
    String endDate = ShortMessageDataCriteria.getEndDate();
    if ("nullValue".equals(endDate)) {
      try {
        calendarDate = Calendar.getInstance();
        Date dNow = new Date();   //当前时间
        for (int i = 0; i < 7; i++) {
          Date dBefore = new Date();
          Calendar calendar = Calendar.getInstance();  //得到日历
          calendar.setTime(dNow);//把当前时间赋给日历
          calendar.add(Calendar.DAY_OF_MONTH, -i);  //设置为前一天
          dBefore = calendar.getTime();   //得到前一天的时间

          String defaultStartDate = sdf.format(dBefore);    //格式化前一天
          dateList.add(sdf.parse(defaultStartDate));
        }
        startDate = sdf.format(dateList.get(6)) + " " + "00:00:00";
        endDate = sdf.format(dateList.get(0)) + " 23:59:59";

      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      startDate = startDate.replaceAll("/", "-");
      endDate = endDate.replaceAll("/", "-");
      startDate = startDate.split(" ")[0] + " " + "00:00:00";
      endDate = endDate.split(" ")[1] + " 23:59:59";

    }
    List<ShortMessageScene> shortMessageSceneList = shortMessageSceneService.findAll();

    List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();

    for (ShortMessageScene shortMessageScene : shortMessageSceneList) {
      Map<String, String> tableMap = new HashMap<String, String>();
      String sceneName = shortMessageScene.getName();
      tableMap.put("sceneName", sceneName);
      int
          sint =
          shortMessageDataService
              .sceneSucceedCount(shortMessageScene.getSceneSid(), startDate, endDate);
      int
          dint =
          shortMessageDataService
              .sceneDefeateCount(shortMessageScene.getSceneSid(), startDate, endDate);
      List<Object[]>
          tuchUserCountList =
          shortMessageDataService
              .tuchUserCount(shortMessageScene.getSceneSid(), startDate, endDate);
      tableMap.put("sceneName", sceneName);
      tableMap.put("sint", String.valueOf(sint));
      tableMap.put("dint", String.valueOf(dint));
      int tint = 0;
      for (Object[] obj : tuchUserCountList) {
        if (obj[0] != null) {
          tint = tint + Integer.parseInt(obj[0].toString());
        }
      }
      tableMap.put("tint", String.valueOf(tint));
      dataList.add(tableMap);
    }

    return LejiaResult.ok(dataList);
  }


  public List getDatesBetweenTwoDate(Date beginDate, Date endDate) {
    List sDate = new ArrayList();
    sDate.add(beginDate);//把开始时间加入集合
    //获取当前时间
    Calendar cal = Calendar.getInstance();
    //使用给定的 Date 设置此 Calendar 的时间
    cal.setTime(beginDate);

    boolean bContinue = true;
    while (bContinue) {
      //根据日历的规则，为给定的日历字段添加或减去指定的时间量
      //Calendar.DAY_OF_MONTH
      cal.add(Calendar.DAY_OF_MONTH, 1);
      // 测试此日期是否在指定日期之后
      if (endDate.after(cal.getTime())) {
        sDate.add(cal.getTime());
      } else {
        break;
      }
    }
    sDate.add(endDate);//把结束时间加入集合
    return sDate;
  }

  @RequestMapping(value = "/shortMessage/shortMessageOnOffPage", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult userList() {

    List<ShortMessageScene> shortMessageSceneServiceList = shortMessageSceneService.findAll();
    return LejiaResult.ok(shortMessageSceneServiceList);

  }
}

