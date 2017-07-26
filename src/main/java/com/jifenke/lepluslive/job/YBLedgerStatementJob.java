package com.jifenke.lepluslive.job;

import com.jifenke.lepluslive.yibao.service.YBOrderService;
import com.jifenke.lepluslive.yibao.util.YbRequestUtils;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 易宝通道结算单(每日凌晨5点统计) Created by zhangwen on 17/07/25.
 */
@Component
public class YBLedgerStatementJob implements Job {

  private static final Logger log = LoggerFactory.getLogger(YBLedgerStatementJob.class);


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
    YBOrderService
        ybOrderService =
        (YBOrderService) applicationContext.getBean("YBOrderService");

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    final String tradeDate = sdf.format(new Date());

    //获取所有的审核通过的易宝子商户号
    Set<String> allLedgers = ybOrderService.findAllLedgers();

    //第一次统计
    allLedgers.forEach(ledger -> {
      try {
        Map<String, String> map = YbRequestUtils.querySettlement(ledger, tradeDate);
        if ("1".equals(map.get("code"))) {
          //插入一条通道结算单

        }
      } catch (Exception e) {
        log.error("易宝-商户号为" + ledger + "的通道结算单统计出现问题");
      }

    });
  }
}
