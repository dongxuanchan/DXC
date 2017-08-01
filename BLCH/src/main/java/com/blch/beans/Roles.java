package com.blch.beans;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: cxdong
 * Date: 4/25/17
 * Time: 11:53 AM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="BLCH_USER_ROLES", uniqueConstraints=@UniqueConstraint(columnNames={"USERNAME", "ROLE"}))
public class Roles {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  Long id;

  @Column(name = "USERNAME", nullable = false)
  String username ;

  @Column(name = "ROLE", nullable = false)
  String role;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
