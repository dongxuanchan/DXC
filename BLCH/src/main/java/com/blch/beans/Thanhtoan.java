package com.blch.beans;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="BLCH_THANHTOAN")
public class Thanhtoan implements Serializable {

  @Id
  @Column(  name = "IDKHACHHANG", nullable = false)
  //@ManyToOne
  Long idkhachhang;

  @Id
  @Column(name = "IDCANHO", nullable = false)
  //@ManyToOne
  Long idcanho;

  @Column(name = "SOTIEN")
  double sotien;

  @Id
  @Column(name = "DOTTHANHTOAN")
  int dotthanhtoan;

  @Column(name = "NGAYTHANHTOAN")
  @Temporal(TemporalType.DATE)
  Date ngaythanhtoan;

  @Column(name = "GHICHU")
  String ghichu;



  public double getSotien() {
    return sotien;
  }

  public void setSotien(double sotien) {
    this.sotien = sotien;
  }

  public int getDotthanhtoan() {
    return dotthanhtoan;
  }

  public void setDotthanhtoan(int dotthanhtoan) {
    this.dotthanhtoan = dotthanhtoan;
  }

  public Date getNgaythanhtoan() {
    return ngaythanhtoan;
  }

  public void setNgaythanhtoan(Date ngaythanhtoan) {
    this.ngaythanhtoan = ngaythanhtoan;
  }

  public String getGhichu() {
    return ghichu;
  }

  public void setGhichu(String ghichu) {
    this.ghichu = ghichu;
  }

  public Long getIdkhachhang() {
    return idkhachhang;
  }

  public void setIdkhachhang(Long idkhachhang) {
    this.idkhachhang = idkhachhang;
  }

  public Long getIdcanho() {
    return idcanho;
  }

  public void setIdcanho(Long idcanho) {
    this.idcanho = idcanho;
  }
}