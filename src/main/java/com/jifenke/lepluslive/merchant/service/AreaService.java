package com.jifenke.lepluslive.merchant.service;

import com.jifenke.lepluslive.merchant.domain.entities.City;
import com.jifenke.lepluslive.merchant.repository.AreaRepository;
import com.jifenke.lepluslive.merchant.repository.CityRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by tqy on 2017/1/9.
 */
@Service
@Transactional(readOnly = true)
public class AreaService {

  @Inject
  private AreaRepository areaRepository;

  /**
   * 根据cityId 查询 area
   */
  public List<Object[]> getAreaByCityId(Long city_id){
    return areaRepository.getAreaByCityId(city_id);
  }

}
