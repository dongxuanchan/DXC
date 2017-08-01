package com.blch.beans;

import javax.persistence.*;

@Entity
@Table(name="BLCH_QUANTRIVIEN")
public class Quantrivien {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  Long id;

  @Column(name = "TEN")
  String ten;

  @Column(name = "TENDANGNHAP", nullable = false, unique = true)
  String tendangnhap;

  @Column(name = "EMAIL")
  String email;

  @Column(name = "LOAI")
  String loai;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTendangnhap() {
    return tendangnhap;
  }

  public void setTendangnhap(String tendangnhap) {
    this.tendangnhap = tendangnhap;
  }

  public String getTen() {
    return ten;
  }

  public void setTen(String ten) {
    this.ten = ten;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLoai() {
    return loai;
  }

  public void setLoai(String loai) {
    this.loai = loai;
  }
}