package com.jifenke.lepluslive.sMovie.repository;


import com.jifenke.lepluslive.sMovie.domain.entities.SMovieOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lss on 2016/5/20.
 */
public interface SMovieOrderRepository extends JpaRepository<SMovieOrder, Long> {
    Page findAll(Specification<SMovieOrder> whereClause, Pageable pageRequest);
}
