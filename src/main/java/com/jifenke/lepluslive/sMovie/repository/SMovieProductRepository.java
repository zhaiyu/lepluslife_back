package com.jifenke.lepluslive.sMovie.repository;

import com.jifenke.lepluslive.sMovie.domain.entities.SMovieProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lss on 2017/5/4.
 */
public interface SMovieProductRepository extends JpaRepository<SMovieProduct, Long> {
    Page findAll(Specification<SMovieProduct> whereClause, Pageable pageRequest);
}
