package com.jifenke.lepluslive.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 富友扫码订单生成两种分润单 Created by zhangwen on 16/12/21.
 */
@Component
public class ScanCodeOrderJob implements Job {

  private static final Logger log = LoggerFactory.getLogger(ScanCodeOrderJob.class);


  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
//    ApplicationContext
//        applicationContext = null;
//    try {
//
//      applicationContext =
//          (ApplicationContext) context.getScheduler().getContext().get("applicationContextKey");
//    } catch (SchedulerException e) {
//      log.error("jobExecutionContext.getScheduler().getContext() error!", e);
//      throw new RuntimeException(e);
//    }
//    ScanCodeOrderService
//        orderService =
//        (ScanCodeOrderService) applicationContext.getBean("scanCodeOrderService");
//    ScanCodeStatementService
//        statementService =
//        (ScanCodeStatementService) applicationContext.getBean("scanCodeStatementService");
//    Date date = new Date();
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//    Calendar calendar = Calendar.getInstance();
//    calendar.setTime(date);
//    calendar.add(Calendar.DAY_OF_MONTH, -1);
//    calendar.set(Calendar.HOUR_OF_DAY, 0);
//    calendar.set(Calendar.MINUTE, 0);
//    calendar.set(Calendar.SECOND, 0);
//    Date start = calendar.getTime();
//    calendar.add(Calendar.DAY_OF_MONTH, 1);
//    calendar.add(Calendar.SECOND, -1);
//    Date end = calendar.getTime();
//
//    String currDay2 = sdf.format(start);
//    String currDay = currDay2 + "%";
//
//    List<Object[]> o1 = orderService.countTransferGroupByMerchantNum(currDay);
//    List<Object[]> o2 = orderService.countTransferGroupByMerchant(currDay);
//
//    for (Object[] object : o1) {
//      try {
//        statementService.createScanCodeSettleOrder(object, currDay2);
//      } catch (Exception e) {
//        log.error("商户号为" + object[0] + "的统计出现问题");
//      }
//    }
//
//    for (Object[] object : o2) {
//      try {
//        statementService.createScanCodeMerchantStatement(object, currDay2);
//      } catch (Exception e) {
//        log.error("商户ID为" + object[0] + "的商户结算单统计出现问题");
//      }
//    }
//
//    //根据昨天的已完成退款单冲正两种结算
//    try {
//      statementService.reverseStatement(start, end, currDay2);
//    } catch (ParseException e) {
//      log.error("根据昨天的已完成退款单冲正两种结算出现问题");
//    }


  }
}
