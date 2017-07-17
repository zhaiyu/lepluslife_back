package com.jifenke.lepluslive.groupon.domain.criteria;

/**
 * 团购产品 - 查询条件
 * Created by xf on 17-6-16.
 */
public class GrouponProductCriteria {

    private String merchantUser;            // 所属商户
    private String sid;                     // 团购SID
    private String name;                    // 团购名称
    private Integer state;                  // 团购状态  0下架 1 上架
    private Integer offset;                 // 页数

    public String getMerchantUser() {
        return merchantUser;
    }

    public void setMerchantUser(String merchantUser) {
        this.merchantUser = merchantUser;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
