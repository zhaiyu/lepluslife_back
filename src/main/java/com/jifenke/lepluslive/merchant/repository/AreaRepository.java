package com.jifenke.lepluslive.merchant.repository;

import com.jifenke.lepluslive.merchant.domain.entities.Area;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by tqy on 2017/1/9.
 */
public interface AreaRepository extends JpaRepository<Area, Long> {

  Page<Area> findAll(Pageable pageable);

  /**
   * 根据cityId 查询 area
   */
  @Query(value = "select id, name from area where city_id = ?1", nativeQuery = true)
  List<Object[]> getAreaByCityId(Long city_id);

}
