package com.jifenke.lepluslive.product.service;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.product.domain.entities.Product;
import com.jifenke.lepluslive.product.domain.entities.ProductSecKillCriteria;
import com.jifenke.lepluslive.product.domain.entities.ProductSecKillTime;
import com.jifenke.lepluslive.product.repository.ProductSecKillTimeRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
 * 秒杀时段 Created by tqy on 2016/1/3.
 */
@Service
@Transactional(readOnly = true)
public class ProductSecKillTimeService {

  @Inject
  private EntityManager em;
  @Inject
  private ProductSecKillTimeRepository productSecKillTimeRepository;

  /**
   * 秒杀时段 新增或修改
   * @param productSecKillTime
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public int saveProductSecKillTime(ProductSecKillTime productSecKillTime) {

    ProductSecKillTime db_pskt = null;
    Date date = new Date();
    Integer id = productSecKillTime.getId();

    //依据日期 开始时间 结束时间 查重...
//    List<ProductSecKillTime> list111 = productSecKillTimeRepository.findProductSecKillTimeBySecKillDateAndStartTimeAndEndTime();

    if (id != null){//修改
      db_pskt = productSecKillTimeRepository.findOne(id);
      if (db_pskt==null){
        return 499;
      }
    }else {//新建
      db_pskt = new ProductSecKillTime();
      db_pskt.setCreateTime(date);
    }

    db_pskt.setUpdateTime(date);
    db_pskt.setSecKillDateName(productSecKillTime.getSecKillDateName());
    db_pskt.setSecKillDate(productSecKillTime.getSecKillDate());
    db_pskt.setStartTime(productSecKillTime.getStartTime());
    db_pskt.setEndTime(productSecKillTime.getEndTime());
    db_pskt.setTimeLimitNumber(productSecKillTime.getTimeLimitNumber());
    if(productSecKillTime.getNote()!=null && !productSecKillTime.getNote().equals("")){
      db_pskt.setNote(productSecKillTime.getNote());
    }
    productSecKillTimeRepository.save(db_pskt);

    return 200;
  }

  /**
   * 秒杀时段 查询
   * @param productSecKillCriteria
   * @return
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Page findByCriteria(ProductSecKillCriteria productSecKillCriteria) {
    Sort sort = new Sort(Sort.Direction.DESC, "createTime");
    PageRequest pageRequest = new PageRequest(productSecKillCriteria.getOffset() - 1, productSecKillCriteria.getPageSize(), sort);
    return productSecKillTimeRepository.findAll(getWhereClause(productSecKillCriteria), pageRequest);
  }
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public static Specification<ProductSecKillTime> getWhereClause(ProductSecKillCriteria criteria) {
    return new Specification<ProductSecKillTime>() {
      @Override
      public Predicate toPredicate(Root<ProductSecKillTime> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (criteria.getSecKillDate() != null && !criteria.getSecKillDate().equals("")) {  //秒杀日期(时段名称)
          predicate.getExpressions().add(cb.equal(root.get("secKillDate"), criteria.getSecKillDate()));
        }
        return predicate;
      }
    };
  }

  /**
   * 秒杀时段 id查询
   * @param id
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public ProductSecKillTime findById(Integer id) {
    return productSecKillTimeRepository.findOne(id);
  }
  /**
   * 秒杀时段 查询所有
   * @param
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<ProductSecKillTime> findAll() {
    return productSecKillTimeRepository.findAll();
  }
  /**
   * 秒杀时段 删除
   * @param id
   * @return
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteProductSecKillTime(Integer id) {
    productSecKillTimeRepository.delete(id);
  }

}
