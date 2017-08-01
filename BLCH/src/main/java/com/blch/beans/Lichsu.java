package com.blch.beans;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: cxdong
 * Date: 4/25/17
 * Time: 11:46 AM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="BLCH_LICHSU")
public class Lichsu {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  Long id;

  @Column(name = "USER", nullable = false)
  String user;

  @Column(name = "TYPE", nullable = false)
  String type;

  @Column(name = "TABLE", nullable = false)
  String table;

  @Column(name = "IDS")
  String ids;

  @Column(name = "COLUMNS")
  String columns;

  @Column(name = "NEWALUES")
  String newvalues;

  @Column(name = "DATE", nullable = false)
  Date date;

  @Column(name = "DESC")
  String desc;

  public Lichsu(String user, String type, String table, String ids, String columns, String newvalues, Date date, String desc) {
    this.user = user;
    this.type = type;
    this.table = table;
    this.ids = ids;
    this.columns = columns;
    this.newvalues = newvalues;
    this.date = date;
    this.desc = desc;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTable() {
    return table;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public String getIds() {
    return ids;
  }

  public void setIds(String ids) {
    this.ids = ids;
  }

  public String getColumns() {
    return columns;
  }

  public void setColumns(String columns) {
    this.columns = columns;
  }

  public String getNewvalues() {
    return newvalues;
  }

  public void setNewvalues(String newvalues) {
    this.newvalues = newvalues;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
