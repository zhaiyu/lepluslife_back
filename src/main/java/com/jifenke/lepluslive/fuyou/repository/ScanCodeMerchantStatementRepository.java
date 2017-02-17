package com.jifenke.lepluslive.fuyou.repository;

import com.jifenke.lepluslive.fuyou.domain.entities.ScanCodeMerchantStatement;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 富友扫码结算单 Created by zhangwen on 16/12/19.
 */
public interface ScanCodeMerchantStatementRepository
    extends JpaRepository<ScanCodeMerchantStatement, Long> {

  /**
   * 根据门店和交易日期获取结算单 2016/12/30
   *
   * @param merchant  门店
   * @param tradeDate 交易日期
   */
  ScanCodeMerchantStatement findByMerchantAndTradeDate(Merchant merchant, String tradeDate);

  /**
   * 根据门店ID和交易日期获取结算单 2017/01/03
   *
   * @param merchantId 门店ID
   * @param tradeDate  交易日期
   */
  @Query(value = "SELECT * FROM scan_code_merchant_statement WHERE merchant_id = ?1 AND trade_date = ?2", nativeQuery = true)
  ScanCodeMerchantStatement findByMerchantIdAndTradeDate(Long merchantId, String tradeDate);
}
