package com.jifenke.lepluslive.shortMessage.domain.criteria;


/**
 * Created by lss on 2016/10/20.
 */
public class ShortMessageCriteria {

  private String reqid;

  private Long shortMessageScene;

  private String startDate;

  private String endDate;

  private Integer offset;

  private String category;

  private String reqCode;

  public String getReqCode() {
    return reqCode;
  }

  public void setReqCode(String reqCode) {
    this.reqCode = reqCode;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public String getReqid() {
    return reqid;
  }

  public void setReqid(String reqid) {
    this.reqid = reqid;
  }

  public Long getShortMessageScene() {
    return shortMessageScene;
  }

  public void setShortMessageScene(Long shortMessageScene) {
    this.shortMessageScene = shortMessageScene;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }
}
