package com.jifenke.lepluslive.activity.domain.criteria;

/**
 * 话费产品筛选条件 Created by zhangwen on 16/12/7.
 */
public class PhoneRuleCriteria {

  private Integer state; //产品状态 1=上架 0=下架不可使用


  private Integer currPage = 1;  //当前页码

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public Integer getCurrPage() {
    return currPage;
  }

  public void setCurrPage(Integer currPage) {
    this.currPage = currPage;
  }
}
