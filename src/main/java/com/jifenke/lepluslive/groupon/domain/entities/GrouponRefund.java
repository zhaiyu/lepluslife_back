package com.jifenke.lepluslive.groupon.domain.entities;

import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by wcg on 2017/6/14. 团购退款申请
 */
@Entity
@Table(name = "GROUPON_REFUND")
public class GrouponRefund {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  private GrouponOrder grouponOrder;

  private Integer state; //0 未完成 1 完成 2 驳回

  private Date createDate;

  private Date completeDate;

  private String description;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getCompleteDate() {
    return completeDate;
  }

  public void setCompleteDate(Date completeDate) {
    this.completeDate = completeDate;
  }
}
