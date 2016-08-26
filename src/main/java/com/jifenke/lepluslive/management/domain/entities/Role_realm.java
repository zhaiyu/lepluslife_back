package com.jifenke.lepluslive.management.domain.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by lss on 16/7/25.
 */
@Entity
@Table(name = "ROLE_REALM")
public class Role_realm {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String rolePercode;

  private Long roleID;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRolePercode() {
    return rolePercode;
  }

  public void setRolePercode(String rolePercode) {
    this.rolePercode = rolePercode;
  }

  public Long getRoleID() {
    return roleID;
  }

  public void setRoleID(Long roleID) {
    this.roleID = roleID;
  }
}
