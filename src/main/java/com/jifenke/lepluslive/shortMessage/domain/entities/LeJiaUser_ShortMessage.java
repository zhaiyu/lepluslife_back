package com.jifenke.lepluslive.shortMessage.domain.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/17.
 */
@Entity
@Table(name = "LEJIAUSER_SHORTMESSAGE")
public class LeJiaUser_ShortMessage {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Long leJiaUser_id;

  private Long ShortMessage_id;

  private Integer state;//0提交失败 1提交成功

  private String sendStatusCode;//发送状态码

  private Integer sendState;//1成功 0失败 是否发送失败

  private Date receivetime;//用户接受短信的具体时间

  public Date getReceivetime() {
    return receivetime;
  }

  public void setReceivetime(Date receivetime) {
    this.receivetime = receivetime;
  }

  public Integer getSendState() {
    return sendState;
  }

  public void setSendState(Integer sendState) {
    this.sendState = sendState;
  }

  public String getSendStatusCode() {
    return sendStatusCode;
  }

  public void setSendStatusCode(String sendStatusCode) {
    this.sendStatusCode = sendStatusCode;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getLeJiaUser_id() {
    return leJiaUser_id;
  }

  public void setLeJiaUser_id(Long leJiaUser_id) {
    this.leJiaUser_id = leJiaUser_id;
  }

  public Long getShortMessage_id() {
    return ShortMessage_id;
  }

  public void setShortMessage_id(Long shortMessage_id) {
    ShortMessage_id = shortMessage_id;
  }
}
