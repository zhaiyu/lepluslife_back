package com.jifenke.lepluslive.groupon.domain.criteria;

/**
 * 团购订单 - 查询条件
 *
 * @author XF
 * @date 2017/6/19
 */
public class GrouponOrderCriteria {
    private String sid;                     // 订单SID
    private String productSid;                 // 团购SID
    private String name;                    // 团购名称
    private Integer orderState;                  // 团购状态  0下架 1 上架
    private Integer offset;                 // 页数
    private Integer type;                   // 页数

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getProductSid() {
        return productSid;
    }

    public void setProductSid(String productSid) {
        this.productSid = productSid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

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
}
