package com.jifenke.lepluslive.shortMessage.domain.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/17.
 */
@Entity
@Table(name = "SHORT_MESSAGE")
public class ShortMessage {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String reqid; //短信批次号

  private String content;//短信内容

  @ManyToOne
  private ShortMessageScene shortMessageScene;

  private Date sendDate;

  private String reqCode;//返回值

  private Integer userAmount;

  private String reqMsg;//返回信息


  public Integer getUserAmount() {
    return userAmount;
  }

  public void setUserAmount(Integer userAmount) {
    this.userAmount = userAmount;
  }

  public String getReqCode() {
    return reqCode;
  }

  public void setReqCode(String reqCode) {
    this.reqCode = reqCode;
  }

  public String getReqMsg() {
    return reqMsg;
  }

  public void setReqMsg(String reqMsg) {
    this.reqMsg = reqMsg;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getReqid() {
    return reqid;
  }

  public void setReqid(String reqid) {
    this.reqid = reqid;
  }

  public ShortMessageScene getShortMessageScene() {
    return shortMessageScene;
  }

  public void setShortMessageScene(ShortMessageScene shortMessageScene) {
    this.shortMessageScene = shortMessageScene;
  }

  public Date getSendDate() {
    return sendDate;
  }

  public void setSendDate(Date sendDate) {
    this.sendDate = sendDate;
  }


}
