package com.blch.quartz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: cxdong
 * Date: 6/9/17
 * Time: 11:00 AM
 * To change this template use File | Settings | File Templates.
 */

@Service
public class SampleService {
  private static final Logger logger = LoggerFactory.getLogger(SampleService.class);

  public void hello() {
    logger.info("Hello DXC!");
  }
}
