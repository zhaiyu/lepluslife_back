package com.jifenke.lepluslive.withdrawBill.repository;

import com.jifenke.lepluslive.withdrawBill.domain.entities.WeiXinWithdrawBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/8/26.
 */
public interface WeixinWithdrawBillRepository extends JpaRepository<WeiXinWithdrawBill,Long> {

  WeiXinWithdrawBill findByMchBillno(String mchBillno);

  Page findAll(Specification<WeiXinWithdrawBill> whereClause, Pageable pageRequest);



  @Query(value = "SELECT sum(total_price) FROM weixin_withdraw_bill where partner_id=?1 and state=0",nativeQuery = true)
  Long findPartnerOnWithdrawalByPartnerId(Long id);

}
