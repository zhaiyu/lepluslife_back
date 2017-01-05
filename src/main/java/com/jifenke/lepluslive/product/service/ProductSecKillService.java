package com.jifenke.lepluslive.product.service;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.product.domain.entities.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 商品秒杀 Created by tqy on 2016/1/3.
 */
@Service
@Transactional(readOnly = true)
public class ProductSecKillService {

  @Inject
  private EntityManager em;

  /**
   * 10=首页好店推荐
   * @param secKillDate  //秒杀日期
   * @param state        //1=上架|0=已下架
   *
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<Object[]> findProductSecKillByDateAndState(String secKillDate, Integer state) {
    List<Map> mapList = new ArrayList<>();

    String sql = null;
    sql =   " SELECT psk.id id, psk.sid sid, psk.sec_kill_date, psk.product_id, p.name, psk.convert_score,  psk.convert_price, psk.start_time, psk.end_time, psk.init_number, psk.is_link_product "
          + " FROM product_sec_kill psk LEFT JOIN product p ON psk.product_id = p.id WHERE "
          + " psk.sec_kill_date = "+secKillDate
          + " AND psk.state = "+state;
    sql +=  " ORDER BY psk.sid ASC";
    sql +=  " LIMIT " + 0 + "," + 20;

    Query query = em.createNativeQuery(sql);
    List<Object[]> list = query.getResultList();
    return list;
  }

}
