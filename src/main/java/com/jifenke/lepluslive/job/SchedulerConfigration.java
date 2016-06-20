package com.jifenke.lepluslive.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

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

  @Bean
  public SchedulerFactoryBean schedulerFactory() {
    SchedulerFactoryBean bean = new SchedulerFactoryBean();
    bean.setConfigLocation(resourceLoader.getResource("classpath:quartz.properties"));
    bean.setApplicationContextSchedulerContextKey("applicationContextKey");
    bean.setDataSource(dataSource);
    bean.setTriggers(cronTriggerBean().getObject(), wxCronTriggerBean().getObject());
    bean.setSchedulerName("orderConfrim");
    // bean.setTriggers (orderTrigger);
    return bean;
  }
}
