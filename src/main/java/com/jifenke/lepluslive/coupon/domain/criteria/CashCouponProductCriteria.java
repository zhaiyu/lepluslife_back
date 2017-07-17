package com.jifenke.lepluslive.coupon.domain.criteria;

/**
 * CashCouponProductCriteria
 * 满减券查询条件
 *
 * @author XF
 * @date 2017/7/6
 */
public class CashCouponProductCriteria {
    private String merchantName;     //  所属商户
    private String price;            //  面额
    private Long productSid;         //  产品ID
    private Integer state;          //  状态
    private Integer offset;         //  页数

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getProductSid() {
        return productSid;
    }

    public void setProductSid(Long productSid) {
        this.productSid = productSid;
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
