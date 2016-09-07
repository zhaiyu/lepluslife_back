package com.jifenke.lepluslive.banner.repository;

import com.jifenke.lepluslive.banner.domain.entities.Banner;
import com.jifenke.lepluslive.merchant.domain.entities.City;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * Created by wcg on 16/3/24.
 */
public interface BannerRepository extends JpaRepository<Banner, Long> {

  Page findAll(Specification<Banner> whereClause, Pageable pageRequest);

  List<Banner> findByCityOrderByCreateDateDesc(City city);

}
