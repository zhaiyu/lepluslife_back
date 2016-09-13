package com.jifenke.lepluslive.merchant.repository;

import com.jifenke.lepluslive.merchant.domain.entities.MerchantProtocol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by wcg on 16/6/7.
 */
public interface MerchantProtocolRepository extends JpaRepository<MerchantProtocol, Long> {

  @Query(value = "select * from merchant_protocol where merchant_id=?1 and pic_state=0", nativeQuery = true)
  public List<MerchantProtocol> getProtImgByMerchantId(Long merchantId);

  @Modifying
  @Query(value = "update merchant_protocol set pic_state=1 where merchant_id=?1  and protocol_type=?2  and pic_index=?3", nativeQuery = true)
  public void updateProtImgByMercIdAndTypeAndIndex(Long merchantId, Long merchantType,
                                                   Long picIndex);
}
