package com.blch.beans;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="BLCH_KHACHHANG")
public class Khachhang {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  Long id;

  @Column(name = "TENKHACHHANG")
  String tenkhachhang;

  @Column(name = "TENDANGNHAP", unique = true)
  String tendangnhap;

  @Column(name = "EMAIL")
  String email;

  @Column(name = "DIENTHOAI")
  String dienthoai;

  @Column(name = "NGAYKICHHOAT")
  Date ngaykichhoat;

  /*@OneToMany(mappedBy="khachhang")
  private Collection<Thanhtoan> thanhtoans = new ArrayList<Thanhtoan>();*/

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTenkhachhang() {
    return tenkhachhang;
  }

  public void setTenkhachhang(String tenkhachhang) {
    this.tenkhachhang = tenkhachhang;
  }

  public String getTendangnhap() {
    return tendangnhap;
  }

  public void setTendangnhap(String tendangnhap) {
    this.tendangnhap = tendangnhap;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDienthoai() {
    return dienthoai;
  }

  public void setDienthoai(String dienthoai) {
    this.dienthoai = dienthoai;
  }

  public Date getNgaykichhoat() {
    return ngaykichhoat;
  }

  public void setNgaykichhoat(Date ngaykichhoat) {
    this.ngaykichhoat = ngaykichhoat;
  }

/*  public Collection<Thanhtoan> getThanhtoans() {
    return thanhtoans;
  }

  public void setThanhtoans(Collection<Thanhtoan> thanhtoans) {
    this.thanhtoans = thanhtoans;
  }*/
}