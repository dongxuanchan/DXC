package com.blch.config;

/**
 * Created with IntelliJ IDEA.
 * User: cxdong
 * Date: 6/8/17
 * Time: 3:24 PM
 * To change this template use File | Settings | File Templates.
 */
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import com.blch.Util.Util;
import com.blch.quartz.job.SampleJob;
import com.blch.quartz.job.SampleJob2;
import liquibase.integration.spring.SpringLiquibase;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * @author pavan.solapure
 *
 */

@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class ConfigureQuartz {

  /*job1 setting*/
  @Bean(name = "testRunJobDetail1")
  public JobDetailFactoryBean testRunJobDetail1() {
    //return null;
    return createJobDetail(SampleJob.class);
  }
  @Bean(name = "sampleJobTrigger")
  public CronTriggerFactoryBean sampleJobTrigger(@Qualifier("testRunJobDetail1") JobDetail jobDetail,
                                                   @Value("${samplejob.cronexpression}") String cronExpression) {
    return createCronTrigger(jobDetail, cronExpression);
  }

  /*job2 setting*/
  @Bean(name = "testRunJobDetail2")
  public JobDetailFactoryBean testRunJobDetail2() {
    return createJobDetail(SampleJob2.class);
  }
  @Bean(name = "sampleJobTrigger2")
  public SimpleTriggerFactoryBean sampleJobTrigger2(@Qualifier("testRunJobDetail2") JobDetail jobDetail,
                                                  @Value("${samplejob.frequency}") long frequency) {
    return createTrigger(jobDetail, frequency);
  }

  /*apply jobs to factory*/
  @Bean
  public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, JobFactory jobFactory,
                                                   @Qualifier("sampleJobTrigger") Trigger sampleJobTrigger,
                                                   @Qualifier("sampleJobTrigger2") Trigger sampleJobTrigger2) throws IOException {
    SchedulerFactoryBean factory = new SchedulerFactoryBean();
    // this allows to update triggers in DB when updating settings in config file:
    factory.setOverwriteExistingJobs(true);
    factory.setDataSource(dataSource);
    factory.setJobFactory(jobFactory);

    factory.setQuartzProperties(quartzProperties());
    //factory.setTriggers(sampleJobTrigger,sampleJobTrigger2);

    return factory;
  }

  @Bean
  public JobFactory jobFactory(ApplicationContext applicationContext,
                               // injecting SpringLiquibase to ensure liquibase is already initialized and created the quartz tables:
                               SpringLiquibase springLiquibase)
  {
    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
    jobFactory.setApplicationContext(applicationContext);
    return jobFactory;
  }

  @Bean
  public Properties quartzProperties() throws IOException {
    PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
    propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
    propertiesFactoryBean.afterPropertiesSet();
    return propertiesFactoryBean.getObject();
  }

  private static JobDetailFactoryBean createJobDetail(Class jobClass) {
    JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
    factoryBean.setJobClass(jobClass);
    // job has to be durable to be stored in DB:
    factoryBean.setDurability(true);
    return factoryBean;
  }

  private static SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail, long pollFrequencyMs) {
    SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
    factoryBean.setJobDetail(jobDetail);
    factoryBean.setStartDelay(0L);
    factoryBean.setRepeatInterval(pollFrequencyMs);
    factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
    // in case of misfire, ignore all missed triggers and continue :
    factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
    return factoryBean;
  }

  // Use this method for creating cron triggers instead of simple triggers:
  private static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression) {
    CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
    factoryBean.setJobDetail(jobDetail);
    factoryBean.setCronExpression(cronExpression);
    factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
    return factoryBean;
  }

}