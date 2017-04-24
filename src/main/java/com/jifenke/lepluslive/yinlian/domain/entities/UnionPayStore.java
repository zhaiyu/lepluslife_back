package com.jifenke.lepluslive.yinlian.domain.entities;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;

import javax.persistence.*;
import java.util.Date;

/**
 * 银联商务门店 Created by zhangwen on 2017/1/17.
 */
@Entity
@Table(name = "UNION_PAY_STORE")
public class UnionPayStore {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private Merchant merchant;

  @Column(nullable = false, length = 30)
  private String shopNumber;  //银联门店号

  @Column(length = 100)
  private String shopName;  //门店名称

  private String address;   //门店地址

  @Column(length = 30)
  private String merchantNum;   //营销联盟商户号

  private String longitude;  //带六位小数点，下同

  private String latitude;  //纬度

  @Column(length = 100)
  private String cityName;  //城市名称

  @Column(length = 10)
  private String cityCode;  //城市代码

  @Column(length = 50)
  private String provinceName;  //省份名称

  @Column(length = 4)
  private String provinceCode;  //省份代码

  private String termNos;   //门店终端号|多终端时，使用半角逗号分隔

  private Date createDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Merchant getMerchant() {
    return merchant;
  }

  public void setMerchant(Merchant merchant) {
    this.merchant = merchant;
  }

  public String getShopNumber() {
    return shopNumber;
  }

  public void setShopNumber(String shopNumber) {
    this.shopNumber = shopNumber;
  }

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getMerchantNum() {
    return merchantNum;
  }

  public void setMerchantNum(String merchantNum) {
    this.merchantNum = merchantNum;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public String getCityCode() {
    return cityCode;
  }

  public void setCityCode(String cityCode) {
    this.cityCode = cityCode;
  }

  public String getProvinceName() {
    return provinceName;
  }

  public void setProvinceName(String provinceName) {
    this.provinceName = provinceName;
  }

  public String getProvinceCode() {
    return provinceCode;
  }

  public void setProvinceCode(String provinceCode) {
    this.provinceCode = provinceCode;
  }

  public String getTermNos() {
    return termNos;
  }

  public void setTermNos(String termNos) {
    this.termNos = termNos;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
}
