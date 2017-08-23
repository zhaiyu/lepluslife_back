package com.jifenke.lepluslive.job;

import com.jifenke.lepluslive.global.config.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Arrays;
import java.util.Collection;

import javax.inject.Inject;
import javax.sql.DataSource;


/**
 * Created by wcg on 16/3/27.
 */
@Configuration
public class SchedulerConfigration {

  @Inject
  private DataSource dataSource;

  @Autowired
  private ResourceLoader resourceLoader;

  @Inject
  private Environment env;

  @Bean(name = "offLineOrderDetail")
  public JobDetailFactoryBean offLineOrderDetail() {
    JobDetailFactoryBean bean = new JobDetailFactoryBean();
    bean.setJobClass(OffLineOrderJob.class);
    bean.setDurability(false);
    return bean;
  }

  @Bean(name = "offLineOrderTrigger")
  public CronTriggerFactoryBean cronTriggerBean() {
    CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
    tigger.setJobDetail(offLineOrderDetail().getObject());
    try {
      //tigger.setCronExpression ("0 0/5 * * * ? ");//每天凌晨1点执行
      tigger.setCronExpression("0 0 1 * * ? ");//每天凌晨1点执行
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tigger;
  }

//  @Bean(name = "scanCodeOrderDetail")
//  public JobDetailFactoryBean scanCodeOrderDetail() {
//    JobDetailFactoryBean bean = new JobDetailFactoryBean();
//    bean.setJobClass(ScanCodeOrderJob.class);
//    bean.setDurability(false);
//    return bean;
//  }
//
//  @Bean(name = "scanCodeOrderTrigger")
//  public CronTriggerFactoryBean scanCodeCronTriggerBean() {
//    CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
//    tigger.setJobDetail(scanCodeOrderDetail().getObject());
//    try {
//      tigger.setCronExpression("0 0 3 * * ? ");//每天凌晨3点执行
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//    return tigger;
//  }

  /**
   * 易宝订单定时转账  23:35
   */
  @Bean(name = "ybTimingTransferDetail")
  public JobDetailFactoryBean ybTimingTransferDetail() {
    JobDetailFactoryBean bean = new JobDetailFactoryBean();
    bean.setJobClass(YBTimingTransferJob.class);
    bean.setDurability(false);
    return bean;
  }

  @Bean(name = "ybTimingTransferTrigger")
  public CronTriggerFactoryBean ybTimingTransferTriggerBean() {
    CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
    tigger.setJobDetail(ybTimingTransferDetail().getObject());
    try {
      tigger.setCronExpression("0 35 23 * * ? ");//每天晚上11:35点执行
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tigger;
  }

  /**
   * 易宝门店结算单(每日凌晨4点统计)
   */
  @Bean(name = "ybStoreStatementDetail")
  public JobDetailFactoryBean ybStoreStatementDetail() {
    JobDetailFactoryBean bean = new JobDetailFactoryBean();
    bean.setJobClass(YBStoreStatementJob.class);
    bean.setDurability(false);
    return bean;
  }

  @Bean(name = "ybStoreStatementTrigger")
  public CronTriggerFactoryBean ybStoreStatementTriggerBean() {
    CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
    tigger.setJobDetail(ybStoreStatementDetail().getObject());
    try {
      tigger.setCronExpression("0 0 4 * * ? ");//每天早上04:00点执行
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tigger;
  }

  /**
   * 易宝通道结算单(每日早上12点统计)
   */
  @Bean(name = "ybLedgerStatementDetail")
  public JobDetailFactoryBean ybLedgerStatementDetail() {
    JobDetailFactoryBean bean = new JobDetailFactoryBean();
    bean.setJobClass(YBLedgerStatementJob.class);
    bean.setDurability(false);
    return bean;
  }

  @Bean(name = "ybLedgerStatementTrigger")
  public CronTriggerFactoryBean ybLedgerStatementTriggerBean() {
    CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
    tigger.setJobDetail(ybLedgerStatementDetail().getObject());
    try {
      tigger.setCronExpression("0 0 12 * * ? ");//每天早上12:00点执行
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tigger;
  }

  /**
   * 团购结算统计(每日早上5:00统计) Created by zhangwen on 17/08/23.
   */
  @Bean(name = "grouponStatisticDetail")
  public JobDetailFactoryBean grouponStatisticDetail() {
    JobDetailFactoryBean bean = new JobDetailFactoryBean();
    bean.setJobClass(GrouponStatisticJob.class);
    bean.setDurability(false);
    return bean;
  }

  @Bean(name = "grouponStatisticTrigger")
  public CronTriggerFactoryBean grouponStatisticTriggerBean() {
    CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
    tigger.setJobDetail(grouponStatisticDetail().getObject());
    try {
      tigger.setCronExpression("0 0 5 * * ? ");//每天早上5:00点执行
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tigger;
  }

  @Bean(name = "wxRefreshDetail")
  public JobDetailFactoryBean wxRefreshDetail() {
    JobDetailFactoryBean bean = new JobDetailFactoryBean();
    bean.setJobClass(WeixinRefreshJob.class);
    bean.setDurability(false);
    return bean;
  }

  @Bean(name = "wxRefreshTrigger")
  public CronTriggerFactoryBean wxCronTriggerBean() {
    CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
    tigger.setJobDetail(wxRefreshDetail().getObject());
    try {
      tigger.setCronExpression("0 */59 * * * ?");//每隔59分钟执行一次
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tigger;
  }

  //每天增加红包账户
  @Bean(name = "scoreAAccountAdd")
  public JobDetailFactoryBean scoreAAccountAddDetail() {
    JobDetailFactoryBean bean = new JobDetailFactoryBean();
    bean.setJobClass(scoreAAccountJob.class);
    bean.setDurability(false);
    return bean;
  }

  @Bean(name = "scoreAAccountAddTrigger")
  public CronTriggerFactoryBean scoreAAccountAddCronTriggerBean() {
    CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
    tigger.setJobDetail(scoreAAccountAddDetail().getObject());
    try {
      tigger.setCronExpression("0 0 1 * * ? ");//每天凌晨1点执行
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tigger;
  }

  //监控金币
  @Bean(name = "monitorScoreCJob")
  public JobDetailFactoryBean monitorScoreCDetail() {
    JobDetailFactoryBean bean = new JobDetailFactoryBean();
    bean.setJobClass(ScoreCMonitorJob.class);
    bean.setDurability(false);
    return bean;
  }

  @Bean(name = "monitorScoreCTrigger")
  public CronTriggerFactoryBean monitorScoreCTriggerBean() {
    CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
    tigger.setJobDetail(monitorScoreCDetail().getObject());
    try {
      tigger.setCronExpression("0 0 9 * * ? ");//每天早九点执行
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tigger;
  }

  /**
   * 定时保存当天账户汇总记录
   */

  @Bean(name = "socreDailyTotalDetail")
  public JobDetailFactoryBean socreDailyTotalDetail() {
    JobDetailFactoryBean bean = new JobDetailFactoryBean();
    bean.setJobClass(ScoreDailyTotalJob.class);
    bean.setDurability(false);
    return bean;
  }

  @Bean(name = "socreDailyTotalTrigger")
  public CronTriggerFactoryBean socreDailyTotalTriggerBean() {
    CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
    tigger.setJobDetail(socreDailyTotalDetail().getObject());
    try {
      tigger.setCronExpression("0 0 0 * * ?");  // 每天凌晨执行
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tigger;
  }

  /**
   * 工厂  -  设置定时任务须在此配置
   */
  @Bean
  public SchedulerFactoryBean schedulerFactory() {
    Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
    SchedulerFactoryBean bean = new SchedulerFactoryBean();
    if (activeProfiles
        .contains(Constants.SPRING_PROFILE_PRODUCTION)) {
      bean.setConfigLocation(resourceLoader.getResource("classpath:quartz.properties"));
      bean.setApplicationContextSchedulerContextKey("applicationContextKey");
      bean.setDataSource(dataSource);
      //scanCodeCronTriggerBean().getObject()
      bean.setTriggers(
          cronTriggerBean().getObject(),
          wxCronTriggerBean().getObject(),
          scoreAAccountAddCronTriggerBean().getObject(),
          monitorScoreCTriggerBean().getObject(),
          socreDailyTotalTriggerBean().getObject(),
          ybLedgerStatementTriggerBean().getObject(),
          ybStoreStatementTriggerBean().getObject(),
          ybTimingTransferTriggerBean().getObject(),
          grouponStatisticTriggerBean().getObject()
      );
      bean.setSchedulerName("orderConfrim");
    }
    return bean;
  }


}
