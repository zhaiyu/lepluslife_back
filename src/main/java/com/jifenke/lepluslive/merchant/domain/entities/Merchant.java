package com.jifenke.lepluslive.merchant.domain.entities;

import com.jifenke.lepluslive.global.util.MvUtil;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by wcg on 16/3/17.
 */
@Entity
@Table(name = "MERCHANT")
public class Merchant {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Integer sid;

  @OneToMany(fetch = FetchType.LAZY)
  private List<MerchantBank> merchantBanks;

  private String merchantSid = MvUtil.getMerchantSid();

  @ManyToOne
  private City city;

  private String thumb;//缩略图

  @ManyToOne
  private MerchantType merchantType;

  private String location;

  private String name;

  private String picture;

  private String phoneNumber;

  private Integer discount; //折扣

  private Integer rebate;  //返利

  private Double lng;

  private Double lat;

  @ManyToOne
  private Area area;

  public String getMerchantSid() {
    return merchantSid;
  }

  public void setMerchantSid(String merchantSid) {
    this.merchantSid = merchantSid;
  }

  public String getThumb() {
    return thumb;
  }

  public void setThumb(String thumb) {
    this.thumb = thumb;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public Integer getDiscount() {
    return discount;
  }

  public void setDiscount(Integer discount) {
    this.discount = discount;
  }

  public Integer getRebate() {
    return rebate;
  }

  public void setRebate(Integer rebate) {
    this.rebate = rebate;
  }

  public MerchantType getMerchantType() {
    return merchantType;
  }

  public void setMerchantType(MerchantType merchantType) {
    this.merchantType = merchantType;
  }

  public Area getArea() {
    return area;
  }

  public void setArea(Area area) {
    this.area = area;
  }

  public Double getLng() {
    return lng;
  }

  public void setLng(Double lng) {
    this.lng = lng;
  }

  public Double getLat() {
    return lat;
  }

  public void setLat(Double lat) {
    this.lat = lat;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getSid() {
    return sid;
  }

  public void setSid(Integer sid) {
    this.sid = sid;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
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

}
