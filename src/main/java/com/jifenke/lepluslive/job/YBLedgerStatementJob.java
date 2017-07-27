package com.jifenke.lepluslive.job;

import com.jifenke.lepluslive.global.util.DataUtils;
import com.jifenke.lepluslive.yibao.service.LedgerSettlementService;
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
import java.util.List;
import java.util.Map;

/**
 * 易宝通道结算单(每日早上12点统计) Created by zhangwen on 17/07/25.
 */
@Component
public class YBLedgerStatementJob implements Job {

  private static final Logger log = LoggerFactory.getLogger(YBLedgerStatementJob.class);

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {

    //周六周日不生成通道结算单
    int day = DataUtils.getDay(new Date());
    if (day == 1 || day == 7) {
      return;
    }
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
    LedgerSettlementService
        ledgerSettlementService =
        (LedgerSettlementService) applicationContext.getBean("LedgerSettlementService");

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    final String tradeDate = sdf.format(new Date());

    //获取所有的审核通过的易宝子商户号
    List<Map<String, Object>> list = ybOrderService.findAllLedgers();

    for (Map<String, Object> map : list) {
      try {
        Map<String, String>
            queryMap =
            YbRequestUtils.querySettlement("" + map.get("ledgerNo"), tradeDate);
        ledgerSettlementService.createLedgerSettlement(queryMap, map);
      } catch (Exception e) {
        log.error("易宝-商户号为" + map.get("ledgerNo") + "的通道结算单统计出现问题");
      }
    }
  }
}
