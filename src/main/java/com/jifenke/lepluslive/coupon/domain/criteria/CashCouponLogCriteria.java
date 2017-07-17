package com.jifenke.lepluslive.coupon.domain.criteria;

/**
 * CashCouponLogCriteria
 * 领券记录 - 查询条件
 *
 * @author XF
 * @date 2017/7/5
 */
public class CashCouponLogCriteria {

    private String wxName;      //  微信昵称
    private String userId;          //  消费者ID
    private String productName;     //  满减券名称
    private Long couponSid;          //  消费单号
    private Integer state;          //  状态
    private Integer offset;         //  页数

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getCouponSid() {
        return couponSid;
    }

    public void setCouponSid(Long couponSid) {
        this.couponSid = couponSid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
