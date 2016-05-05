package com.jifenke.lepluslive.job;

import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.ArrayList;

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
  public JobDetailFactoryBean offLineOrderDetail(OffLineOrderJob scheduledTasks){
    JobDetailFactoryBean bean = new JobDetailFactoryBean ();
    bean.setJobClass(OffLineOrderJob.class);
    bean.setDurability(false);
    return bean;
  }

  @Bean(name = "offLineOrderTrigger")
    public CronTriggerFactoryBean cronTriggerBean(JobDetailFactoryBean offLineOrderDetail){
    CronTriggerFactoryBean tigger = new CronTriggerFactoryBean ();
    tigger.setJobDetail(offLineOrderDetail.getObject());
    try {
      //tigger.setCronExpression ("*/5 * * * * ? ");//每天凌晨1点执行
      tigger.setCronExpression ("0 0 1 * * ? ");//每天凌晨1点执行
    } catch (Exception e) {
      e.printStackTrace ();
    }
    return tigger;

  }

  @Bean
  public SchedulerFactoryBean schedulerFactory(CronTriggerFactoryBean cronTriggerBean ){
    SchedulerFactoryBean bean = new SchedulerFactoryBean ();
    bean.setConfigLocation(resourceLoader.getResource("classpath:quartz.properties"));
    bean.setApplicationContextSchedulerContextKey("applicationContextKey");
    bean.setDataSource(dataSource);
    ArrayList<Trigger> trigger = new ArrayList<>();
    bean.setTriggers(cronTriggerBean.getObject());
    bean.setSchedulerName("orderConfrim");
   // bean.setTriggers (orderTrigger);
    return bean;
  }
}
