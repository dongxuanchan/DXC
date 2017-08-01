package com.blch.quartz.job;

import com.blch.Util.Mail;
import com.blch.quartz.service.SampleService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: cxdong
 * Date: 6/9/17
 * Time: 11:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class SampleJob2 implements Job {
  private static final Logger logger = LoggerFactory.getLogger(SampleJob2.class);

  @Autowired
  private SampleService service;

  @Override
  public void execute(JobExecutionContext jobExecutionContext) {
    logger.info("invoke SampleJob2.execute()");
    service.hello();
  }

}
