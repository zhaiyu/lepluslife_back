package com.jifenke.lepluslive.product.repository;

import com.jifenke.lepluslive.product.domain.entities.ProductRecommend;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by wcg on 16/3/25.
 */
public interface ProductRecommendRepository extends JpaRepository<ProductRecommend, Integer> {

  Page findAllByState(Integer state, Pageable pageRequest);
}
