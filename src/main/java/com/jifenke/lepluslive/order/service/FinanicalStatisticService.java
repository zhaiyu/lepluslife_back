package com.jifenke.lepluslive.order.service;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.order.domain.entities.FinancialStatistic;
import com.jifenke.lepluslive.order.repository.FinancialStatisticRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by wcg on 16/5/21.
 */
@Service
@Transactional(readOnly = true)
public class FinanicalStatisticService {

  @Inject
  private MerchantService merchantService;

  @Inject
  private FinancialStatisticRepository financialStatisticRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void createFinancialstatistic(Object[] object, Date date) {
    Merchant
        merchant =
        merchantService.findMerchantById(object[0] == null ? Long.parseLong(object[5].toString())
                                                           : Long.parseLong(object[0].toString()));
    if (merchant != null) {
      FinancialStatistic financialStatistic = new FinancialStatistic();
      financialStatistic.setMerchant(merchant);
      financialStatistic.setBalanceDate(date);
      financialStatistic
          .setTransferPrice(object[1] == null ? 0 : ((BigDecimal) object[1]).longValue());
      financialStatistic
          .setTransferFromTruePay(object[3] == null ? 0 : ((BigDecimal) object[3]).longValue());
      financialStatistic.setAppTransfer(
          object[2] == null ? 0 : ((BigDecimal) object[2]).longValue());
      financialStatistic
          .setAppTransFromTruePay(object[4] == null ? 0 : ((BigDecimal) object[4]).longValue());
      financialStatistic.setPosTransfer(
          object[6] == null ? 0 : ((BigDecimal) object[6]).longValue());
      financialStatistic
          .setPosTransFromTruePay(object[7] == null ? 0 : ((BigDecimal) object[7]).longValue());
      financialStatisticRepository.save(financialStatistic);
    }
  }

}
