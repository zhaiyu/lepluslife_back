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


  @Bean
  public SchedulerFactoryBean schedulerFactory() {
    Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
    SchedulerFactoryBean bean = new SchedulerFactoryBean();
    if (activeProfiles
        .contains(Constants.SPRING_PROFILE_PRODUCTION)) {
      bean.setConfigLocation(resourceLoader.getResource("classpath:quartz.properties"));
      bean.setApplicationContextSchedulerContextKey("applicationContextKey");
      bean.setDataSource(dataSource);
      bean.setTriggers(cronTriggerBean().getObject(), wxCronTriggerBean().getObject(),
                       scoreAAccountAddCronTriggerBean().getObject());
      bean.setSchedulerName("orderConfrim");
    }
    return bean;
  }
}
