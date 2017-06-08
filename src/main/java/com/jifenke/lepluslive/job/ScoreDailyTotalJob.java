package com.jifenke.lepluslive.job;

import com.jifenke.lepluslive.score.service.ScoreDailyTotalService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * ScoreDailyTotalJob
 *   每日钱包数据汇总记录
 * @author XF
 * @date 2017/6/8
 */
@Component
public class ScoreDailyTotalJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(OffLineOrderJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ApplicationContext
                applicationContext = null;
        try {
            applicationContext =
                    (ApplicationContext) context.getScheduler().getContext().get("applicationContextKey");
        } catch (SchedulerException e) {
            log.error("[ScoreDailyTotalJob] - jobExecutionContext.getScheduler().getContext() error!", e);
            throw new RuntimeException(e);
        }
        try {
            ScoreDailyTotalService scoreDailyTotalService = (ScoreDailyTotalService)applicationContext.getBean("scoreDailyTotalService");
            scoreDailyTotalService.saveScoreDailyTotal();
        } catch (Exception e) {
            log.error("[ScoreDailyTotalJob] - scoreDailyTotalService.saveScoreDailyTotal() error!", e);
            throw new RuntimeException(e);
        }
    }
}
