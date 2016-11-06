package com.jifenke.lepluslive.product.service;

import com.jifenke.lepluslive.barcode.BarcodeConfig;
import com.jifenke.lepluslive.barcode.service.BarcodeService;
import com.jifenke.lepluslive.filemanage.service.FileImageService;
import com.jifenke.lepluslive.global.config.Constants;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.order.domain.entities.OrderDetail;
import com.jifenke.lepluslive.product.controller.dto.LimitProductDto;
import com.jifenke.lepluslive.product.controller.dto.ProductDto;
import com.jifenke.lepluslive.product.domain.entities.Product;
import com.jifenke.lepluslive.product.domain.entities.ProductCriteria;
import com.jifenke.lepluslive.product.domain.entities.ProductDetail;
import com.jifenke.lepluslive.product.domain.entities.ProductSpec;
import com.jifenke.lepluslive.product.domain.entities.ProductType;
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

import java.io.IOException;
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
 * Created by wcg on 16/3/9.
 */
@Service
@Transactional(readOnly = true)
public class ProductService {

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
  private FileImageService fileImageService;

  @Inject
  private BarcodeService barcodeService;

  @Inject
  private ScrollPictureRepository scrollPictureRepository;

  /**
   * 普通商品分页条件查询  16/11/03
   *
   * @param criteria 查询条件
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Map findCommonProductByPage(ProductCriteria criteria) {
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
      pro.put("typeName", product.getProductType().getType());
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

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteProduct(Long id) throws Exception {

    productRepository.delete(id);

  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Product findOneProduct(Long id) {
    return productRepository.findOne(id);
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editProduct(ProductDto productDto) throws Exception {
    Product origin = null;
    if (productDto.getId() != null) {
      origin = productRepository.findOne(productDto.getId());
    } else {
      origin = new Product();
      origin.setState(1);
    }
    origin.setSid(productDto.getSid());
    origin.setName(productDto.getName());
    float minPrice = Float.parseFloat(productDto.getMinPrice());
    origin.setMinPrice((long) (minPrice * 100));
    origin.setPicture(productDto.getPicture());
    origin.setDescription(productDto.getDescription());
    origin.setThumb(productDto.getThumb());
    origin.setPrice((long) (Float.parseFloat(productDto.getPrice()) * 100));
    origin.setCustomSale(productDto.getCustomSale());
    origin.setProductType(productDto.getProductType());

    productRepository.save(origin);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public LejiaResult putOnProduct(Long id) {
    Product product = productRepository.findOne(id);
    if (product == null) {
      return LejiaResult.build(500, "商品不存在");
    }
    product.setState(1);
    productRepository.save(product);
    return LejiaResult.build(200, "成功上架商品");
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public LejiaResult pullOffProduct(Long id) {
    Product product = productRepository.findOne(id);
    if (product == null) {
      return LejiaResult.build(500, "商品不存在");
    }
    product.setState(0);
    productRepository.save(product);
    return LejiaResult.build(200, "成功下架商品");
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Long getTotalCount() {
    return productRepository.getTotalCount();
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editProductType(ProductType productType) {
    ProductType productTypeOri = productTypeRepository.findOne(productType.getId());
    productTypeOri.setType(productType.getType());
    productTypeRepository.save(productTypeOri);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<ProductType> findAllProductType() {
    return productTypeRepository.findAll();
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteProductType(Integer id) {
    productTypeRepository.delete(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void addProductType(ProductType productType) {
    productTypeRepository.save(productType);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<ProductDetail> findAllProductDetailsByProduct(Product product) {
    return productDetailRepository.findAllByProduct(product);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void orderCancle(List<OrderDetail> orderDetails) {
    for (OrderDetail orderDetail : orderDetails) {
      ProductSpec productSpec = orderDetail.getProductSpec();
      Integer repository = productSpec.getRepository() + orderDetail.getProductNumber();
      productSpec.setRepository(repository);
      productSpecRepository.save(productSpec);
    }
  }

  /**
   * 爆品列表 16/09/18
   *
   * @param criteria 条件
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Map findLimitProductByPage(ProductCriteria criteria) {
    Page<Product>
        page =
        productRepository.findAll(getWhereClause(criteria),
                                  new PageRequest(criteria.getOffset() - 1, 10,
                                                  new Sort(Sort.Direction.ASC, "id")));
    List<Product> list = page.getContent();
    Map<String, Object> result = new HashMap<>();
    List<Map> productList = new ArrayList<>();
    result.put("totalPages", page.getTotalPages());
    result.put("totalElements", page.getTotalElements());
    for (Product product : list) {
      Map<String, Object> pro = new HashMap<>();
      pro.put("id", product.getId());
      pro.put("name", product.getName());
      pro.put("minPrice", product.getMinPrice());
      pro.put("minScore", product.getMinScore());
      pro.put("state", product.getState());
      pro.put("saleNumber", product.getSaleNumber());
      pro.put("thumb", product.getThumb());
      pro.put("hotStyle", product.getHotStyle());

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
        if (productCriteria.getProductType() != null) {
          predicate.getExpressions().add(
              cb.equal(r.<ProductType>get("productType"), productCriteria.getProductType()));
        }
        if (productCriteria.getStartDate() != null && (!""
            .equals(productCriteria.getStartDate()))) { //创建时间
          predicate.getExpressions().add(
              cb.between(r.get("createDate"), new Date(productCriteria.getStartDate()),
                         new Date(productCriteria.getEndDate())));
        }
        if (productCriteria.getMark() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("mark").get("id"), productCriteria.getMark()));
        }
        if (productCriteria.getMinTruePrice() != null) {
          predicate.getExpressions().add(
              cb.greaterThanOrEqualTo(r.get("minPrice"), productCriteria.getMinTruePrice()));
        }
        if (productCriteria.getMaxTruePrice() != null) {
          predicate.getExpressions().add(
              cb.lessThanOrEqualTo(r.get("minPrice"), productCriteria.getMaxTruePrice()));
        }
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

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public String qrCodeManage(Long id) {

    Product product = productRepository.findOne(id);

    if (product.getQrCodePicture() == null) {
      byte[]
          bytes =
          new byte[0];
      try {
        bytes = barcodeService.qrCode(Constants.PRODUCT_URL + id,
                                      BarcodeConfig.QRCode.defaultConfig());
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      String filePath = MvUtil.getFilePath(Constants.BAR_CODE_EXT);
      fileImageService.SaveBarCode(bytes, filePath);

      product.setQrCodePicture(barCodeRootUrl + "/" + filePath);

      productRepository.save(product);
    }

    return product.getQrCodePicture();

  }

  /**
   * 上架或下架 16/09/18
   *
   * @param id 商品ID
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void changeState(Long id) {
    Product p = productRepository.findOne(id);
    if (p == null) {
      throw new RuntimeException("不存在的商品");
    }
    p.setState(1 - p.getState());
    productRepository.save(p);
  }

  /**
   * 爆款设置 16/09/19
   *
   * @param id    商品id
   * @param thumb 爆款图片(type=1是必须)
   * @param type  1=设为爆款|0=取消爆款
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void changeHot(Long id, String thumb, Integer type) throws Exception {
    Product p = productRepository.findOne(id);
    try {
      if (p == null) {
        throw new RuntimeException("不存在的商品");
      }
      if (type == 1) {
        p.setThumb(thumb);
        p.setHotStyle(1);
      } else {
        p.setHotStyle(0);
      }
      productRepository.save(p);
    } catch (Exception e) {
      throw new Exception();
    }
  }

  /**
   * 保存秒杀商品 16/09/19
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveLimitProduct(LimitProductDto limitProductDto) throws Exception {

    Product product = limitProductDto.getProduct();
    List<ProductSpec> specList = limitProductDto.getProductSpecList();
    List<ProductDetail> detailList = limitProductDto.getProductDetailList();
    List<ScrollPicture> scrollList = limitProductDto.getScrollPictureList();
    List<ProductSpec> delSpecList = limitProductDto.getDelSpecList();
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
      DBProduct.setState(1);
      DBProduct.setType(2);
      DBProduct.setBuyLimit(product.getBuyLimit());
      DBProduct.setMinPrice(product.getMinPrice());
      DBProduct.setMinScore(product.getMinScore());
      DBProduct.setName(product.getName());
      DBProduct.setPrice(product.getPrice());
      DBProduct.setPostage(product.getPostage());
      DBProduct.setCustomSale(product.getCustomSale());
      DBProduct.setPicture(product.getPicture());
      productRepository.save(DBProduct);
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

  /**
   * 保存普通商品 16/11/03
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveCommonProduct(LimitProductDto limitProductDto) throws Exception {

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
      DBProduct.setMark(product.getMark());
      DBProduct.setState(1);
      DBProduct.setType(1);
      DBProduct.setMinPrice(product.getMinPrice());
      DBProduct.setMinScore(product.getMinScore());
      DBProduct.setName(product.getName());
      DBProduct.setPrice(product.getPrice());
      DBProduct.setPostage(product.getPostage());
      DBProduct.setFreePrice(product.getFreePrice());
      DBProduct.setCustomSale(product.getCustomSale());
      DBProduct.setPicture(product.getPicture());
      DBProduct.setThumb(product.getThumb());
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

  //  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
//  public ProductType findOneProductType(Integer id) {
//    return productTypeRepository.findOne(id);
//  }

//  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
//  public List<ProductSpec> findProductSpecsByProduct(Product product) {
//    return productSpecRepository.findAllByProduct(product);
//  }
}
