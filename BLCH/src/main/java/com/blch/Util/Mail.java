package com.blch.Util;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: cxdong
 * Date: 6/12/17
 * Time: 11:26 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class Mail {

  private static final Logger logger = LoggerFactory.getLogger(Mail.class);

  @Autowired
  private JavaMailSender sender;

  @Autowired
  private VelocityEngine velocityEngine;


  public void sendSimpleEmail(String from, String recipient, String subject,  String content){
    MimeMessage message = sender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);
    try {
        helper.setFrom(from);
        helper.setTo(recipient);
        helper.setSubject(subject);
        helper.setText(content);
        sender.send(message);
    } catch (Exception e) {
      logger.error("sendSimpleEmail() error ",e);
    }
  }

  public void sendHtmlEmail(String from, String recipient, String subject,  String content){
    MimeMessage message = sender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);
    try {
      helper.setFrom(from);
      helper.setTo(recipient);
      helper.setSubject(subject);
      helper.setText(content,true);
      sender.send(message);
    } catch (Exception e) {
      logger.error("sendHtmlEmail() error ",e);
    }
  }

  public void sendEmailWithAtts(String from,String recipient, String subject,  String content, String filename, File f){
    MimeMessage message = sender.createMimeMessage();
    try {
      // Enable the multipart flag!
      MimeMessageHelper helper = new MimeMessageHelper(message,true);
      helper.setFrom(from);
      helper.setTo(recipient);
      helper.setSubject(subject);
      helper.setText(content);
      helper.addAttachment(filename, f);
      sender.send(message);
    } catch (Exception e) {
      logger.error("sendEmailWithAtts() error ",e);
    }
  }

  public void sendEmailWithTemplate(String from, String recipient, String subject, String templocation, Map content){
    MimeMessage message = sender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);
    try {
      String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templocation, content);
      helper.setFrom(from);
      helper.setTo(recipient);
      helper.setSubject(subject);
      helper.setText(text,true);
      sender.send(message);
    } catch (Exception e) {
      logger.error("sendEmailWithTemplate() error ",e);
    }
  }

}
