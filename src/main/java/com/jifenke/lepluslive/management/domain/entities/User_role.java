package com.jifenke.lepluslive.management.domain.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by lss on 16/7/25.
 */
@Entity
@Table(name = "USER_ROLE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User_role {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Long userID;

  private Long roleID;

  public Long getRoleID() {
    return roleID;
  }

  public void setRoleID(Long roleID) {
    this.roleID = roleID;
  }

  public Long getUserID() {

    return userID;
  }

  public void setUserID(Long userID) {
    this.userID = userID;
  }

  public Long getId() {

    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
