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
 * 商品秒杀 信息
 * Created by tqy on 2016/12/30.
 */
@Entity
@Table(name = "PRODUCT_SEC_KILL")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductSecKill {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private Integer sid = 1;              //序号
  private Integer state = 1;            //1=上架|0=已下架

  @ManyToOne
  private Product product;

  private Long  convertPrice;           //兑换所需价格
  private Integer convertScore = 0;     //兑换所需积分
  private Long  initNumber;             //初始销量
  private Long  userLimit;              //个人兑换限制（单个用户本时段最多可购买商品数量）
  private Integer isUserLimit = 0;      //0无限制 1有限制

  @NotNull
  @Column(length = 40)
  private String secKillDate;           //秒杀日期(时段名称, 格式: 2016-12-30)
  private Date startTime;               //开始时间
  private Date endTime;                 //结束时间
  private Date createTime = new Date(); //创建时间
  private Date updateTime;              //最后修改时间


  private Integer isLinkProduct = 0;    //有无关联商品 0无 1有
  @ManyToOne
  private Product LinkProduct;          //关联商品

  @Column(length = 200)
  private String note;                  //备注

//  @Version
//  private Long version = 0L;



  public Long getConvertPrice() {
    return convertPrice;
  }

  public void setConvertPrice(Long convertPrice) {
    this.convertPrice = convertPrice;
  }

  public Integer getConvertScore() {
    return convertScore;
  }

  public void setConvertScore(Integer convertScore) {
    this.convertScore = convertScore;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Long getInitNumber() {
    return initNumber;
  }

  public void setInitNumber(Long initNumber) {
    this.initNumber = initNumber;
  }

  public Integer getIsLinkProduct() {
    return isLinkProduct;
  }

  public void setIsLinkProduct(Integer isLinkProduct) {
    this.isLinkProduct = isLinkProduct;
  }

  public Integer getIsUserLimit() {
    return isUserLimit;
  }

  public void setIsUserLimit(Integer isUserLimit) {
    this.isUserLimit = isUserLimit;
  }

  public Product getLinkProduct() {
    return LinkProduct;
  }

  public void setLinkProduct(Product linkProduct) {
    LinkProduct = linkProduct;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public String getSecKillDate() {
    return secKillDate;
  }

  public void setSecKillDate(String secKillDate) {
    this.secKillDate = secKillDate;
  }

  public Integer getSid() {
    return sid;
  }

  public void setSid(Integer sid) {
    this.sid = sid;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Long getUserLimit() {
    return userLimit;
  }

  public void setUserLimit(Long userLimit) {
    this.userLimit = userLimit;
  }

//  public Long getVersion() {
//    return version;
//  }
//
//  public void setVersion(Long version) {
//    this.version = version;
//  }

}
