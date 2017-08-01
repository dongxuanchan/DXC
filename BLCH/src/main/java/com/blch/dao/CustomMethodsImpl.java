package com.blch.dao;

import com.blch.Util.Constants;
import com.blch.Util.Util;
import com.blch.beans.*;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Repository annotation allows the component scanning support to find and
 * configure the DAO wihtout any XML configuration and also provide the Spring
 * exceptiom translation.
 * Since we've setup setPackagesToScan and transaction manager on
 * DatabaseConfig, any bean method annotated with Transactional will cause
 * Spring to magically call begin() and commit() at the start/end of the
 * method. If exception occurs it will also call rollback().
 */


@Repository
public class CustomMethodsImpl {

  private static final Logger logger = LoggerFactory.getLogger(CustomMethodsImpl.class);


  // ------------------------
  // PRIVATE FIELDS
  // ------------------------

  // An EntityManager will be automatically injected from entityManagerFactory
  // setup on DatabaseConfig class.
  @PersistenceContext
  private EntityManager em;

  @Transactional
  public Map getDsCanhoTrangAdmin(int page,int numb){
    //System.out.println("page: " + page);
    //System.out.println("numb: "+numb);
    logger.info("getDsCanhoTrangAdmin() started");
    //System.out.println("getDsCanhoTrangAdmin() started");

    Map rs = new HashMap();

    String qr = "SELECT DISTINCT kh.id, kh.tenkhachhang, ch " +
                " FROM Canho ch, Khachhang kh " +
                " WHERE EXISTS (SELECT tt FROM Thanhtoan tt WHERE tt.idcanho=ch.id and tt.idkhachhang=kh.id ) ORDER BY kh.id, ch.id ";

    List l = em.createQuery(qr).setFirstResult(numb*(page-1)).setMaxResults(numb).getResultList();

    String countQr =  " SELECT count(*)                                                                                   " +
                      " FROM BLCH_CANHO ch, BLCH_KHACHHANG kh                                                             " +
                      " WHERE EXISTS (SELECT 1 FROM BLCH_THANHTOAN tt WHERE tt.idcanho=ch.id and tt.idkhachhang=kh.id )   ";
    int maxrow = ((BigInteger) em.createNativeQuery(countQr).getSingleResult()).intValue();

    //System.out.println("maxrow: "+maxrow);

    rs.put("currentpage",page);
    rs.put("lastpage",(int) Math.ceil( ((double)maxrow) / numb));
    rs.put("data",l);

    return rs;
  }

  @Transactional
  public List getChiTietThanhToanAdmin(int idkh, int idch){
    String qr = "SELECT DISTINCT kh.tenkhachhang, ch.macanho, tt " +
                " FROM Thanhtoan tt, Canho ch, Khachhang kh " +
                " WHERE tt.idcanho= ch.id and tt.idkhachhang=kh.id and tt.idcanho= ?1 and tt.idkhachhang= ?2 ORDER BY tt.dotthanhtoan ";

    List l = em.createQuery(qr).setParameter(1,Long.valueOf(idch)).setParameter(2, Long.valueOf(idkh)).getResultList();

    return l;
  }

  @Transactional
  public List getCanHoKhachHangUser(int idkh){
    String qr = " SELECT  temp.id, temp.macanho, temp.tenduan, temp.filedinhkem, temp.tinhtrang, temp.bangiao,                          " +
                "         FORMATDATETIME(bg.ngaydangky,'dd/MM/YYYY'), FORMATDATETIME(bg.ngayduyet,'dd/MM/YYYY')                         " +
                " FROM  (SELECT DISTINCT ch.*                                                                                           " +
                "         FROM BLCH_CANHO ch, BLCH_KHACHHANG kh                                                                         " +
                "         WHERE kh.id = ?1                                                                                              " +
                "         AND EXISTS (SELECT 1 FROM BLCH_THANHTOAN tt WHERE tt.idcanho=ch.id and tt.idkhachhang=kh.id )) temp           " +
                " LEFT JOIN BLCH_LICHBANGIAO bg                                                                                         " +
                " ON temp.id=bg.idcanho                                                                                                 " +
                " ORDER BY temp.id                                                                                                      ";

    List<Object[]> l = em.createNativeQuery(qr).setParameter(1,Long.valueOf(idkh)).getResultList();

    return l;
  }

  @Transactional
  public boolean duyetBangiaoCanhoAdmin(Principal principal,int idch) {
    boolean rs = false;
    String qr = "SELECT ch " +
      " FROM Canho ch " +
      " WHERE ch.id= ?1 ";

    Canho ch = (Canho) em.createQuery(qr).setParameter(1,Long.valueOf(idch)).getSingleResult();
    ch.setBangiao(true);
    em.persist(ch);
    Lichsu ls = new Lichsu(principal.getName(),"UPDATE","BLCH_CANHO",String.valueOf(ch.getId()),"BANGIAO","true",new Date(),null);
    em.persist(ls);
    rs=true;

    return rs;
  }

/*  public int kiemtraDangnhap(String user, String pass){
    String qr = "SELECT kh.id " +
      " FROM Khachhang kh " +
      " WHERE kh.tendangnhap = ?1 " +
      " AND kh.matkhau = ?2 ";

    List l = em.createQuery(qr).setParameter(1, user).setParameter(2, Util.enscriptPassMD5(pass)).getResultList();
    if(l.isEmpty())
      return -1;
    return ((Long)l.get(0)).intValue();
  }*/

  @Transactional
  public int  getKhachhangByTendangnhapUser(String username){
    String qr = "SELECT kh.id " +
      " FROM Khachhang kh " +
      " WHERE kh.tendangnhap = ?1 ";

    List l = em.createQuery(qr).setParameter(1, username).getResultList();
    if(l.isEmpty())
      return -1;
    return ((Long)l.get(0)).intValue();
  }

  @Transactional
  public boolean chonNgayBanGiaoUser(Principal principal,int idch, String date) {
    boolean rs = false;
    //xac thuc nguoi dung so huu can ho
    String qr =  " SELECT kh.id " +
      " FROM Canho ch, Khachhang kh " +
      " WHERE ch.id = ?1 " +
      " AND kh.tendangnhap = ?2 " +
      " AND EXISTS (SELECT 1 FROM Thanhtoan tt WHERE tt.idcanho=ch.id and tt.idkhachhang=kh.id ) ";
    List idkhs = em.createQuery(qr)
                                .setParameter(1,Long.valueOf(idch))
                                .setParameter(2,principal.getName()).getResultList();
    if(idkhs.isEmpty()){return false;}
    long idkh= (Long) idkhs.get(0);
    //System.out.println("chonNgayBanGiaoUser idkh: "+idkh);

    qr =  " SELECT DISTINCT bg " +
          " FROM Lichbangiao bg " +
          " WHERE bg.idkhachhang = ?1 AND bg.idcanho = ?2 ";
    List bgList =  em.createQuery(qr).setParameter(1,idkh).setParameter(2,Long.valueOf(idch)).getResultList();

    Lichbangiao bg;
    Lichsu ls;
    if(bgList.isEmpty()){
      bg = new Lichbangiao(idkh,Long.valueOf(idch),Util.parseDate(date),0,null,null);
      ls = new Lichsu(principal.getName(),"INSERT","BLCH_LICHBANGIAO",String.valueOf(idkh)+"/"+String.valueOf(idch)
                      ,"NGAYDANGKY",date, new Date(),null);
    }else{
      bg = (Lichbangiao) bgList.get(0);
      bg.setNgaydangky(Util.parseDate(date));
      bg.setNgayduyet(null);
      bg.setTrangthai(0);
      ls = new Lichsu(principal.getName(),"UPDATE","BLCH_LICHBANGIAO",String.valueOf(bg.getIdkhachhang())+"/"+String.valueOf(bg.getIdcanho())
                      ,"NGAYDANGKY",date, new Date(),null);
    }
    em.persist(bg);
    em.persist(ls);
    rs=true;

    return rs;
  }

  @Transactional
  public Map getDsCanhoBangiaoAdmin(int page,int numb){

    Map rs = new HashMap();

    String qr = "SELECT ch.macanho, ch.tenduan, ch.filedinhkem, kh.tenkhachhang, bg   " +
                " FROM Lichbangiao bg, Canho ch, Khachhang kh                         " +
                " WHERE bg.idcanho = ch.id AND bg.idkhachhang=kh.id                   " +
                " ORDER BY bg.ngayduyet, bg.ngaydangky                                ";

    List l = em.createQuery(qr).setFirstResult(numb*(page-1)).setMaxResults(numb).getResultList();

    String countQr =  " SELECT  count(*) FROM BLCH_LICHBANGIAO ";
    int maxrow = ((BigInteger) em.createNativeQuery(countQr).getSingleResult()).intValue();

    //System.out.println("maxrow: "+maxrow);

    rs.put("currentpage",page);
    rs.put("lastpage",(int) Math.ceil( ((double)maxrow) / numb));
    rs.put("data",l);

    return rs;
  }

  @Transactional
  public boolean duyetNgayBanGiaoAdmin(Principal principal,int idkh, int idch) {
    boolean rs = false;
    String qr =  " SELECT DISTINCT bg " +
      " FROM Lichbangiao bg " +
      " WHERE bg.idkhachhang = ?1 AND bg.idcanho = ?2 ";
    List bgList =  em.createQuery(qr).setParameter(1,Long.valueOf(idkh)).setParameter(2,Long.valueOf(idch)).getResultList();

    Lichbangiao bg;
    Lichsu ls;
    if(bgList.isEmpty()){
      return false;
    }else{
      bg = (Lichbangiao) bgList.get(0);
      bg.setNgayduyet(bg.getNgaydangky());
      //bg.setTrangthai(0);
      ls = new Lichsu(principal.getName(),"UPDATE","BLCH_LICHBANGIAO",String.valueOf(bg.getIdkhachhang())+"/"+String.valueOf(bg.getIdcanho())
        ,"NGAYDUYET",Util.dateFormat.format(bg.getNgaydangky()), new Date(),null);
    }
    em.persist(bg);
    em.persist(ls);
    rs=true;

    return rs;
  }

  @Transactional
  public boolean chonNgayBanGiaoAdmin(Principal principal,int idkh, int idch, String date) {
    boolean rs = false;
    String qr =  " SELECT DISTINCT bg " +
      " FROM Lichbangiao bg " +
      " WHERE bg.idkhachhang = ?1 AND bg.idcanho = ?2 ";
    List bgList =  em.createQuery(qr).setParameter(1,Long.valueOf(idkh)).setParameter(2,Long.valueOf(idch)).getResultList();

    Lichbangiao bg;
    Lichsu ls;
    if(bgList.isEmpty()){
      return false;
    }else{
      bg = (Lichbangiao) bgList.get(0);
      bg.setNgayduyet(Util.parseDate(date));
      //bg.setTrangthai(0);
      ls = new Lichsu(principal.getName(),"UPDATE","BLCH_LICHBANGIAO",String.valueOf(bg.getIdkhachhang())+"/"+String.valueOf(bg.getIdcanho())
        ,"NGAYDUYET",date, new Date(),null);
    }
    em.persist(bg);
    em.persist(ls);
    rs=true;

    return rs;
  }

  @Transactional
  public Map getDsKhachhangAdmin(int page,int numb){

    Map rs = new HashMap();

    String qr = "SELECT kh.id, kh.tendangnhap, kh.tenkhachhang, kh.email, kh.dienthoai, us.enabled  " +
                " FROM BLCH_KHACHHANG kh                                                            " +
                " LEFT JOIN BLCH_USERS us                                                           " +
                " ON kh.tendangnhap = us.username                                                   ";

    List l = em.createNativeQuery(qr).setFirstResult(numb*(page-1)).setMaxResults(numb).getResultList();

    String countQr =  "SELECT count(*)             " +
                      " FROM BLCH_KHACHHANG kh     ";
    int maxrow = ((BigInteger) em.createNativeQuery(countQr).getSingleResult()).intValue();

    //System.out.println("maxrow: "+maxrow);

    rs.put("currentpage",page);
    rs.put("lastpage",(int) Math.ceil( ((double)maxrow) / numb));
    rs.put("data",l);

    return rs;
  }

  @Transactional
  public List getUserDetail(int idkh){
    String qr = " SELECT kh                           " +
                "  FROM Khachhang kh                  " +
                " WHERE kh.id = ?1                    ";

    List l = em.createQuery(qr).setParameter(1,Long.valueOf(idkh)).getResultList();

    return l;
  }

  @Transactional
  public Map capnhatKhachhang(Principal principal,int idkh, String name, String pass1, String pass2, String email, String phone) {
    Map rs = new HashMap();
    //validate input
    if(!validateInputCapnhatKhachang(name,pass1,pass2,email,phone)){
      rs.put("code",1);
      rs.put("msg","Dữ liệu nhập không hợp lệ !");
      return rs;
    }

    String qr = "SELECT kh            " +
                " FROM Khachhang kh   " +
                " WHERE kh.id= ?1     ";

    List l = em.createQuery(qr).setParameter(1,Long.valueOf(idkh)).getResultList();
    if(l.isEmpty()){
      rs.put("code",1);
      rs.put("msg","Không tìm thấy khách hàng !");
      return rs;
    }
    Khachhang kh = (Khachhang) l.get(0);
    kh.setTenkhachhang(name);
    kh.setEmail(email);
    kh.setDienthoai(phone);
    em.persist(kh);
    Lichsu ls1 = new Lichsu(principal.getName(),"UPDATE","BLCH_KHACHHANG",String.valueOf(kh.getId()),"TENKHACHHANG/EMAIL/DIENTHOAI",name+"/"+email+"/"+phone,new Date(),null);
    em.persist(ls1);

    if(pass1!=null && !pass1.equalsIgnoreCase("") ){
      qr =  "  SELECT us                        " +
            " FROM Users us, Khachhang kh       " +
            " WHERE kh.id= ?1                   " +
            " AND kh.tendangnhap=us.username    ";
      Users us = (Users) em.createQuery(qr).setParameter(1,Long.valueOf(idkh)).getSingleResult();
      us.setPassword(Util.enscriptBCrypt(pass1));
      em.persist(us);
      Lichsu ls2 = new Lichsu(principal.getName(),"UPDATE","BLCH_USERS",us.getUsername(),"PASSWORD","XXX",new Date(),null);
      em.persist(ls2);
    }

    rs.put("code",0);
    return rs;
  }


  private boolean validateInputCapnhatKhachang(String name, String pass1, String pass2, String email, String phone){
    if(name==null || email==null)
      return false;
    if(!name.matches(Constants.NAME_REGEX))
      return false;
    if(pass1!=null && !pass1.equalsIgnoreCase("") ){
      if(!pass1.matches(Constants.PASSWORD_REGEX))
        return false;
      if(!pass1.equalsIgnoreCase(pass2))
        return false;
    }
    if(!email.matches(Constants.EMAIL_REGEX))
      return false;
    if(phone!=null && !phone.equalsIgnoreCase("") && !phone.matches(Constants.PHONE_REGEX))
      return false;
    return true;
  }

  private boolean validateInpuThemmoiKhachang(String loginname, String name, String pass1, String pass2, String email, String phone){
    if(loginname==null || name==null || pass1==null || pass2==null || email==null)
      return false;
    if(!loginname.matches(Constants.USERNAME_REGEX))
      return false;
    if(!name.matches(Constants.NAME_REGEX))
      return false;
    if(!pass1.matches(Constants.PASSWORD_REGEX))
      return false;
    if(!pass1.equalsIgnoreCase(pass2))
      return false;
    if(!email.matches(Constants.EMAIL_REGEX))
      return false;
    if(phone!=null && !phone.equalsIgnoreCase("") && !phone.matches(Constants.PHONE_REGEX))
      return false;
    return true;
  }

  @Transactional
  public boolean khoamoTaikhoanKhachhang(Principal principal,boolean type, String user) {
    String qr = " SELECT us               " +
                " FROM Users us           " +
                " WHERE us.username= ?1   ";

    List l = em.createQuery(qr).setParameter(1,user).getResultList();
    if(l.isEmpty()){
      return false;
    }
    Users us = (Users) l.get(0);
    us.setEnabled(type);
    em.persist(us);
    Lichsu ls = new Lichsu(principal.getName(),"UPDATE","BLCH_USERS",us.getUsername(),"ENABLED",String.valueOf(type),new Date(),null);
    em.persist(ls);

    return true;
  }

  @Transactional
  public Map themmoiKhachhang(Principal principal,int idkh, String loginname, String name, String pass1, String pass2, String email, String phone){
    //validate input
    Map rs= new HashMap();
    if(!validateInpuThemmoiKhachang(loginname, name, pass1, pass2, email, phone)){
      rs.put("code",1);
      rs.put("msg","Dữ liệu nhập không hợp lệ !");
      return rs;
    }

    String qr = "SELECT kh            " +
                " FROM Khachhang kh   " +
                " WHERE kh.id= ?1     ";

    List l = em.createQuery(qr).setParameter(1,Long.valueOf(idkh)).getResultList();
    if(l.isEmpty()){
      rs.put("code",1);
      rs.put("msg","Không tìm thấy khách hàng !");
      return rs;
    }
    Khachhang kh = (Khachhang) l.get(0);

    Users us = new Users();
    us.setUsername(loginname);
    us.setEnabled(true);
    us.setPassword(Util.enscriptBCrypt(pass1));
    em.persist(us);
    Lichsu ls1 = new Lichsu(principal.getName(),"INSERT","BLCH_USERS",us.getUsername(),null,null,new Date(),null);
    em.persist(ls1);

    Roles rl = new Roles();
    rl.setUsername(loginname);
    rl.setRole(Constants.USER_ROLE);
    em.persist(rl);
    Lichsu ls2 = new Lichsu(principal.getName(),"INSERT","BLCH_USER_ROLES",null,"USERNAME/ROLE",loginname+"/"+Constants.USER_ROLE,new Date(),null);
    em.persist(ls2);

    kh.setTenkhachhang(name);
    kh.setEmail(email);
    kh.setDienthoai(phone);
    kh.setTendangnhap(loginname);
    em.persist(kh);
    Lichsu ls3 = new Lichsu(principal.getName(),"UPDATE","BLCH_KHACHHANG",String.valueOf(kh.getId()),"TENDANGNHAP/TENKHACHHANG/EMAIL/DIENTHOAI",loginname+"/"+name+"/"+email+"/"+phone,new Date(),null);
    em.persist(ls3);

    /*Khachhang kh = new Khachhang();
    kh.setTendangnhap(loginname);
    kh.setTenkhachhang(name);
    kh.setEmail(email);
    kh.setDienthoai(phone);
    em.persist(kh);
    Lichsu ls3 = new Lichsu(principal.getName(),"INSERT","BLCH_KHACHHANG",null,"TENDANGNHAP/TENKHACHHANG/EMAIL/DIENTHOAI",loginname+"/"+name+"/"+email+"/"+phone,new Date(),null);
    em.persist(ls3);*/

    rs.put("code",0);
    return rs;
  }

  public String uloadExcelFile(MultipartFile file, ModelMap model){
    if (file.isEmpty()) {
      model.addAttribute("code", 1);
      model.addAttribute("msg", "Xin vui lòng chọn File để Upload !");
      return null;
    }
    try{
      byte[] bytes = file.getBytes();
      String  oriname = file.getOriginalFilename();
      String typefile = oriname.substring(oriname.lastIndexOf("."));
      if(!typefile.equalsIgnoreCase(".xls") && !typefile.equalsIgnoreCase(".xlsx")){
        model.addAttribute("code", 1);
        model.addAttribute("msg", "Chỉ upload file Excel !");
        return null;
      }

      String filepath = Constants.UPLOADED_EXCEL_FOLDER + Util.dateFileFormat.format(new Date())+typefile;
      Path path = Paths.get(filepath);
      Files.write(path, bytes);

      model.addAttribute("code", 0);
      model.addAttribute("msg", "Bạn đã upload File '" + oriname + "' thành công !");
      return filepath;
    } catch (IOException e) {
      e.printStackTrace();
      model.addAttribute("code", 1);
      model.addAttribute("msg", e.getMessage()+": "+e.getCause());
      return null;
    }
  }

  @Transactional
  public void importExcelFile(String file, ModelMap model){
    Workbook workbook = null;
    try{
      System.out.println("importExcelFile: " + file);
      workbook = Workbook.getWorkbook(new File(file));

      Sheet canhosheet = workbook.getSheet(0);
      Sheet khachhang = workbook.getSheet(1);
      System.out.println("So dong: "+canhosheet.getRows());
      Cell cell = null;
      String cellcontent = null;
      Long idch=null;
      Long idkh=null;
      String tenduan = "";
      String macanho  = "";
      String tenkhachang = "";
      boolean bangiao;
      String email = "";
      String dienthoai = "";
      String diengiai="";
      int dotthanhtoan;
      double sotien;
      int chcount=0;
      int khcount=0;
      for (int i=1; i<canhosheet.getRows();i++){
        tenduan = canhosheet.getCell(0,i).getContents().trim().equalsIgnoreCase("") ? tenduan:canhosheet.getCell(0,i).getContents().trim();
        macanho = canhosheet.getCell(1,i).getContents().trim();
        tenkhachang = canhosheet.getCell(2,i).getContents().trim();
        bangiao = canhosheet.getCell(3,i).getContents().trim().equalsIgnoreCase("1") ? true:false;
        diengiai = canhosheet.getCell(4,i).getContents().trim();
        dotthanhtoan = Integer.parseInt(canhosheet.getCell(5,i).getContents().trim());
        sotien = Double.parseDouble(canhosheet.getCell(6,i).getContents().trim());

        if(!macanho.equalsIgnoreCase("")){
          //insert new canho
          idch = importCanho(tenduan,macanho,bangiao);
          chcount++;
        }

        if(!tenkhachang.equalsIgnoreCase("")){
          int rowid = khachhang.findCell(tenkhachang).getRow();
          email = khachhang.getCell(1,rowid).getContents().trim();
          email = email.equalsIgnoreCase("") ? null:email;
          dienthoai = khachhang.getCell(2,rowid).getContents().trim();
          dienthoai = dienthoai.equalsIgnoreCase("") ? null:dienthoai;
          //insert new khachhang
          idkh = importKhachhang(tenkhachang,email,dienthoai);
          khcount++;
        }
        //insert new thanhtoan
        importThanhtoan(idch,idkh,dotthanhtoan,sotien,diengiai);
      }
      model.addAttribute("code", 0);
      model.addAttribute("msg", "Thêm mới thành công "+chcount+" căn hộ, "+khcount+" khách hàng!");
    } catch (IOException e) {
      e.printStackTrace();
      model.addAttribute("code", 1);
      model.addAttribute("msg", e.getMessage()+": "+e.getCause());
    } catch (BiffException e) {
      e.printStackTrace();
      model.addAttribute("code", 1);
      model.addAttribute("msg", e.getMessage()+": "+e.getCause());
    }finally {
      if (workbook != null) {
        workbook.close();
      }
    }
  }

  @Transactional
  private Long importCanho(String tenduan, String macanho, boolean bangiao){
    Canho ch = new Canho();
    ch.setMacanho(macanho);
    ch.setTenduan(tenduan);
    ch.setBangiao(bangiao);
    em.persist(ch);
    return ch.getId();
  }

  @Transactional
  private Long importKhachhang(String tenkhachhang, String email, String dienthoai){
    Khachhang kh = new Khachhang();
    kh.setTenkhachhang(tenkhachhang);
    kh.setEmail(email);
    kh.setDienthoai(dienthoai);
    em.persist(kh);
    return kh.getId();
  }

  @Transactional
  private void importThanhtoan(Long idch, Long idkh, int dotthanhtoan, double sotien, String ghichu){
    Thanhtoan tt = new Thanhtoan();
    tt.setIdcanho(idch);
    tt.setIdkhachhang(idkh);
    tt.setDotthanhtoan(dotthanhtoan);
    tt.setSotien(sotien);
    tt.setGhichu(ghichu);
    em.persist(tt);
  }

}
