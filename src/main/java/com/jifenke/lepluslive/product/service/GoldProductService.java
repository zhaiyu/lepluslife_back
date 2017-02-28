package com.jifenke.lepluslive.product.service;

import com.jifenke.lepluslive.product.controller.dto.LimitProductDto;
import com.jifenke.lepluslive.product.domain.entities.Product;
import com.jifenke.lepluslive.product.domain.entities.ProductCriteria;
import com.jifenke.lepluslive.product.domain.entities.ProductDetail;
import com.jifenke.lepluslive.product.domain.entities.ProductSpec;
import com.jifenke.lepluslive.product.domain.entities.ScrollPicture;
import com.jifenke.lepluslive.product.repository.ProductDetailRepository;
import com.jifenke.lepluslive.product.repository.ProductRepository;
import com.jifenke.lepluslive.product.repository.ProductSpecRepository;
import com.jifenke.lepluslive.product.repository.ProductTypeRepository;
import com.jifenke.lepluslive.product.repository.ScrollPictureRepository;

import org.springframework.beans.factory.annotation.Value;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * 金币商品 Created by zhangwen on 17/2/20.
 */
@Service
@Transactional(readOnly = true)
public class GoldProductService {

  @Value("${bucket.ossBarCodeReadRoot}")
  private String barCodeRootUrl;

  @Inject
  private ProductRepository productRepository;

  @Inject
  private ProductTypeRepository productTypeRepository;

  @Inject
  private ProductDetailRepository productDetailRepository;

  @Inject
  private ProductSpecRepository productSpecRepository;

  @Inject
  private ProductSpecService productSpecService;


  @Inject
  private ScrollPictureRepository scrollPictureRepository;

  /**
   * 金币商品分页条件查询  17/2/20
   *
   * @param criteria 查询条件
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Map findGoldProductByPage(ProductCriteria criteria) {
    String order = "id";
    Sort sort = null;
    if (criteria.getOrderBy() != null) {
      switch (criteria.getOrderBy()) {
        case 1:
          order = "createDate";
          break;
        case 2:
          order = "sid";
          break;
        case 3:
          order = "minPrice";
          break;
        case 4:
          order = "minScore";
          break;
        case 5:
          order = "saleNumber";
          break;
        case 6:
          order = "lastUpdate";
          break;
        default:
          break;
      }
    }

    if (criteria.getDesc() == null || criteria.getDesc() == 1) {
      sort = new Sort(Sort.Direction.DESC, order);
    } else {
      sort = new Sort(Sort.Direction.ASC, order);
    }
    Page<Product>
        page =
        productRepository.findAll(getWhereClause(criteria),
                                  new PageRequest(criteria.getOffset() - 1, 10, sort));
    List<Product> list = page.getContent();
    Map<String, Object> result = new HashMap<>();
    List<Map> productList = new ArrayList<>();
    result.put("totalPages", page.getTotalPages());
    result.put("totalElements", page.getTotalElements());
    for (Product product : list) {
      Map<String, Object> pro = new HashMap<>();
//      pro.put("typeName", product.getProductType().getType());
      pro.put("sid", product.getSid());
      pro.put("id", product.getId());
      pro.put("name", product.getName());
      pro.put("thumb", product.getThumb());
      pro.put("picture", product.getPicture());
      pro.put("minPrice", product.getMinPrice());
      pro.put("minScore", product.getMinScore());
      pro.put("price", product.getPrice());
      pro.put("state", product.getState());
      pro.put("saleNumber", product.getSaleNumber());

      //库存
      List<ProductSpec> specList = productSpecService.findProductSpecsByProduct(product);
      int repository = 0;
      for (ProductSpec spec : specList) {
        if (spec.getState() == 1) {
          repository += spec.getRepository();
        }
      }
      pro.put("repository", repository);
      productList.add(pro);
    }
    result.put("productList", productList);
    return result;
  }

  public static Specification<Product> getWhereClause(ProductCriteria productCriteria) {
    return new Specification<Product>() {
      @Override
      public Predicate toPredicate(Root<Product> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        predicate.getExpressions().add(
            cb.equal(r.get("state"), productCriteria.getState()));
        if (productCriteria.getType() != null) {
          predicate.getExpressions().add(cb.equal(r.get("type"), productCriteria.getType()));
        }
//        if (productCriteria.getProductType() != null) {
//          predicate.getExpressions().add(
//              cb.equal(r.<ProductType>get("productType"), productCriteria.getProductType()));
//        }
        if (productCriteria.getStartDate() != null && (!""
            .equals(productCriteria.getStartDate()))) { //创建时间
          predicate.getExpressions().add(
              cb.between(r.get("createDate"), new Date(productCriteria.getStartDate()),
                         new Date(productCriteria.getEndDate())));
        }
//        if (productCriteria.getMark() != null) {
//          predicate.getExpressions().add(
//              cb.equal(r.get("mark").get("id"), productCriteria.getMark()));
//        }
//        if (productCriteria.getMinTruePrice() != null) {
//          predicate.getExpressions().add(
//              cb.greaterThanOrEqualTo(r.get("minPrice"), productCriteria.getMinTruePrice()));
//        }
//        if (productCriteria.getMaxTruePrice() != null) {
//          predicate.getExpressions().add(
//              cb.lessThanOrEqualTo(r.get("minPrice"), productCriteria.getMaxTruePrice()));
//        }
        if (productCriteria.getName() != null && (!""
            .equals(productCriteria.getName()))) {
          predicate.getExpressions().add(
              cb.like(r.get("name"), "%" + productCriteria.getName() + "%"));
        }
        if (productCriteria.getPostage() != null) {
          if (productCriteria.getPostage() == 0) { //包邮
            predicate.getExpressions().add(cb.equal(r.get("postage"), 0));
          } else {
            predicate.getExpressions().add(cb.notEqual(r.get("postage"), 0));
          }
        }
        return predicate;
      }
    };
  }


  /**
   * 保存金币商品 17/2/20
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveGoldProduct(LimitProductDto limitProductDto) throws Exception {

    Product product = limitProductDto.getProduct();

    List<ProductDetail> detailList = limitProductDto.getProductDetailList();
    List<ScrollPicture> scrollList = limitProductDto.getScrollPictureList();
    List<ProductDetail> delDetailList = limitProductDto.getDelDetailList();
    List<ScrollPicture> delScrollList = limitProductDto.getDelScrollList();
    try {
      Product DBProduct = null;
      if (product.getId() == null) {
        DBProduct = new Product();
      } else {
        DBProduct = productRepository.findOne(product.getId());
        if (DBProduct == null) {
          throw new RuntimeException();
        }
      }
      DBProduct.setSid(product.getSid());
      DBProduct.setProductType(product.getProductType());
//      DBProduct.setMark(product.getMark());
      DBProduct.setState(1);
      DBProduct.setType(4);
      DBProduct.setLastUpdate(new Date());
      DBProduct.setMinPrice(product.getMinPrice());
      DBProduct.setMinScore(product.getMinScore());
      DBProduct.setName(product.getName());
      DBProduct.setDescription(product.getDescription());
      DBProduct.setPrice(product.getPrice());
      DBProduct.setPostage(product.getPostage());
      DBProduct.setFreePrice(product.getFreePrice());
      DBProduct.setCustomSale(product.getCustomSale());
      DBProduct.setPicture(product.getPicture());
      DBProduct.setThumb(product.getThumb());
      if (product.getIsBackRed() != null) {
        DBProduct.setIsBackRed(product.getIsBackRed());
      }
      if (product.getBackRedType() != null) {
        DBProduct.setBackRedType(product.getBackRedType());
      }
      if (product.getBackRatio() != null) {
        DBProduct.setBackRatio(product.getBackRatio());
      }
      if (product.getBackMoney() != null) {
        DBProduct.setBackMoney(product.getBackMoney());
      }
      productRepository.save(DBProduct);

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
