package com.jifenke.lepluslive.sMovie.repository;

import com.jifenke.lepluslive.sMovie.domain.entities.SMovieProduct;
import com.jifenke.lepluslive.sMovie.domain.entities.SMovieTerminal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lss on 17-5-3.
 */
public interface SMovieTerminalRepository extends JpaRepository<SMovieTerminal, Long> {
    Page findAll(Specification<SMovieProduct> whereClause, Pageable pageRequest);
}
