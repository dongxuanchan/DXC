package com.blch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandling {

  private static final Logger logger = LoggerFactory.getLogger(ExceptionHandling.class);


  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ModelAndView badParameterInURL(HttpServletRequest req, Exception ex) {
    //logger.error("Request: " + req.getRequestURL() + " raised " + ex);
    logger.error("badParameterInURL()",ex);
    ModelAndView mav = new ModelAndView();
    //mav.addObject("url", req.getRequestURL());
    mav.addObject("code", "400");
    mav.addObject("msg", "Đường link không hợp lệ !");
    //mav.addObject("desc", ex.getCause());
    mav.setViewName("error");
    return mav;
  }

  @ExceptionHandler(InternalAuthenticationServiceException.class)
  public ModelAndView badAuthentication(HttpServletRequest req, Exception ex) {
    //logger.error("Request: " + req.getRequestURL() + " raised " + ex);
    logger.error("badAuthentication()",ex);
    ModelAndView mav = new ModelAndView();
    //mav.addObject("url", req.getRequestURL());
    mav.addObject("code", "xxx");
    mav.addObject("msg", "Lỗi Database !");
    //mav.addObject("desc", ex.getCause());
    mav.setViewName("error");
    return mav;
  }

  @ExceptionHandler(MultipartException.class)
  public ModelAndView loiUploadFile(MultipartException  ex) {
    ex.printStackTrace();
    logger.error("loiUploadFile()",ex);
    ModelAndView mav = new ModelAndView();
    mav.addObject("code", 1);
    mav.addObject("msg", ex.getCause().getMessage());
    mav.addObject("exception", ex);
    mav.setViewName("adminimportexcel");
    return mav;
  }

  @ExceptionHandler(Exception.class)
  public ModelAndView handleError(Exception ex) {
    ex.printStackTrace();
    logger.error("handleError()",ex);
    ModelAndView mav = new ModelAndView();
    mav.addObject("code", "xxx");
    mav.addObject("msg", "Lỗi kỹ thuật !");
    mav.addObject("exception", ex);
    mav.setViewName("error");
    return mav;
  }

}
