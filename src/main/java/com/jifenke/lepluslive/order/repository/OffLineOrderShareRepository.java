package com.jifenke.lepluslive.order.repository;

import com.jifenke.lepluslive.order.domain.entities.OffLineOrderShare;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wcg on 16/5/5.
 */
public interface OffLineOrderShareRepository extends JpaRepository<OffLineOrderShare,Long> {

  Page findAll(Specification<OffLineOrderShare> whereClause, Pageable pageRequest);
}
