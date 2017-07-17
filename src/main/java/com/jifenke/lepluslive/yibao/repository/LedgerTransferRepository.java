package com.jifenke.lepluslive.yibao.repository;

import com.jifenke.lepluslive.yibao.domain.entities.LedgerTransfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhangwen on 2017/7/12.
 */
public interface LedgerTransferRepository extends JpaRepository<LedgerTransfer, Long> {

    Page findAll(Specification<LedgerTransfer> whereClause, Pageable pageRequest);

}
