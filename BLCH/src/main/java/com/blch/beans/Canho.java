package com.blch.beans;

import javax.persistence.*;

@Entity
@Table(name="BLCH_CANHO")
public class Canho {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  Long id;

  @Column(name = "MACANHO", nullable = false)
  String macanho;

  @Column(name = "TENDUAN")
  String tenduan;

  @Column(name = "FILEDINHKEM")
  String filedinhkem;

  @Column(name = "TINHTRANG")
  int tinhtrang;

  @Column(name = "BANGIAO")
  boolean bangiao;

/*  @OneToMany(mappedBy = "canho")
  private Collection<Thanhtoan> thanhtoans = new ArrayList<Thanhtoan>();*/

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMacanho() {
    return macanho;
  }

  public void setMacanho(String macanho) {
    this.macanho = macanho;
  }

  public String getTenduan() {
    return tenduan;
  }

  public void setTenduan(String tenduan) {
    this.tenduan = tenduan;
  }

  public String getFiledinhkem() {
    return filedinhkem;
  }

  public void setFiledinhkem(String filedinhkem) {
    this.filedinhkem = filedinhkem;
  }

  public int getTinhtrang() {
    return tinhtrang;
  }

  public void setTinhtrang(int tinhtrang) {
    this.tinhtrang = tinhtrang;
  }

  public boolean isBangiao() {
    return bangiao;
  }

  public void setBangiao(boolean bangiao) {
    this.bangiao = bangiao;
  }

/*  public Collection<Thanhtoan> getThanhtoans() {
    return thanhtoans;
  }

  public void setThanhtoans(Collection<Thanhtoan> thanhtoans) {
    this.thanhtoans = thanhtoans;
  }*/
}