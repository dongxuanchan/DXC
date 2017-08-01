package com.blch.controller;


import com.blch.Util.Constants;
import com.blch.beans.Canho;
import com.blch.dao.CustomMethodsImpl;
import com.blch.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;

@Controller
public class AdminController {

  private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

  //@Autowired
  //DataSource dataSource;

  @Autowired
  CustomMethodsImpl customMethodsImpl;

  @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.GET})
  public String index() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //check if user was logged in
    if (!(auth instanceof AnonymousAuthenticationToken)) {
      Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)auth.getAuthorities();
      for (GrantedAuthority authority : authorities) {
        if(authority.getAuthority().equals(Constants.ADMIN_ROLE)){
          return "redirect:/admin/home";
        }
      }
      return "redirect:/user/home";
    }
    return "login";
  }

  @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
  public String adminHome1() {
    return "adminhome1";
  }

  @RequestMapping(value="/admin/home/limit/{limit}/page/{page}",method = RequestMethod.GET)
  public String adminHome2(@PathVariable int limit,
                         @PathVariable int page, ModelMap model) {
    if((limit!=5 && limit!=10 && limit!=20) || page<1){
      throw new MethodArgumentTypeMismatchException(null,null,null,null,null);
    }
    model.addAttribute("page", page);
    model.addAttribute("limit", limit);
    return "adminhome1";
  }

  /*@RequestMapping(value = "/user/home", method = RequestMethod.POST)
  public String dangnhap(@ModelAttribute("SpringWeb")User user, ModelMap model) {
    int idkh= customMethodsImpl.kiemtraDangnhap(user.getUsername(),user.getPassword());
    if(idkh==-1){
      model.addAttribute("listcanho", null);
    } else{
      List canhoList= customMethodsImpl.getCanHoKhachHang(idkh);
      model.addAttribute("listcanho", canhoList);
    }
    return "userhome";
  }*/

  @RequestMapping(value = "/user/home", method = RequestMethod.GET )
  public String userHome(ModelMap model, Principal principal, HttpSession session) {
    //System.out.println("SPRING_SECURITY_CONTEXT: " + session.getAttribute("SPRING_SECURITY_CONTEXT"));
    //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //UserDetails userDetail = (UserDetails) auth.getPrincipal();

    //session.invalidate();

    int idkh= customMethodsImpl.getKhachhangByTendangnhapUser(principal.getName());
    if(idkh==-1){
      model.addAttribute("listcanho", null);
    } else{
      List canhoList= customMethodsImpl.getCanHoKhachHangUser(idkh);
      model.addAttribute("listcanho", canhoList);
    }
    return "userhome";
  }

 /* @RequestMapping(value = {"/home"}, method = {RequestMethod.GET,RequestMethod.POST} )
  public String defaultPage() {
    *//*ModelAndView model = new ModelAndView();
    model.addObject("title", "Spring Security Login Form - Database Authentication");
    model.addObject("message", "This is default page!");
    model.setViewName("hello");*//*
    return "home";

  }*/

  @RequestMapping(value = "/dangnhap", method = RequestMethod.GET)
  public ModelAndView login(@RequestParam(value = "loi", required = false) String error,
                            @RequestParam(value = "thoat", required = false) String logout) {

    ModelAndView model = new ModelAndView();
    if (error != null) {
      model.addObject("error", "Lỗi đăng nhập !");
    }

    if (logout != null) {
      model.addObject("msg", "Bạn đã đăng xuất thành công.");
    }
    //page to return incase of wrong pass or logout
    model.setViewName("login");

    return model;

  }

  //for 403 access denied page
  @RequestMapping(value = "/403", method = RequestMethod.GET)
  public String accesssDenied() {

    /*ModelAndView model = new ModelAndView();

    //check if user is login
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (!(auth instanceof AnonymousAuthenticationToken)) {
      UserDetails userDetail = (UserDetails) auth.getPrincipal();
      model.addObject("username", userDetail.getUsername());
    }

    model.setViewName("403");*/
    return "403";

  }

  //for 404 access denied page
  @RequestMapping(value = "/404", method = RequestMethod.GET)
  public String notFound() {
    return "404";
  }

  @RequestMapping(value = "/admin/bangiao", method = RequestMethod.GET)
  public String adminBangiao1() {
    return "adminbangiao";
  }

  @RequestMapping(value="/admin/bangiao/limit/{limit}/page/{page}",method = RequestMethod.GET)
  public String adminBangiao2(@PathVariable int limit,
                           @PathVariable int page, ModelMap model) {
    if((limit!=5 && limit!=10 && limit!=20) || page<1){
      throw new MethodArgumentTypeMismatchException(null,null,null,null,null);
    }
    model.addAttribute("page", page);
    model.addAttribute("limit", limit);
    return "adminbangiao";
  }

  @RequestMapping(value = "/admin/user/{idkh}", method = RequestMethod.GET)
  public String updateUser(@PathVariable int idkh, ModelMap model) {
    List kh= customMethodsImpl.getUserDetail(idkh);
    model.addAttribute("khachhang", kh);
    return "adminupdatekhachhang";
  }

  //for angular page
  @RequestMapping(value = "/admin/user2/{idkh}", method = RequestMethod.GET)
  public String updateUser2(@PathVariable int idkh, ModelMap model) {
    model.addAttribute("idkhachhang", idkh);
    return "adminupdatekhachhang1";
  }

  @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
  public String adminKhachhang1() {
    return "adminkhachhang1";
  }

  @RequestMapping(value="/admin/users/limit/{limit}/page/{page}",method = RequestMethod.GET)
  public String adminKhachhang2(@PathVariable int limit,
                           @PathVariable int page, ModelMap model) {
    if((limit!=5 && limit!=10 && limit!=20) || page<1){
      throw new MethodArgumentTypeMismatchException(null,null,null,null,null);
    }
    model.addAttribute("page", page);
    model.addAttribute("limit", limit);
    return "adminkhachhang1";
  }

/*  @RequestMapping(value = "/admin/newuser", method = RequestMethod.GET)
  public String adminNewUser() {
    return "admincreatekhachhang";
  }*/

  @GetMapping("/admin/uploadexcelfile")   //new annotation since 4.3
  public String uloadExcelFile1() {
    return "adminimportexcel";
  }

  @PostMapping("/admin/uploadexcelfile")    //new annotation since 4.3
  public String uloadExcelFile2(@RequestParam("file") MultipartFile file, ModelMap model) {
    //try{
    String filepath = customMethodsImpl.uloadExcelFile(file,model);
    customMethodsImpl.importExcelFile(filepath,model);
    /*}catch (MultipartException ex){
      ex.printStackTrace();
      model.addAttribute("code", 1);
      model.addAttribute("msg", ex.getCause().getMessage());
    }*/
    return "adminimportexcel";
  }


}
