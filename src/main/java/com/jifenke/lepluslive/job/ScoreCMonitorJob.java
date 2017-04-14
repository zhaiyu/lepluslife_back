package com.jifenke.lepluslive.job;

import com.jifenke.lepluslive.order.service.OffLineOrderService;
import com.jifenke.lepluslive.score.domain.entities.ScoreC;
import com.jifenke.lepluslive.score.service.ScoreCService;
import com.jifenke.lepluslive.scoreAAccount.service.ScoreAAccountService;
import com.jifenke.lepluslive.shortMessage.service.ShortMessageSceneService;
import com.jifenke.lepluslive.shortMessage.service.ShortMessageService;
import com.jifenke.lepluslive.user.service.UserService;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wcg on 2017/3/30.
 */
public class ScoreCMonitorJob implements Job {

  private static final Logger log = LoggerFactory.getLogger(scoreAAccountJob.class);

  @Override
  public void execute(JobExecutionContext context) {
    Calendar calendar = Calendar.getInstance();
    Date end = calendar.getTime();
    String string = "2017-04-02 00:00:00";
    String string2 = "2017-04-05 00:00:00";
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = null;
    Date date2 = null;
    try {
      date = format.parse(string);
      date2 = format.parse(string2);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    if (end.after(date)) {
      calendar.set(Calendar.MINUTE, -60);
      Date start = calendar.getTime();
      ApplicationContext applicationContext = null;
      try {

        applicationContext =
            (ApplicationContext) context.getScheduler().getContext().get("applicationContextKey");
        ScoreCService
            scoreCService =
            (ScoreCService) applicationContext.getBean("scoreCService");
        OffLineOrderService offLineOrderService =
            (OffLineOrderService) applicationContext.getBean("offLineOrderService");
        ShortMessageService shortMessageService =
            (ShortMessageService) applicationContext.getBean("shortMessageService");
        UserService userService = (UserService) applicationContext.getBean("userService");
        ShortMessageSceneService
            shortMessageSceneService =
            (ShortMessageSceneService) applicationContext.getBean("shortMessageSceneService");
        Long warningOrder = offLineOrderService.monitorOffLineOrder(start, end);
        int scoreCs = scoreCService.monitorScoreC().size();
        Long scoreCValue = offLineOrderService.monitorScorec();
        System.out.println(warningOrder);
        System.out.println(scoreCs);
        System.out.println(scoreCValue);
        StringBuilder message = new StringBuilder();
        message.append("4月1日到现在发送了");
        message.append(scoreCValue / 100.0);
        message.append("金币");
        if (warningOrder != 0) {
          message.append(",出现");
          message.append(warningOrder);
          message.append("比可疑订单");
        }
        if (scoreCs != 0) {
          message.append(",有");
          message.append(scoreCs);
          message.append("个用户金币大于100");
        }
        System.out.println(message.toString());
        shortMessageService
            .sendOneMessage(userService.findUserById(65791L),
                            shortMessageSceneService.findSceneById(6L),
                            message.toString());
      } catch (Exception e) {
        log.error("error",e);
      }
    }
  }
}
