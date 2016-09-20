package com.jifenke.lepluslive.product.domain.entities;

/**
 * Created by wcg on 16/4/13.
 */
public class ProductCriteria {

  private Integer state;

  private Integer offset;

  private Integer type;

  private ProductType productType;

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public ProductType getProductType() {
    return productType;
  }

  public void setProductType(ProductType productType) {
    this.productType = productType;
  }

  public ProductCriteria(Integer state,
                         ProductType productType) {
    this.state = state;
    this.productType = productType;
  }

  public ProductCriteria(Integer state) {
    this.state = state;
  }

  public ProductCriteria() {
  }
}
