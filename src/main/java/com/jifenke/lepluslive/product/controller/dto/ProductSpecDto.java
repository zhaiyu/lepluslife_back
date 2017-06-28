package com.jifenke.lepluslive.product.controller.dto;

import com.jifenke.lepluslive.product.domain.entities.Product;

/**
 * 规格编数据传输类
 * Created by zhangwen on 2017/6/26.
 */
public class ProductSpecDto {

  private Integer id;

  private String specDetail;

  private String picture;

  private Integer repository;

  private String price;

  private String costPrice;

  private String otherPrice;

  private Product product;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getSpecDetail() {
    return specDetail;
  }

  public void setSpecDetail(String specDetail) {
    this.specDetail = specDetail;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public Integer getRepository() {
    return repository;
  }

  public void setRepository(Integer repository) {
    this.repository = repository;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getCostPrice() {
    return costPrice;
  }

  public void setCostPrice(String costPrice) {
    this.costPrice = costPrice;
  }

  public String getOtherPrice() {
    return otherPrice;
  }

  public void setOtherPrice(String otherPrice) {
    this.otherPrice = otherPrice;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}
