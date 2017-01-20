package com.jifenke.lepluslive.merchant.repository;

import com.jifenke.lepluslive.merchant.domain.entities.City;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by wcg on 16/3/17.
 */
public interface CityRepository extends JpaRepository<City, Long> {

  Page<City> findAll(Pageable pageable);

  /**
   * 查询所有省份
   */
  @Query(value = "SELECT province FROM city GROUP BY province", nativeQuery = true)
  List<Object[]> getAllProvince();

  /**
   * 根据省 查询 市
   */
  @Query(value = "select id, name from city where province = ?1", nativeQuery = true)
  List<Object[]> getCityByProvince(String province);

  /**
   * 根据省 查询 市
   */
  @Query(value = "SELECT * FROM city WHERE province=?1", nativeQuery = true)
  List<City> findCityByProvince(String province);

}
