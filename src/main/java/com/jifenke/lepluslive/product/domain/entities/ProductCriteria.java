package com.jifenke.lepluslive.product.domain.entities;

/**
 * Created by wcg on 16/4/13.
 */
public class ProductCriteria {

  private Integer state;

  private String name;

  private Integer offset;

  private String startDate;

  private String endDate;

  private Integer minTruePrice;  //实付最低价格

  private Integer maxTruePrice;   //实付最高价格

  private Integer type;

  private ProductType productType;

  private Integer mark;  //角标

  private Integer postage;  //是否包邮 0=是

  private Integer orderBy; //排序条件（1>创建时间|2>商品序号|3>实际价格|4>可用积分|5>实际销量|6>最后修改时间）

  private Integer desc; //升序还是降序 1=降序|2=升序

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStartDate() {
    return startDate;
  }

  public Integer getMark() {
    return mark;
  }

  public void setMark(Integer mark) {
    this.mark = mark;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public Integer getMinTruePrice() {
    return minTruePrice;
  }

  public void setMinTruePrice(Integer minTruePrice) {
    this.minTruePrice = minTruePrice;
  }

  public Integer getMaxTruePrice() {
    return maxTruePrice;
  }

  public void setMaxTruePrice(Integer maxTruePrice) {
    this.maxTruePrice = maxTruePrice;
  }

  public Integer getPostage() {
    return postage;
  }

  public void setPostage(Integer postage) {
    this.postage = postage;
  }

  public Integer getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(Integer orderBy) {
    this.orderBy = orderBy;
  }

  public Integer getDesc() {
    return desc;
  }

  public void setDesc(Integer desc) {
    this.desc = desc;
  }
}
