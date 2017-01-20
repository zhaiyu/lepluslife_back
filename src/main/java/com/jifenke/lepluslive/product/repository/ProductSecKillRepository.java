package com.jifenke.lepluslive.product.repository;

import com.jifenke.lepluslive.product.domain.entities.ProductSecKill;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 秒杀概览
 * Created by tqy on 2017/1/11.
 */
public interface ProductSecKillRepository extends JpaRepository<ProductSecKill, Integer> {

  Page findAll(Specification<ProductSecKill> specification, Pageable pageRequest);

}
