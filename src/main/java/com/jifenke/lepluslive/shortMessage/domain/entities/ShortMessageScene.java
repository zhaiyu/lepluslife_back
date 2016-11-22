package com.jifenke.lepluslive.shortMessage.domain.entities;

import com.jifenke.lepluslive.global.util.MvUtil;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/10/17.
 */
@Entity
@Table(name = "SHORT_MESSAGE_SCENE")
public class ShortMessageScene {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;//0手动发送  1注册时发送 2成功邀请好友时发送 3商品支付时发送 商城订单返红包 4商品支付时发送 商城订单未返红包 5线下消费时发送

  private boolean recloser;  //自动开关 0 关 1开

  private boolean unsubscribeRecloser;//退订用户是否继续发送 1继续发送 0不继续发送

  private String content;

  private String category;//展示用的发送场景

  private Integer parameterKind;//参数分类 1是传会员sid时 2是传线上订单sid时 3是传商城订单sid时

  private String sceneSid = MvUtil.getShortMessageSceneNumber();

  private Long addedcondition1;//额外条件1 对于线下订单叫 使用超过多少钱

  private Long addedcondition2;//额外条件2 对于线下订单叫 使用红包超过多少钱

  public boolean isUnsubscribeRecloser() {
    return unsubscribeRecloser;
  }

  public void setUnsubscribeRecloser(boolean unsubscribeRecloser) {
    this.unsubscribeRecloser = unsubscribeRecloser;
  }

  public Long getAddedcondition1() {
    return addedcondition1;
  }

  public void setAddedcondition1(Long addedcondition1) {
    this.addedcondition1 = addedcondition1;
  }

  public Long getAddedcondition2() {
    return addedcondition2;
  }

  public void setAddedcondition2(Long addedcondition2) {
    this.addedcondition2 = addedcondition2;
  }

  public String getSceneSid() {
    return sceneSid;
  }

  public void setSceneSid(String sceneSid) {
    this.sceneSid = sceneSid;
  }

  public Integer getParameterKind() {
    return parameterKind;
  }

  public void setParameterKind(Integer parameterKind) {
    this.parameterKind = parameterKind;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isRecloser() {
    return recloser;
  }

  public void setRecloser(boolean recloser) {
    this.recloser = recloser;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}