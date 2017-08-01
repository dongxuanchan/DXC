package com.blch.config;

/**
 * Created with IntelliJ IDEA.
 * User: cxdong
 * Date: 6/8/17
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;


/**
 * Adds autowiring support to quartz jobs.
 * Created by david on 2015-01-20.
 * @see //gist.github.com/jelies/5085593
 */
public final class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements
  ApplicationContextAware {

  private transient AutowireCapableBeanFactory beanFactory;

  @Override
  public void setApplicationContext(final ApplicationContext context) {
    beanFactory = context.getAutowireCapableBeanFactory();
  }

  @Override
  protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
    final Object job = super.createJobInstance(bundle);
    beanFactory.autowireBean(job);
    return job;
  }
}