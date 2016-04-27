package com.jifenke.lepluslive.product.service;

import com.jifenke.lepluslive.barcode.BarcodeConfig;
import com.jifenke.lepluslive.barcode.service.BarcodeService;
import com.jifenke.lepluslive.filemanage.service.FileImageService;
import com.jifenke.lepluslive.global.config.Constants;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.order.domain.entities.OrderDetail;
import com.jifenke.lepluslive.product.controller.dto.ProductDto;
import com.jifenke.lepluslive.product.domain.entities.Product;
import com.jifenke.lepluslive.product.domain.entities.ProductCriteria;
import com.jifenke.lepluslive.product.domain.entities.ProductDetail;
import com.jifenke.lepluslive.product.domain.entities.ProductSpec;
import com.jifenke.lepluslive.product.domain.entities.ProductType;
import com.jifenke.lepluslive.product.repository.ProductDetailRepository;
import com.jifenke.lepluslive.product.repository.ProductRepository;
import com.jifenke.lepluslive.product.repository.ProductSpecRepository;
import com.jifenke.lepluslive.product.repository.ProductTypeRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

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
  private FileImageService fileImageService;

  @Inject
  private BarcodeService barcodeService;


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Page findProductsByPage(Integer offset, ProductCriteria productCriteria) {
    Page<Product>
        page =
        productRepository.findAll(getWhereClause(productCriteria),
                                  new PageRequest(offset - 1, 10,
                                                  new Sort(Sort.Direction.ASC, "sid")));
    return page;
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
    origin.setPacketCount(productDto.getPacketCount());
    origin.setPicture(productDto.getPicture());
    origin.setPointsCount(productDto.getPointsCount());
    origin.setDescription(productDto.getDescription());
    origin.setSaleNumber(productDto.getSaleNumber());
    origin.setThumb(productDto.getThumb());
    origin.setPrice((long) Float.parseFloat(productDto.getPrice()) * 100);
    origin.setProductType(productDto.getProductType());
    for (ProductSpec productSpec : productDto.getProductSpecs()) {
      productSpec.setProduct(origin);
      productSpecRepository.save(productSpec);
    }
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
  public ProductType findOneProductType(Integer id) {
    return productTypeRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<ProductDetail> findAllProductDetailsByProduct(Product product) {
    return productDetailRepository.findAllByProduct(product);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<ProductSpec> findProductSpecsByProduct(Product product) {
    return productSpecRepository.findAllByProduct(product);
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

  public static Specification<Product> getWhereClause(ProductCriteria productCriteria) {
    return new Specification<Product>() {
      @Override
      public Predicate toPredicate(Root<Product> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        predicate.getExpressions().add(
            cb.equal(r.get("state"),
                     productCriteria.getState()));
        if (productCriteria.getProductType() != null) {
          predicate.getExpressions().add(
              cb.equal(r.<ProductType>get("productType"), productCriteria.getProductType()));
        }
        return predicate;
      }
    };
  }

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
}
