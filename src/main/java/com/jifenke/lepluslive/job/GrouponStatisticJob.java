package com.jifenke.lepluslive.job;

import com.jifenke.lepluslive.groupon.service.GrouponStatisticService;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 团购结算统计(每日早上5:00统计) Created by zhangwen on 17/08/23.
 */
@Component
public class GrouponStatisticJob implements Job {

  private static final Logger log = LoggerFactory.getLogger(GrouponStatisticJob.class);

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {

    ApplicationContext
        applicationContext = null;
    try {
      applicationContext =
          (ApplicationContext) context.getScheduler().getContext().get("applicationContextKey");
    } catch (SchedulerException e) {
      log.error("jobExecutionContext.getScheduler().getContext() error!", e);
      throw new RuntimeException(e);
    }
    GrouponStatisticService
        grouponStatisticService =
        (GrouponStatisticService) applicationContext.getBean("grouponStatisticService");
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);

    Date start = calendar.getTime();
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    calendar.add(Calendar.SECOND, -1);

    Date end = calendar.getTime();

    List<Object[]> objects = grouponStatisticService.statistic(start, end);

    for (Object[] obj : objects) {
      try {
        grouponStatisticService.createStatistic(obj, end);
      } catch (Exception e) {
        log.error("门店ID为" + obj[1] + "的团购统计出现问题");
      }
    }
  }
}
