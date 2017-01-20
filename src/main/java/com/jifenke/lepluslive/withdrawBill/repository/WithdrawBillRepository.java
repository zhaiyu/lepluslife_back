package com.jifenke.lepluslive.withdrawBill.repository;

import com.jifenke.lepluslive.withdrawBill.domain.entities.WithdrawBill;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/8/26.
 */
public interface WithdrawBillRepository extends JpaRepository<WithdrawBill,Long> {

  Page findAll(Specification<WithdrawBill> whereClause, Pageable pageRequest);



  @Query(value = "SELECT sum(total_price) FROM withdraw_bill where partner_id=?1 and bill_type=1 and state=0",nativeQuery = true)
  Long findPartnerOnWithdrawalByPartnerId(Long id);

  @Query(value = "SELECT sum(total_price) FROM withdraw_bill where partner_manager_id=?1 and bill_type=0 and state=0",nativeQuery = true)
  Long findPartnerManagerOnWithdrawalByPartnerManagerId(Long id);

  @Query(value = "SELECT sum(total_price) FROM withdraw_bill where merchant_id=?1 and bill_type=2 and state=0",nativeQuery = true)
  Long findMerchantOnWithdrawalByMerchantId(Long id);


}
