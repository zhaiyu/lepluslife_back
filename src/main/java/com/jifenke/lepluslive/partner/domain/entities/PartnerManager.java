package com.jifenke.lepluslive.partner.domain.entities;

import com.jifenke.lepluslive.merchant.domain.entities.Area;
import com.jifenke.lepluslive.merchant.domain.entities.City;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wcg on 16/6/21.
 */
@Entity
@Table(name = "PARTNER_MANAGER")
public class PartnerManager {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String name;

    private String payee;

    private Long userLimit = 0L;                                // 绑定用户上限

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserLimit() {
        return userLimit;
    }

    public void setUserLimit(Long userLimit) {
        this.userLimit = userLimit;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    private String bankNumber;           // 银行卡号

    private String bankName;             // 银行名

    private String phoneNumber;         // 电话

    @ManyToOne
    private City city;                   // 城市

    private Long partnerId;              // 登录账号

    private Long bindMerchantLimit;      // 锁定门店上限

    private Long bindPartnerLimit;       // 锁定天使合伙人上限

    private String password;             //  登录密码

    private String partnerManagerSid;    //   sid

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public Long getBindMerchantLimit() {
        return bindMerchantLimit;
    }

    public void setBindMerchantLimit(Long bindMerchantLimit) {
        this.bindMerchantLimit = bindMerchantLimit;
    }

    public Long getBindPartnerLimit() {
        return bindPartnerLimit;
    }

    public void setBindPartnerLimit(Long bindPartnerLimit) {
        this.bindPartnerLimit = bindPartnerLimit;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPartnerManagerSid() {
        return partnerManagerSid;
    }

    public void setPartnerManagerSid(String partnerManagerSid) {
        this.partnerManagerSid = partnerManagerSid;
    }
}
