package com.jifenke.lepluslive.product.controller.dto;

import com.jifenke.lepluslive.product.domain.entities.ProductSpec;
import com.jifenke.lepluslive.product.domain.entities.ProductType;

import java.util.List;

/**
 * Created by wcg on 16/4/5.
 */
public class ProductDto {

  private Long id;

  private Long sid;

  private String name;

  private String picture;

  private String price;

  private String minPrice;


  private Integer saleNumber;

  private Long pointsCount;

  private Long packetCount;

  private String description;

  private Integer customSale;

  private Integer state;

  private String thumb;//缩略图,显示在订单里


  private ProductType productType;

  private List<ProductSpec> productSpecs;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getSid() {
    return sid;
  }

  public void setSid(Long sid) {
    this.sid = sid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(String minPrice) {
    this.minPrice = minPrice;
  }

  public Integer getCustomSale() {
    return customSale;
  }

  public void setCustomSale(Integer customSale) {
    this.customSale = customSale;
  }

  public Integer getSaleNumber() {
    return saleNumber;
  }

  public void setSaleNumber(Integer saleNumber) {
    this.saleNumber = saleNumber;
  }

  public Long getPointsCount() {
    return pointsCount;
  }

  public void setPointsCount(Long pointsCount) {
    this.pointsCount = pointsCount;
  }

  public Long getPacketCount() {
    return packetCount;
  }

  public void setPacketCount(Long packetCount) {
    this.packetCount = packetCount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public String getThumb() {
    return thumb;
  }

  public void setThumb(String thumb) {
    this.thumb = thumb;
  }

  public ProductType getProductType() {
    return productType;
  }

  public void setProductType(ProductType productType) {
    this.productType = productType;
  }

  public List<ProductSpec> getProductSpecs() {
    return productSpecs;
  }

  public void setProductSpecs(List<ProductSpec> productSpecs) {
    this.productSpecs = productSpecs;
  }
}
