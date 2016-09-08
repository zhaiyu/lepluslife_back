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

  private Long realmId;

  private Long roleId;

  public Long getRoleID() {
    return roleId;
  }

  public void setRoleID(Long roleID) {
    this.roleId = roleID;
  }

  public Long getRealmId() {

    return realmId;
  }

  public void setRealmId(Long realmId) {
    this.realmId = realmId;
  }

  public Long getId() {

    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
