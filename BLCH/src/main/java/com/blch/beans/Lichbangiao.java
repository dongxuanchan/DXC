package com.blch.beans;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="BLCH_LICHBANGIAO")
public class Lichbangiao implements Serializable {

  @Id
  @Column(  name = "IDKHACHHANG", nullable = false)
  Long idkhachhang;

  @Id
  @Column(name = "IDCANHO", nullable = false)
  Long idcanho;

  @Column(name = "NGAYDANGKY")
  Date ngaydangky;

  @Column(name = "TRANGTHAI")
  int trangthai;

  @Column(name = "IDQUANTRI")
  Long idquantri;

  @Column(name = "NGAYDUYET")
  Date ngayduyet;

  public Lichbangiao(Long idkhachhang, Long idcanho, Date ngaydangky, int trangthai, Long idquantri, Date ngayduyet) {
    this.idkhachhang = idkhachhang;
    this.idcanho = idcanho;
    this.ngaydangky = ngaydangky;
    this.trangthai = trangthai;
    this.idquantri = idquantri;
    this.ngayduyet = ngayduyet;
  }

  public Lichbangiao() {
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

  public Date getNgaydangky() {
    return ngaydangky;
  }

  public void setNgaydangky(Date ngaydangky) {
    this.ngaydangky = ngaydangky;
  }

  public int getTrangthai() {
    return trangthai;
  }

  public void setTrangthai(int trangthai) {
    this.trangthai = trangthai;
  }

  public Long getIdquantri() {
    return idquantri;
  }

  public void setIdquantri(Long idquantri) {
    this.idquantri = idquantri;
  }

  public Date getNgayduyet() {
    return ngayduyet;
  }

  public void setNgayduyet(Date ngayduyet) {
    this.ngayduyet = ngayduyet;
  }
}