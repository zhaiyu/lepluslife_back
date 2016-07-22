package com.jifenke.lepluslive.partner.service;

import com.jifenke.lepluslive.global.util.MD5Util;
import com.jifenke.lepluslive.partner.domain.entities.Partner;
import com.jifenke.lepluslive.partner.repository.PartnerManagerRepository;
import com.jifenke.lepluslive.partner.repository.PartnerManagerWalletRepository;
import com.jifenke.lepluslive.partner.repository.PartnerRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by wcg on 16/6/3.
 */
@Service
@Transactional(readOnly = true)
public class PartnerService {

  @Inject
  private EntityManager entityManager;

  @Inject
  private PartnerRepository partnerRepository;

  @Inject
  private PartnerManagerWalletRepository partnerManagerWalletRepository;

  @Inject
  private PartnerManagerRepository partnerManagerRepository;


  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public List<Partner> findAllParter() {
    return partnerRepository.findAll();
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List findAll() {

    Query
        query =
        entityManager.createNativeQuery(
            "select partner.id ,partner.name,partner.user_limit,partner.merchant_limit,partner.available,partner.total,partner.partner_name partner_name,partner.phone_number phone_number,partner.password password,ifnull(bindUser.bind ,0)userBind,ifnull(bindMerchant.bindMerchant,0) as merchantBind from (select partner.id id,partner.merchant_limit merchant_limit,partner.name name,partner.partner_name partner_name,partner.phone_number phone_number,partner.password password,partner.user_limit user_limit,partner_wallet.available_balance available,partner_wallet.total_money total from partner,partner_wallet where partner_wallet.partner_id = partner.id )partner left join (select count(*) bind,partner.id ids from le_jia_user,partner where partner.id = le_jia_user.bind_partner_id group by partner.id )bindUser on bindUser.ids = partner.id left join (select count(*)bindMerchant,partner.id id from merchant,partner where partner.id = merchant.partner_id group by merchant.partner_id)bindMerchant on bindMerchant.id = partner.id");

    List<Object[]> details = query.getResultList();

    return details;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List findAllPartnerManagerByWallet() {
   return partnerManagerWalletRepository.findAll();
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editPartnerPassword(Partner partner) {
    Partner origin = partnerRepository.findOne(partner.getId());
    origin.setName(partner.getName());
    origin.setPassword(MD5Util.MD5Encode(partner.getPassword(),"utf-8"));
    partnerRepository.save(origin);

  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Partner findPartnerById(Long id) {
    return partnerRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List findAllPartnerManager() {
    return partnerManagerRepository.findAll();
  }
}
