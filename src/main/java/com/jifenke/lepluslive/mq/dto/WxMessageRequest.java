package com.jifenke.lepluslive.mq.dto;

import java.util.List;

public class WxMessageRequest {

  private String first;
  private String keyWord1;
  private String keyWord2;
  private String keyWord3;
  private String keyWord4;
  private List<String> openIds;
  private String url;
  private String remark;
  private WxMsgType wxMsgType;

  public WxMsgType getWxMsgType() {
    return wxMsgType;
  }

  public void setWxMsgType(WxMsgType wxMsgType) {
    this.wxMsgType = wxMsgType;
  }

  public String getKeyWord1() {
    return keyWord1;
  }

  public void setKeyWord1(String keyWord1) {
    this.keyWord1 = keyWord1;
  }

  public String getKeyWord2() {
    return keyWord2;
  }

  public void setKeyWord2(String keyWord2) {
    this.keyWord2 = keyWord2;
  }

  public String getKeyWord3() {
    return keyWord3;
  }

  public void setKeyWord3(String keyWord3) {
    this.keyWord3 = keyWord3;
  }

  public String getKeyWord4() {
    return keyWord4;
  }

  public void setKeyWord4(String keyWord4) {
    this.keyWord4 = keyWord4;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public List<String> getOpenIds() {
    return openIds;
  }

  public void setOpenIds(List<String> openIds) {
    this.openIds = openIds;
  }

  public String getFirst() {
    return first;
  }

  public void setFirst(String first) {
    this.first = first;
  }
}
