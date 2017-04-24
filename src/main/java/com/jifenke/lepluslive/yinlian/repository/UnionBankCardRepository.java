package com.jifenke.lepluslive.yinlian.repository;

import com.jifenke.lepluslive.yinlian.domain.entities.UnionBankCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lss on 17-4-17.
 */
public interface UnionBankCardRepository extends JpaRepository<UnionBankCard, Long> {
    Page findAll(Specification<UnionBankCard> whereClause, Pageable pageRequest);

    UnionBankCard findUnionBankCardByNumber(String number);
}
