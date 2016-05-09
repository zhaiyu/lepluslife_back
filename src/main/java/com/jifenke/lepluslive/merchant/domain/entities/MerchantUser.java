package com.jifenke.lepluslive.merchant.domain.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by wcg on 16/5/9.
 */
@Entity
@Table(name = "MERCHANT_USER")
public class MerchantUser {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

}
