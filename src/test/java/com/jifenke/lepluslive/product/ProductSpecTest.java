//package com.jifenke.lepluslive.product;
//
//import com.jifenke.lepluslive.Application;
//import com.jifenke.lepluslive.global.config.Constants;
//import com.jifenke.lepluslive.product.controller.dto.ProductSpecDto;
//import com.jifenke.lepluslive.product.domain.entities.ProductRebatePolicy;
//import com.jifenke.lepluslive.product.domain.entities.ProductSpec;
//import com.jifenke.lepluslive.product.repository.ProductRebatePolicyRepository;
//import com.jifenke.lepluslive.product.service.ProductRebatePolicyService;
//import com.jifenke.lepluslive.product.service.ProductSpecService;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.test.IntegrationTest;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import javax.inject.Inject;
//
///**
// * Created by wcg on 16/4/15.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
//@WebAppConfiguration
//@IntegrationTest
//@ActiveProfiles({Constants.SPRING_PROFILE_DEVELOPMENT})
//public class ProductSpecTest {
//
//
//  private static final Logger log = LoggerFactory.getLogger(ProductSpecTest.class);
//
//  @Inject
//  private ProductSpecService productSpecService;
//
//  @Inject
//  private ProductRebatePolicyRepository rebatePolicyRepository;
//
//  @Test
//  public void updateRebatePolicy() throws Exception {
//
//    BigDecimal bigDecimal100 = new BigDecimal(100);
//    List<ProductRebatePolicy> list = rebatePolicyRepository.findAll();
//    for (ProductRebatePolicy policy : list) {
//      ProductSpecDto specDto = new ProductSpecDto();
//      ProductSpec spec = policy.getProductSpec();
//      specDto.setCostPrice(
//          BigDecimal.valueOf(spec.getCostPrice()).divide(bigDecimal100, 2, BigDecimal.ROUND_HALF_UP)
//              .toString());
//      specDto.setId(spec.getId());
//      specDto.setOtherPrice(BigDecimal.valueOf(spec.getOtherPrice())
//                                .divide(bigDecimal100, 2, BigDecimal.ROUND_HALF_UP)
//                                .toString());
//      specDto.setPicture(spec.getPicture());
//      specDto.setPrice(BigDecimal.valueOf(spec.getPrice())
//                           .divide(bigDecimal100, 2, BigDecimal.ROUND_HALF_UP)
//                           .toString());
//      specDto.setProduct(spec.getProduct());
//      specDto.setRepository(spec.getRepository());
//      specDto.setSpecDetail(spec.getSpecDetail());
//
//      productSpecService.editProductSpec(specDto);
//    }
//
//
//  }
//}
