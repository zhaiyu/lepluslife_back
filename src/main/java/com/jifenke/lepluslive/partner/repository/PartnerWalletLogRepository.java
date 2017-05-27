package com.jifenke.lepluslive.partner.repository;

import com.jifenke.lepluslive.partner.domain.entities.PartnerWalletLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wcg on 16/6/3.
 */
public interface PartnerWalletLogRepository extends JpaRepository<PartnerWalletLog, Long> {
    Page findAll(Specification<PartnerWalletLog> whereClause, Pageable pageRequest);
}
