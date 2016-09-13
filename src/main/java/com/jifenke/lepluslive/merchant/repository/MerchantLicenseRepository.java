package com.jifenke.lepluslive.merchant.repository;

import com.jifenke.lepluslive.merchant.domain.entities.MerchantLicense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zxf on 2016/9/9.
 */
public interface MerchantLicenseRepository extends JpaRepository<MerchantLicense,Integer>{
   @Query(value="select * from merchant_license where merchant_id = ?1 and pic_state=0",nativeQuery=true)
   public List<MerchantLicense> getLiceImgByMerchantId(Long merchantId);

   @Modifying
   @Query(value="update merchant_license set pic_state=1 where merchant_id=?1  and license_type=?2  and pic_index=?3",nativeQuery = true)
   public void updateLiceImgByMercIdAndTypeAndIndex(Long merchantId, Long merchantType,
                                                    Long picIndex);
}
