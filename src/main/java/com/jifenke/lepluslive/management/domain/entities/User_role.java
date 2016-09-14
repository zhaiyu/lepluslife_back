package com.jifenke.lepluslive.management.domain.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by lss on 16/7/25.
 */
@Entity
@Table(name = "USER_ROLE")
public class User_role {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Long userId;

  private Long roleId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getRoleId() {

    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public Long getId() {

    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
