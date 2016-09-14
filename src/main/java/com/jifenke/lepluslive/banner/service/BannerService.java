package com.jifenke.lepluslive.banner.service;

import com.jifenke.lepluslive.banner.domain.criteria.BannerCriteria;
import com.jifenke.lepluslive.banner.domain.entities.Banner;
import com.jifenke.lepluslive.banner.domain.entities.BannerType;
import com.jifenke.lepluslive.banner.repository.BannerRepository;
import com.jifenke.lepluslive.merchant.domain.entities.City;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.product.domain.entities.Product;
import com.jifenke.lepluslive.product.service.ProductService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * app广告轮播 Created by zhangwen on 16/8/26.
 */
@Service
@Transactional(readOnly = true)
public class BannerService {

  @Inject
  private BannerRepository bannerRepository;

  @Inject
  private ProductService productService;

  @Inject
  private MerchantService merchantService;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Banner findById(Long id) {
    return bannerRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Page findBannersByPage(BannerCriteria bannerCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.ASC, "sid");
    return bannerRepository
        .findAll(getWhereClause(bannerCriteria),
                 new PageRequest(bannerCriteria.getOffset() - 1, limit, sort));
  }

  //某个城市对应的热门关键词集合
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<Banner> findByCityOrderByCreateDateDesc(City city) {
    return bannerRepository.findByCityOrderByCreateDateDesc(city);
  }

  //修改某个城市对应的热门关键词
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editHotWord(List<Banner> list) throws Exception {
    City city = null;
    int i = 0;
    Date date = new Date();
    BannerType bannerType = new BannerType(7L);
    if (list != null && list.size() > 0) {
      city = list.get(0).getCity();
      i = 1;
    }
    try {
      //获取已有的关键词集合
      List<Banner> DBList = bannerRepository.findByCityOrderByCreateDateDesc(city);
      //删除关键词
      for (Banner DBBanner : DBList) {
        if (i == 1) {
          int j = 0;  //不存在
          for (Banner banner : list) {
            if (banner.getId() != null) {
              if (DBBanner.getId().longValue() == banner.getId()) {
                j = 1;  //存在
                break;
              }
            }
          }
          if (j == 0) { //删除关键词
            bannerRepository.delete(DBBanner);
          }
        } else {
          bannerRepository.delete(DBBanner);
        }
      }
      //新增关键词
      if (i == 1) {
        for (Banner banner : list) {
          if (banner.getId() == null) {
            Banner newBanner = new Banner();
            newBanner.setBannerType(bannerType);
            newBanner.setCreateDate(date);
            newBanner.setLastUpDate(date);
            newBanner.setPicture("7");
            newBanner.setCity(banner.getCity());
            newBanner.setTitle(banner.getTitle());
            bannerRepository.save(newBanner);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static Specification<Banner> getWhereClause(BannerCriteria criteria) {
    return new Specification<Banner>() {
      @Override
      public Predicate toPredicate(Root<Banner> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (criteria.getType() != null) {   //banner类型
          predicate.getExpressions().add(
              cb.equal(r.<BannerType>get("bannerType").get("id"), criteria.getType()));
        }

        if (criteria.getStatus() != null) {   //上架、下架
          predicate.getExpressions().add(
              cb.equal(r.get("status"), criteria.getStatus()));
        }

        if (criteria.getAlive() != null) {   //当期、往期
          predicate.getExpressions().add(
              cb.equal(r.get("alive"), criteria.getAlive()));
        }

        if (criteria.getCity() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("merchant").get("city"),
                       new City(criteria.getCity())));
        }

        if (criteria.getStartDate() != null && (!"".equals(criteria.getStartDate()))) {
          predicate.getExpressions().add(
              cb.between(r.get("createDate"), new Date(criteria.getStartDate()),
                         new Date(criteria.getEndDate())));
        }

        return predicate;
      }
    };
  }

  //新建或修改
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public int editBanner(Banner banner) {
    Banner DBBanner = null;
    Date date = new Date();
    Long id = banner.getId();
    Integer afterType = banner.getAfterType();
    try {
      if (id != null) {
        DBBanner = bannerRepository.findOne(id);
        if (DBBanner == null) {
          return 404;
        }
      } else { //新建
        DBBanner = new Banner();
        DBBanner.setBannerType(banner.getBannerType());
        DBBanner.setCreateDate(date);
      }
      DBBanner.setSid(banner.getSid());
      DBBanner.setPicture(banner.getPicture());
      DBBanner.setAfterType(afterType);
      if (afterType == 1) {//后置类型  1=链接  2=商品   3=商家
        DBBanner.setUrl(banner.getUrl());
        DBBanner.setUrlTitle(banner.getUrlTitle());
        if (banner.getMerchant() != null) {
          if (banner.getMerchant().getMerchantSid() != null) {
            Merchant
                merchant =
                merchantService.findMerchantByMerchantSid(banner.getMerchant().getMerchantSid());
            if (merchant == null) {
              return 506;
            }
            DBBanner.setMerchant(merchant);
          }
        }
      } else if (afterType == 2) {
        Product product = productService.findOneProduct(banner.getProduct().getId());
        if (product == null) {
          return 505;
        }
        DBBanner.setProduct(product);
      } else if (afterType == 3) {
        Merchant
            merchant =
            merchantService.findMerchantByMerchantSid(banner.getMerchant().getMerchantSid());
        if (merchant == null) {
          return 506;
        }
        DBBanner.setMerchant(merchant);
      }

      if (banner.getIntroduce() != null) {
        DBBanner.setIntroduce(banner.getIntroduce());
      }
      if (banner.getTitle() != null) {
        DBBanner.setTitle(banner.getTitle());
      }
      if (banner.getPrice() != null) {
        DBBanner.setPrice(banner.getPrice());
      }
      if (banner.getOldPicture() != null) {
        DBBanner.setOldPicture(banner.getOldPicture());
      }
      DBBanner.setLastUpDate(date);
      bannerRepository.save(DBBanner);
    } catch (Exception e) {
      e.printStackTrace();
      return 500;
    }
    return 200;
  }

  //上架或下架
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void changeStatus(Long id) {
    Banner banner = bannerRepository.findOne(id);
    if (banner == null) {
      throw new RuntimeException("不存在的轮播图");
    }
    banner.setStatus(1 - banner.getStatus());
    banner.setLastUpDate(new Date());
    bannerRepository.save(banner);
  }

  //当期或往期
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void changeAlive(Long id) {
    Banner banner = bannerRepository.findOne(id);
    if (banner == null) {
      throw new RuntimeException("不存在的轮播图");
    }
    banner.setAlive(1 - banner.getAlive());
    banner.setLastUpDate(new Date());
    bannerRepository.save(banner);
  }

}
