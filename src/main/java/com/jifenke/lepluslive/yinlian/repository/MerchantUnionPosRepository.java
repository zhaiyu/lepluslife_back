package com.jifenke.lepluslive.yinlian.repository;

import com.jifenke.lepluslive.yinlian.domain.entities.MerchantUnionPos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by sunxingfei on 2017/4/10.
 */
public interface MerchantUnionPosRepository extends JpaRepository<MerchantUnionPos, Long> {


    @Query(value = "SELECT * FROM  merchant_union_pos WHERE merchant_id=?1", nativeQuery = true)
    MerchantUnionPos findByMerchantId(Long id);


    @Query(value = "SELECT * FROM  merchant_union_pos WHERE merchant_id=?1", nativeQuery = true)
    List<MerchantUnionPos> findListByMerchantId(Long id);


}
