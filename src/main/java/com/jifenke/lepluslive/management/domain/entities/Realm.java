package com.jifenke.lepluslive.management.domain.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by lss on 2016/9/1.
 */
@Entity
@Table(name = "REALM")
public class Realm {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String  name;

  private String rolePercode;

  public String getRolePercode() {
    return rolePercode;
  }

  public void setRolePercode(String rolePercode) {
    this.rolePercode = rolePercode;
  }

  public String getName() {

    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {

    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
