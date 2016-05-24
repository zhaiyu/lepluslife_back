package com.jifenke.lepluslive.job;

import com.jifenke.lepluslive.order.service.FinanicalStatisticService;
import com.jifenke.lepluslive.order.service.OffLineOrderService;

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

import javax.inject.Inject;

/**
 * Created by wcg on 16/5/5.
 */
@Component
public class OffLineOrderJob implements Job {

  private static final Logger log = LoggerFactory.getLogger(OffLineOrderJob.class);


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
    OffLineOrderService offLineOrderService =
        (OffLineOrderService) applicationContext.getBean("offLineOrderService");
    FinanicalStatisticService
        finanicalStatisticService =
        (FinanicalStatisticService) applicationContext.getBean("finanicalStatisticService");
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

    List<Object[]> objects = offLineOrderService.countTransferMoney(start, end);

    for (Object[] object : objects) {
      try {
        finanicalStatisticService.createFinancialstatistic(object,end);
      } catch (Exception e) {
        log.error("商户ID为" + object[0] + "的商户统计出现问题");
      }
    }

  }
}
