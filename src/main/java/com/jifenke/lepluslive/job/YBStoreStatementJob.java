package com.jifenke.lepluslive.job;

import com.jifenke.lepluslive.yibao.service.StoreSettlementService;
import com.jifenke.lepluslive.yibao.service.YBOrderService;

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
 * 易宝门店结算单(每日凌晨4点统计) Created by zhangwen on 17/07/25.
 */
@Component
public class YBStoreStatementJob implements Job {

  private static final Logger log = LoggerFactory.getLogger(YBStoreStatementJob.class);


  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    log.error("not error----------YBStoreStatementJob beginning-----" + new Date().getTime());
    System.out.println("not error----------YBStoreStatementJob beginning-----" + new Date().getTime());
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
    StoreSettlementService
        storeSettlementService =
        (StoreSettlementService) applicationContext.getBean("storeSettlementService");
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    Date start = calendar.getTime();
    String tradeDate = sdf.format(start);

    //每日易宝订单统计
    List<Map<String, Object>> list = ybOrderService.countStoreStatementGroupByMerchantId(tradeDate);
    //每日退款完成统计
    Map<String, Object> refundMap = ybOrderService.countRefundGroupByMerchantId(tradeDate);

    for (Map<String, Object> map : list) {
      try {
        storeSettlementService.createStoreSettlement(map, refundMap, tradeDate);
      } catch (Exception e) {
        log.error("易宝-门店ID为" + map.get("merchantId") + "的门店结算单统计出现问题");
      }
    }
    log.error("not error----------YBStoreStatementJob stop-----" + new Date().getTime());
    System.out.println("not error----------YBStoreStatementJob stop-----" + new Date().getTime());
  }
}
