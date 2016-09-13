package com.jifenke.lepluslive.merchant.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by zxf on 2016/9/9.
 */
@Entity
@Table(name = "MERCHANT_LICENSE")
public class MerchantLicense {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String picture;                     // 图片 URL

  @ManyToOne
  private Merchant merchant;                  // 商户id

  @Column(name="license_type",length = 20,columnDefinition = "BIGINT")
  private Long licenseType;              // 图片类型： 1-营业执照  2-法人身份证 3-税务登记证 4-结算银行卡持有身份证 5-组织结构照 6-结算银行卡

  @Column(name="pic_index",length = 20,columnDefinition = "BIGINT")
  private Long picIndex;                         // 位置索引: 从0开始
  @Column(name="pic_state",length = 20,columnDefinition = "BIGINT")
  private Long picState;                         // 图片状态: 0-正常 1-失效

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public Merchant getMerchant() {
    return merchant;
  }

  public void setMerchant(Merchant merchant) {
    this.merchant = merchant;
  }

  public Long getPicIndex() {
    return picIndex;
  }

  public void setPicIndex(Long picIndex) {
    this.picIndex = picIndex;
  }

  public Long getPicState() {
    return picState;
  }

  public void setPicState(Long picState) {
    this.picState = picState;
  }


  public Long getLicenseType() {
    return licenseType;
  }

  public void setLicenseType(Long licenseType) {
    this.licenseType = licenseType;
  }
}
