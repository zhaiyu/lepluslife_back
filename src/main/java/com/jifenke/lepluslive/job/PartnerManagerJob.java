package com.jifenke.lepluslive.job;

import com.jifenke.lepluslive.order.service.FinanicalStatisticService;
import com.jifenke.lepluslive.order.service.OffLineOrderService;
import com.jifenke.lepluslive.partner.service.PartnerService;

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
 * Created by wcg on 16/5/5.
 */
@Component
public class PartnerManagerJob implements Job {

  private static final Logger log = LoggerFactory.getLogger(PartnerManagerJob.class);


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
    PartnerService partnerService =
        (PartnerService) applicationContext.getBean("partnerService");
    partnerService.calculateActivityMember();

  }
}
