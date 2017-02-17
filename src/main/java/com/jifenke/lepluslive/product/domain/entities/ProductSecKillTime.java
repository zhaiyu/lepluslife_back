package com.jifenke.lepluslive.product.domain.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 * 秒杀时段
 * Created by tqy on 2016/12/30.
 */
@Entity
@Table(name = "PRODUCT_SEC_KILL_TIME")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductSecKillTime {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private Integer sid = 1;              //序号
  private Integer timeLimitNumber;      //时段限购(单个用户本时段最多可购买商品数量)

  @NotNull
  @Column(length = 40)
  private String secKillDateName;       //时段名称(显示用的 秒杀日期)
  @NotNull
  @Column(length = 40)
  private String secKillDate;           //秒杀日期(格式: 2016-12-30)

  private String startTime;               //开始时间
  private String endTime;                 //结束时间
  private Date createTime;              //创建时间
  private Date updateTime;              //最后修改时间

  @Column(length = 200)
  private String note;                  //备注

//  @Version
//  private Long version = 0L;



  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public String getSecKillDate() {
    return secKillDate;
  }

  public void setSecKillDate(String secKillDate) {
    this.secKillDate = secKillDate;
  }

  public String getSecKillDateName() {
    return secKillDateName;
  }

  public void setSecKillDateName(String secKillDateName) {
    this.secKillDateName = secKillDateName;
  }

  public Integer getSid() {
    return sid;
  }

  public void setSid(Integer sid) {
    this.sid = sid;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public Integer getTimeLimitNumber() {
    return timeLimitNumber;
  }

  public void setTimeLimitNumber(Integer timeLimitNumber) {
    this.timeLimitNumber = timeLimitNumber;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

//  public Long getVersion() {
//    return version;
//  }
//
//  public void setVersion(Long version) {
//    this.version = version;
//  }

}
