package com.jifenke.lepluslive.product.domain.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by wcg on 16/3/16.
 */
@Entity
@Table(name = "PRODUCT_SPEC")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProductSpec {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne
  private Product product;

  @NotNull
  private Integer repository;

  private String specDetail;

  @NotNull
  private String picture;

  @NotNull
  private Long price;

  @Column(name = "min_price")
  @NotNull
  private Long minPrice;

  private Integer state;

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public String getSpecDetail() {
    return specDetail;
  }

  public void setSpecDetail(String specDetail) {
    this.specDetail = specDetail;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public Long getPrice() {
    return price;
  }

  public void setPrice(Long price) {
    this.price = price;
  }

  public Long getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(Long minPrice) {
    this.minPrice = minPrice;
  }

  public Integer getRepository() {
    return repository;
  }

  public void setRepository(Integer repository) {
    this.repository = repository;
  }
}
