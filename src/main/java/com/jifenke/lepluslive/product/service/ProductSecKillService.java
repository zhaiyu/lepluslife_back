package com.jifenke.lepluslive.product.service;

import com.jifenke.lepluslive.barcode.service.BarcodeService;
import com.jifenke.lepluslive.filemanage.service.FileImageService;
import com.jifenke.lepluslive.product.controller.dto.LimitProductDto;
import com.jifenke.lepluslive.product.domain.entities.Product;
import com.jifenke.lepluslive.product.domain.entities.ProductDetail;
import com.jifenke.lepluslive.product.domain.entities.ProductSecKill;
import com.jifenke.lepluslive.product.domain.entities.ProductSecKillCriteria;
import com.jifenke.lepluslive.product.domain.entities.ProductSecKillTime;
import com.jifenke.lepluslive.product.domain.entities.ProductSpec;
import com.jifenke.lepluslive.product.domain.entities.ProductType;
import com.jifenke.lepluslive.product.domain.entities.ScrollPicture;
import com.jifenke.lepluslive.product.repository.ProductDetailRepository;
import com.jifenke.lepluslive.product.repository.ProductRepository;
import com.jifenke.lepluslive.product.repository.ProductSecKillRepository;
import com.jifenke.lepluslive.product.repository.ProductSpecRepository;
import com.jifenke.lepluslive.product.repository.ProductTypeRepository;
import com.jifenke.lepluslive.product.repository.ScrollPictureRepository;

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
 * 秒杀概览 Created by tqy on 2016/1/3.
 */
@Service
@Transactional(readOnly = true)
public class ProductSecKillService {

  @Inject
  private EntityManager em;
  @Inject
  private ProductSecKillRepository productSecKillRepository;
  @Inject
  private ProductSpecService productSpecService;
  @Inject
  private ProductRepository productRepository;
  @Inject
  private ProductDetailRepository productDetailRepository;
  @Inject
  private ProductSpecRepository productSpecRepository;
  @Inject
  private ScrollPictureRepository scrollPictureRepository;

  /**
   * 秒杀概览 查询
   * @param secKillDate  //秒杀日期
   * @param state        //1=上架|0=已下架
   *
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<Object[]> findProductSecKillByDateAndState(String secKillDate, Integer state) {

    String sql = null;
    sql =   " SELECT psk.id id, psk.sid sid, psk.sec_kill_date, psk.product_id, p.name, psk.convert_score,  psk.convert_price, psk.start_time, psk.end_time, psk.init_number, psk.is_link_product "
          + " FROM product_sec_kill_time psk LEFT JOIN product p ON psk.product_id = p.id WHERE "
          + " psk.sec_kill_date = "+secKillDate
          + " AND psk.state = "+state;
    sql +=  " ORDER BY psk.sid ASC";
    sql +=  " LIMIT " + 0 + "," + 20;

    Query query = em.createNativeQuery(sql);
    List<Object[]> list = query.getResultList();
    return list;
  }



  /**
   * 秒杀概览 查询
   * @param pskId 秒杀概览id
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public ProductSecKill findById(Integer pskId) {
    return productSecKillRepository.findOne(pskId);
  }

  /**
   * 秒杀概览 查询
   * @param criteria 条件
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Map findProductSecKillByPage(ProductSecKillCriteria criteria) {
    Page<ProductSecKill>
        page =
        productSecKillRepository.findAll(getWhereClause(criteria),
                                  new PageRequest(criteria.getOffset() - 1, criteria.getPageSize(),
                                                  new Sort(Sort.Direction.ASC, "id")));
    List<ProductSecKill> list = page.getContent();
    Map<String, Object> result = new HashMap<>();
    List<Map> pskList = new ArrayList<>();
    result.put("totalPages", page.getTotalPages());
    result.put("totalElements", page.getTotalElements());
    for (ProductSecKill psk : list) {
      Product product = psk.getProduct();
      ProductSecKillTime pskt = psk.getProductSecKillTime();

      Map<String, Object> pro = new HashMap<>();
      pro.put("pskId", psk.getId());
      pro.put("psktName", pskt.getSecKillDate());
      pro.put("psktName2", pskt.getSecKillDateName());
      pro.put("productId", product.getId());
      pro.put("productName", product.getName());
      pro.put("minScore", product.getMinScore());
      pro.put("minPrice", product.getMinPrice());
      pro.put("startTime", pskt.getStartTime());
      pro.put("endTime", pskt.getEndTime());
      pro.put("customSale", product.getCustomSale());
      pro.put("saleNumber", product.getSaleNumber());
      pro.put("isLinkProduct", psk.getIsLinkProduct());
      pro.put("state", product.getState());

      //库存
      List<ProductSpec> specList = productSpecService.findProductSpecsByProduct(product);
      int repository = 0;
      for (ProductSpec spec : specList) {
        if (spec.getState() == 1) {
          repository += spec.getRepository();
        }
      }
      pro.put("repository", repository);
      pskList.add(pro);
    }
    result.put("pskList", pskList);
    return result;
  }
  public static Specification<ProductSecKill> getWhereClause(ProductSecKillCriteria criteria) {
    return new Specification<ProductSecKill>() {
      @Override
      public Predicate toPredicate(Root<ProductSecKill> r, CriteriaQuery<?> q, CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (criteria.getSecKillDate() != null && !criteria.getSecKillDate().equals("")) {  //秒杀日期(时段名称)
          predicate.getExpressions().add(cb.equal(r.get("productSecKillTime").get("secKillDate"), criteria.getSecKillDate()));
        }
        if (criteria.getStatus() != null) { //秒杀商品 状态   1=正常  0=下架
          predicate.getExpressions().add(cb.equal(r.get("product").get("state"), criteria.getStatus()));
        }
        if (criteria.getType() != null) {  //秒杀商品 商品类型type=3
          predicate.getExpressions().add(cb.equal(r.get("product").get("type"), criteria.getType()));
        }
        return predicate;
      }
    };
  }

  /**
   * 秒杀概览 保存商品
   * @param
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveProductSecKill(LimitProductDto limitProductDto) throws Exception {

    ProductSecKill productSecKill = limitProductDto.getProductSecKill();
    Product product = productSecKill.getProduct();
    List<ProductSpec> specList = limitProductDto.getProductSpecList();
    List<ProductDetail> detailList = limitProductDto.getProductDetailList();
    List<ScrollPicture> scrollList = limitProductDto.getScrollPictureList();
    List<ProductSpec> delSpecList = limitProductDto.getDelSpecList();
    List<ProductDetail> delDetailList = limitProductDto.getDelDetailList();
    List<ScrollPicture> delScrollList = limitProductDto.getDelScrollList();
    try {
      Date date = new Date();
      Product DBProduct = null;
      if (product.getId() == null) {
        DBProduct = new Product();
      } else {
        DBProduct = productRepository.findOne(product.getId());
        if (DBProduct == null) {
          throw new RuntimeException();
        }
      }
      DBProduct.setLastUpdate(date);
      DBProduct.setState(1);
      DBProduct.setType(3);//秒杀商品 商品类型type=3
      DBProduct.setBuyLimit(product.getBuyLimit());
      DBProduct.setMinPrice(product.getMinPrice());
      DBProduct.setMinScore(product.getMinScore());
      DBProduct.setName(product.getName());
      DBProduct.setDescription(product.getDescription());
      DBProduct.setPrice(product.getPrice());
      DBProduct.setPostage(product.getPostage());
      DBProduct.setCustomSale(product.getCustomSale());
      DBProduct.setPicture(product.getPicture());
      if (product.getIsBackRed() != null){
        DBProduct.setIsBackRed(product.getIsBackRed());
      }
      if (product.getBackRedType() != null){
        DBProduct.setBackRedType(product.getBackRedType());
      }
      if (product.getBackRatio() != null){
        DBProduct.setBackRatio(product.getBackRatio());
      }
      if (product.getBackMoney() != null){
        DBProduct.setBackMoney(product.getBackMoney());
      }
      productRepository.saveAndFlush(DBProduct);


      //保存或修改秒杀概览
      ProductSecKill productSecKill_db = null;
      if (productSecKill.getId() == null){
        productSecKill_db = new ProductSecKill();
        productSecKill_db.setCreateTime(date);
      }else {
        productSecKill_db = productSecKillRepository.findOne(productSecKill.getId());
        if (productSecKill_db == null) {
          throw new RuntimeException();
        }
      }
      productSecKill_db.setUpdateTime(date);
      productSecKill_db.setProductSecKillTime(productSecKill.getProductSecKillTime());
      productSecKill_db.setProduct(DBProduct);
      productSecKill_db.setIsLinkProduct(productSecKill.getIsLinkProduct());
      if (productSecKill.getIsLinkProduct() == 1){
        Product linkProduct = productRepository.findOne(productSecKill.getLinkProduct().getId());
        if (linkProduct == null){
//          throw new Exception();
        }
        productSecKill_db.setLinkProduct(productSecKill.getLinkProduct());
      }
      productSecKillRepository.saveAndFlush(productSecKill_db);


      if (delSpecList != null && delSpecList.size() > 0) {
        for (ProductSpec spec : delSpecList) {
          ProductSpec DBSpec = productSpecRepository.findOne(spec.getId());
          if (DBSpec == null) {
            throw new RuntimeException();
          }
          DBSpec.setState(0);
          productSpecRepository.save(DBSpec);
        }
      }
      if (delDetailList != null && delDetailList.size() > 0) {
        for (ProductDetail detail : delDetailList) {
          productDetailRepository.delete(detail);
        }
      }
      if (delScrollList != null && delScrollList.size() > 0) {
        for (ScrollPicture scroll : delScrollList) {
          scrollPictureRepository.delete(scroll);
        }
      }

      if (specList != null && specList.size() > 0) {
        for (ProductSpec spec : specList) {
          ProductSpec DBSpec = null;
          if (spec.getId() == null) {
            DBSpec = new ProductSpec();
          } else {
            DBSpec = productSpecRepository.findOne(spec.getId());
            if (DBSpec == null) {
              throw new RuntimeException();
            }
          }

          DBSpec.setProduct(DBProduct);
          DBSpec.setPicture("2");
          DBSpec.setToPartner(spec.getToPartner());
          DBSpec.setToMerchant(spec.getToMerchant());
          DBSpec.setPrice(spec.getPrice());
          DBSpec.setMinPrice(spec.getMinPrice());
          DBSpec.setMinScore(spec.getMinScore());
          DBSpec.setRepository(spec.getRepository());
          DBSpec.setSpecDetail(spec.getSpecDetail());
          productSpecRepository.save(DBSpec);
        }
      }
      if (detailList != null && detailList.size() > 0) {
        for (ProductDetail detail : detailList) {
          ProductDetail DBDetail = null;
          if (detail.getId() == null) {
            DBDetail = new ProductDetail();
          } else {
            DBDetail = productDetailRepository.findOne(detail.getId());
            if (DBDetail == null) {
              throw new RuntimeException();
            }
          }
          DBDetail.setProduct(DBProduct);
          DBDetail.setPicture(detail.getPicture());
          DBDetail.setSid(detail.getSid());
          productDetailRepository.save(DBDetail);
        }
      }
      if (scrollList != null && scrollList.size() > 0) {
        for (ScrollPicture scroll : scrollList) {
          ScrollPicture DBScroll = null;
          if (scroll.getId() == null) {
            DBScroll = new ScrollPicture();
          } else {
            DBScroll = scrollPictureRepository.findOne(scroll.getId());
            if (DBScroll == null) {
              throw new RuntimeException();
            }
          }
          DBScroll.setProduct(DBProduct);
          DBScroll.setSid(scroll.getSid());
          DBScroll.setPicture(scroll.getPicture());
          scrollPictureRepository.save(DBScroll);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception();
    }
  }


}
