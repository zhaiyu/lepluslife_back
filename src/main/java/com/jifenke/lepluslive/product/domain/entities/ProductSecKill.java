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
import javax.validation.constraints.NotNull;

/**
 * 秒杀概览
 * Created by tqy on 2016/12/30.
 */
@Entity
@Table(name = "PRODUCT_SEC_KILL")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductSecKill {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne
  private ProductSecKillTime productSecKillTime;  //---秒杀时段
  @ManyToOne
  private Product product;              //------新建秒杀商品 商品类型type=3

//  private Long  initNumber;             //------初始销量(展示用)
//  private Long  convertPrice;           //兑换所需价格
//  private Integer convertScore = 0;     //兑换所需积分
//  private Long  userLimit;              //个人兑换限制（单个用户本时段最多可购买商品数量）
//  private Integer isUserLimit = 0;      //0无限制 1有限制

  private Date createTime;              //创建时间
  private Date updateTime;              //最后修改时间


  private Integer isLinkProduct = 0;    //有无关联商品 0无 1有
  @ManyToOne
  private Product linkProduct;          //关联商品

  @Column(length = 200)
  private String note;                  //备注



  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getIsLinkProduct() {
    return isLinkProduct;
  }

  public void setIsLinkProduct(Integer isLinkProduct) {
    this.isLinkProduct = isLinkProduct;
  }

  public Product getLinkProduct() {
    return linkProduct;
  }

  public void setLinkProduct(Product linkProduct) {
    this.linkProduct = linkProduct;
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

  public ProductSecKillTime getProductSecKillTime() {
    return productSecKillTime;
  }

  public void setProductSecKillTime(ProductSecKillTime productSecKillTime) {
    this.productSecKillTime = productSecKillTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

}
