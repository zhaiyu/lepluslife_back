package com.jifenke.lepluslive.job;

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
import java.util.Calendar;
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
    log.error("not error----------YBLedgerStatementJob beginning-----" + new Date().getTime());
    System.out.println("not error----------YBLedgerStatementJob beginning-----" + new Date().getTime());
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
        (YBOrderService) applicationContext.getBean("ybOrderService");
    LedgerSettlementService
        ledgerSettlementService =
        (LedgerSettlementService) applicationContext.getBean("ledgerSettlementService");

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String tradeDate = sdf.format(date);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, -11);
    String beginDate = sdf.format(calendar.getTime());
    calendar.add(Calendar.DAY_OF_MONTH, 10);
    String endDate = sdf.format(calendar.getTime());
    //查询最近十日结算状态非终态的结算单，如果成功，修改状态并修改对应门店结算单状态，并发送消息
    List<Map<String, Object>>
        settlementList =
        ybOrderService.findSettlementByStateAndTradeDate(beginDate, endDate);
    for (Map<String, Object> map : settlementList) {
      try {
        Map<String, String>
            queryMap =
            YbRequestUtils.querySettlement("" + map.get("ledgerNo"), "" + map.get("tradeDate"));
        ledgerSettlementService
            .updateLedgerSettlement(queryMap, Long.valueOf("" + map.get("id")), tradeDate);
      } catch (Exception e) {
        log.error(
            "易宝-商户号为" + map.get("ledgerNo") + "结算日期为" + map.get("tradeDate") + "的通道结算单查询出现问题");
      }
    }

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
    log.error("not error----------YBLedgerStatementJob stop-----" + new Date().getTime());
    System.out.println("not error----------YBLedgerStatementJob stop-----" + new Date().getTime());
  }
}
