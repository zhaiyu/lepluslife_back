package com.jifenke.lepluslive.global.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 数据处理
 */
public class DataUtils {

  //比较两个日期是否是同一天
  public static boolean isSameDay(Date day1, Date day2) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String ds1 = sdf.format(day1);
    String ds2 = sdf.format(day2);
    if (ds1.equals(ds2)) {
      return true;
    } else {
      return false;
    }
  }

  //格式化时间为yyyy-MM-dd
  public static String dateTOString(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(date);
  }

  /**
   * 获取今日的零点零分
   */
  public static Date getCurrDayBeginDate() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    return calendar.getTime();
  }

}
