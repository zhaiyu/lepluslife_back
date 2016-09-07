package com.jifenke.lepluslive.merchant.service;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantScroll;
import com.jifenke.lepluslive.merchant.repository.MerchantScrollRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by wcg on 16/6/8.
 */
@Service
@Transactional(readOnly = true)
public class MerchantScrollService {

  @Inject
  private MerchantScrollRepository merchantScrollRepository;

  public List<MerchantScroll> findAllScorllPicture(Merchant merchant) {
    return merchantScrollRepository.findAllByMerchant(merchant);
  }


  public MerchantScroll findScrollPictureById(Long id) {
    return merchantScrollRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editScrollPicture(MerchantScroll merchantScroll) {
    MerchantScroll origin = null;
    if (merchantScroll.getId() != null) {
      origin = merchantScrollRepository.findOne(merchantScroll.getId());
    } else {
      origin = new MerchantScroll();
    }
    origin.setPicture(merchantScroll.getPicture());
    origin.setSid(merchantScroll.getSid());
    origin.setMerchant(merchantScroll.getMerchant());
    merchantScrollRepository.save(origin);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteScrollPicture(Long id) {
    merchantScrollRepository.delete(id);
  }

}
