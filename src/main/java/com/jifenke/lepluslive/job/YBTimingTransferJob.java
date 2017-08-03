package com.jifenke.lepluslive.job;

import com.jifenke.lepluslive.yibao.service.LedgerTransferService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 易宝定时转账(每日23:35统计) Created by zhangwen on 17/07/20.
 */
@Component
public class YBTimingTransferJob implements Job {

  private static final Logger log = LoggerFactory.getLogger(YBTimingTransferJob.class);

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    log.error("not error----------YBTimingTransferJob beginning-----" + new Date().getTime());
    System.out.println("not error----------YBTimingTransferJob beginning-----" + new Date().getTime());
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
    LedgerTransferService
        ledgerTransferService =
        (LedgerTransferService) applicationContext.getBean("ledgerTransferService");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String tradeDate = sdf.format(new Date());

    //每日易宝订单统计
    List<Map<String, Object>> list = ybOrderService.countTransferGroupByMerchantNum(tradeDate);

    //每日退款完成统计
    Map<String, Object> objectMap = ybOrderService.countRefundGroupByMerchantNum(tradeDate);

    for (Map<String, Object> map : list) {
      try {
        //获取商户号今日退款金额
        String ledgerNo = "" + map.get("ledgerNo");
        Long transferMoney = Long.valueOf("" + map.get("transferMoney"));
        if (objectMap.get(ledgerNo) != null) {
          transferMoney -= Long.valueOf("" + objectMap.get(ledgerNo));
        }
        if (transferMoney > 0) {
          ledgerTransferService.transfer(ledgerNo, transferMoney, tradeDate, 2);
        } else {
          log.error("易宝商户号为" + map.get("ledgerNo") + "的定时转账统计出现负数问题");
        }
      } catch (Exception e) {
        log.error("易宝商户号为" + map.get("ledgerNo") + "的定时转账统计出现问题");
      }
    }
    log.error("not error----------YBTimingTransferJob stop-----" + new Date().getTime());
    System.out.println("not error----------YBTimingTransferJob stop-----" + new Date().getTime());
  }
}
