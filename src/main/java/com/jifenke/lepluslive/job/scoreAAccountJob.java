package com.jifenke.lepluslive.job;

import com.jifenke.lepluslive.scoreAAccount.service.ScoreAAccountService;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
* Created by Administrator on 2016/9/20.
*/
public class scoreAAccountJob implements Job {
  private static final Logger log = LoggerFactory.getLogger(scoreAAccountJob.class);
  @Override
  public void execute(JobExecutionContext context){
    ApplicationContext  applicationContext = null;
    try {

      applicationContext =
          (ApplicationContext) context.getScheduler().getContext().get("applicationContextKey");
    } catch (SchedulerException e) {
      log.error("jobExecutionContext.getScheduler().getContext() error!", e);
      throw new RuntimeException(e);
    }
    ScoreAAccountService scoreAAccountService = (ScoreAAccountService) applicationContext.getBean("scoreAAccountService");
    scoreAAccountService.addOneDayScoreAAccountData();
  }

}
