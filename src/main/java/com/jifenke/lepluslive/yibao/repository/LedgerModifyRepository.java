package com.jifenke.lepluslive.yibao.repository;

import com.jifenke.lepluslive.yibao.domain.entities.LedgerModify;
import com.jifenke.lepluslive.yibao.domain.entities.MerchantUserLedger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhangwen on 2017/7/12.
 */
public interface LedgerModifyRepository extends JpaRepository<LedgerModify, Long> {

  LedgerModify findByRequestId(String requestId);

  Page findAll(Specification<LedgerModify> whereClause, Pageable pageRequest);

  /**
   * 查询某子商户某个状态的修改记录列表 2017/7/16
   *
   * @param merchantUserLedger 子商户
   * @param state              状态
   */
  List<LedgerModify> findAllByMerchantUserLedgerAndState(MerchantUserLedger merchantUserLedger,
                                                         Integer state);

}
