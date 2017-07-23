package com.jifenke.lepluslive.job;

import com.jifenke.lepluslive.fuyou.service.ScanCodeStatementService;
import com.jifenke.lepluslive.order.service.ScanCodeOrderService;
import com.jifenke.lepluslive.yibao.service.YBOrderService;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 易宝订单生成两种结算单 Created by zhangwen on 17/07/20.
 */
@Component
public class YBOrderJob implements Job {

  private static final Logger log = LoggerFactory.getLogger(YBOrderJob.class);


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
    ScanCodeStatementService
        statementService =
        (ScanCodeStatementService) applicationContext.getBean("scanCodeStatementService");
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    Date start = calendar.getTime();
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    calendar.add(Calendar.SECOND, -1);
    Date end = calendar.getTime();

    String currDay2 = sdf.format(start);
    String currDay = currDay2 + "%";

    List<Map<String, Object>> list = ybOrderService.countTransferGroupByMerchantNum(currDay);

    for (Map<String, Object> map : list) {
      try {
//        statementService.createScanCodeSettleOrder(object, currDay2);
      } catch (Exception e) {
        log.error("商户号为" + map.get("ledgerNo") + "的统计出现问题");
      }
    }


    //根据昨天的已完成退款单冲正两种结算
    try {
      statementService.reverseStatement(start, end, currDay2);
    } catch (ParseException e) {
      log.error("根据昨天的已完成退款单冲正两种结算出现问题");
    }


  }
}
