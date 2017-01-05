package com.jifenke.lepluslive.activity.domain.criteria;

/**
 * Created by tqy on 2017-1-4.
 * 充值卡记录 查询条件
 */
public class RechargeCardCriteria {
    private Integer offset;

    private String startDate;

    private String endDate;

    private String exchangeCode;//充值兑换码

    public String getLjUserSid() {
      return ljUserSid;
    }
    public void setLjUserSid(String ljUserSid) {
      this.ljUserSid = ljUserSid;
    }

    private String ljUserSid;//会员ID
    private String userPhone;//会员手机号
    private String rechargeStatus;//充值状态
    private Integer pageSize = 10;//每页size

    public String getEndDate() {
      return endDate;
    }

    public void setEndDate(String endDate) {
      this.endDate = endDate;
    }

    public String getExchangeCode() {
      return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
      this.exchangeCode = exchangeCode;
    }

    public Integer getOffset() {
      return offset;
    }

    public void setOffset(Integer offset) {
      this.offset = offset;
    }

    public Integer getPageSize() {
      return pageSize;
    }

    public void setPageSize(Integer pageSize) {
      this.pageSize = pageSize;
    }

    public String getRechargeStatus() {
      return rechargeStatus;
    }

    public void setRechargeStatus(String rechargeStatus) {
      this.rechargeStatus = rechargeStatus;
    }

    public String getStartDate() {
      return startDate;
    }

    public void setStartDate(String startDate) {
      this.startDate = startDate;
    }

    public String getUserPhone() {
      return userPhone;
    }

    public void setUserPhone(String userPhone) {
      this.userPhone = userPhone;
    }

}
