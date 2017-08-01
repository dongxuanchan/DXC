package com.blch.beans;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: cxdong
 * Date: 4/25/17
 * Time: 11:46 AM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="BLCH_USERS")
public class Users {
  @Id
  @Column(name = "USERNAME", nullable = false)
  String username ;

  @Column(name = "PASSWORD", nullable = false)
  String password;

  @Column(name = "ENABLED", columnDefinition = "boolean default false", nullable = false)
  boolean enabled;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
