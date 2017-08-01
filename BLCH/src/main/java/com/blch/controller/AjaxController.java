package com.blch.controller;


import com.blch.beans.Khachhang;
import com.blch.dao.CanhoRepository;
import com.blch.dao.CustomMethodsImpl;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.security.Principal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AjaxController {

  private static final Logger logger = LoggerFactory.getLogger(AjaxController.class);


  //@Autowired
  //DataSource dataSource;

/*  @Autowired
  CanhoRepository canhoRepository;*/

  @Autowired
  CustomMethodsImpl customMethodsImpl;

  @RequestMapping(value="/admin/canho",method = RequestMethod.POST)
  public Map canhoAdmin(@RequestParam(value="page")int page,
                         @RequestParam(value="numb")int numb) {
    return   customMethodsImpl.getDsCanhoTrangAdmin(page,numb);
  }

  @RequestMapping(value="/admin/chitietthanhtoan",method = RequestMethod.POST)
  public List chitietThanhtoan( @RequestParam(value="idkhachhang")int idkhachhang,
                                @RequestParam(value="idcanho")int idcanho) {
    return   customMethodsImpl.getChiTietThanhToanAdmin(idkhachhang,idcanho);
  }

  @RequestMapping(value="/admin/duyetbangiao",method = RequestMethod.POST)
  public boolean duyetBangiao(Principal principal, @RequestParam(value="idcanho")int idcanho) {
    return   customMethodsImpl.duyetBangiaoCanhoAdmin(principal,idcanho);
  }

  @RequestMapping(value="/user/chonngaybangiao",method = RequestMethod.POST)
  public boolean chonNgayBanGiao(Principal principal, @RequestParam(value="idcanho")int idcanho, @RequestParam(value="date")String date) {
    return   customMethodsImpl.chonNgayBanGiaoUser(principal,idcanho,date);
  }

  @RequestMapping(value="/admin/canhobangiao",method = RequestMethod.POST)
  public Map canhoBangiaoAdmin(@RequestParam(value="page")int page,
                               @RequestParam(value="numb")int numb) {
    return   customMethodsImpl.getDsCanhoBangiaoAdmin(page,numb);
  }

  @RequestMapping(value="/admin/duyetngaybangiao",method = RequestMethod.POST)
   public boolean duyetNgayBanGiaoAdmin(Principal principal, @RequestParam(value="idkhachhang")int idkhachhang, @RequestParam(value="idcanho")int idcanho) {
    return   customMethodsImpl.duyetNgayBanGiaoAdmin(principal,idkhachhang,idcanho);
  }

  @RequestMapping(value="/admin/chonngaybangiao",method = RequestMethod.POST)
  public boolean chonNgayBanGiaoAdmin(Principal principal, @RequestParam(value="idkhachhang")int idkhachhang, @RequestParam(value="idcanho")int idcanho, @RequestParam(value="date")String date) {
    return   customMethodsImpl.chonNgayBanGiaoAdmin(principal,idkhachhang,idcanho,date);
  }

  @RequestMapping(value="/admin/khachhang",method = RequestMethod.POST)
  public Map khachhangAdmin(@RequestParam(value="page")int page,
                               @RequestParam(value="numb")int numb) {
    return   customMethodsImpl.getDsKhachhangAdmin(page,numb);
  }

  @RequestMapping(value="/admin/capnhatkhachhang",method = RequestMethod.POST)
  public Map capnhatKhachhang(Principal principal,  @RequestParam(value="idkh")int idkh,
                                                        @RequestParam(value="name")String name,
                                                        @RequestParam(value="pass1")String pass1,
                                                        @RequestParam(value="pass2")String pass2,
                                                        @RequestParam(value="email")String email,
                                                        @RequestParam(value="phone")String phone) {
    return   customMethodsImpl.capnhatKhachhang(principal,idkh, name, pass1,pass2,email,phone);
  }

  @RequestMapping(value="/admin/khoamotaikhoankhachhang",method = RequestMethod.POST)
  public boolean khoamoTaikhoanKhachhang(Principal principal,  @RequestParam(value="type")boolean type, @RequestParam(value="user")String user) {
    return   customMethodsImpl.khoamoTaikhoanKhachhang(principal, type, user);
  }

  @RequestMapping(value="/admin/themmoitaikhoan",method = RequestMethod.POST)
  public Map themmoiTaikhoan(Principal principal,       @RequestParam(value="idkh")int idkh,
                                                        @RequestParam(value="loginname")String loginname,
                                                        @RequestParam(value="name")String name,
                                                        @RequestParam(value="pass1")String pass1,
                                                        @RequestParam(value="pass2")String pass2,
                                                        @RequestParam(value="email")String email,
                                                        @RequestParam(value="phone")String phone) {
    Map rs = new HashMap();
    try{
      rs = customMethodsImpl.themmoiKhachhang(principal,idkh,loginname, name, pass1,pass2,email,phone);
    }catch (DataIntegrityViolationException ex){
      rs.put("code",1);
      rs.put("msg","Tên đăng nhập "+loginname+" đã tồn tại, xin vui lòng chọn tên đăng nhập khác !");
      return rs;
    }
    return rs;
  }

  @RequestMapping(value="/admin/getdetailkhachhang",method = RequestMethod.POST)
  public Khachhang getDetailKhachhang(@RequestParam(value="idkh")int idkh) {
    List kh= customMethodsImpl.getUserDetail(idkh);
    if(kh.size()==0){
      return null;
    }
    return (Khachhang) kh.get(0);
  }

}
