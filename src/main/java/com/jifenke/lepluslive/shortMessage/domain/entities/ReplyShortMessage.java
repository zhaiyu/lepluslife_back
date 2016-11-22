package com.jifenke.lepluslive.shortMessage.domain.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jfk on 2016/10/21.
 */
@Entity
@Table(name = "REPLYSHORTMESSAGE")
public class ReplyShortMessage {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String phone;

  private String subid;

  private String content;

  private Date receivetime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getSubid() {
    return subid;
  }

  public void setSubid(String subid) {
    this.subid = subid;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getReceivetime() {
    return receivetime;
  }

  public void setReceivetime(Date receivetime) {
    this.receivetime = receivetime;
  }
}
