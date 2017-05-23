package com.jifenke.lepluslive.partner.domain.criteria;

/**
 * Created by lss on 2017/5/11.
 */
public class PartnerCriteria {

    private Integer offset;

    private Integer origin;

    private String name;

    private String phoneNumber;

    private String registerStartDate;

    private String registerEndDate;


    public String getRegisterStartDate() {
        return registerStartDate;
    }

    public void setRegisterStartDate(String registerStartDate) {
        this.registerStartDate = registerStartDate;
    }

    public String getRegisterEndDate() {
        return registerEndDate;
    }

    public void setRegisterEndDate(String registerEndDate) {
        this.registerEndDate = registerEndDate;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
