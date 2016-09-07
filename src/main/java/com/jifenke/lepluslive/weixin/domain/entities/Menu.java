package com.jifenke.lepluslive.weixin.domain.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by zhangwen on 2016/5/25.
 */
@Entity
@Table(name = "MENU")
public class Menu {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Long displayOrder;
  private String name;
  private String triggerKeyword;
  private String triggerUrl;
  private Integer isDisabled = 0;

  @OneToOne(fetch = FetchType.EAGER)
  private Menu parentMenu;

  @OneToMany
  private List<Menu> childrenMenus;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getDisplayOrder() {
    return displayOrder;
  }

  public void setDisplayOrder(Long displayOrder) {
    this.displayOrder = displayOrder;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTriggerKeyword() {
    return triggerKeyword;
  }

  public void setTriggerKeyword(String triggerKeyword) {
    this.triggerKeyword = triggerKeyword;
  }

  public String getTriggerUrl() {
    return triggerUrl;
  }

  public void setTriggerUrl(String triggerUrl) {
    this.triggerUrl = triggerUrl;
  }

  public Integer getIsDisabled() {
    return isDisabled;
  }

  public void setIsDisabled(Integer isDisabled) {
    this.isDisabled = isDisabled;
  }

  public Menu getParentMenu() {
    return parentMenu;
  }

  public void setParentMenu(Menu parentMenu) {
    this.parentMenu = parentMenu;
  }

  public List<Menu> getChildrenMenus() {
    return childrenMenus;
  }

  public void setChildrenMenus(List<Menu> childrenMenus) {
    this.childrenMenus = childrenMenus;
  }
}
