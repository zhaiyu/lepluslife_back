package com.jifenke.lepluslive.banner.service;

import com.jifenke.lepluslive.banner.domain.criteria.BannerCriteria;
import com.jifenke.lepluslive.banner.domain.entities.Banner;
import com.jifenke.lepluslive.banner.domain.entities.BannerType;
import com.jifenke.lepluslive.banner.repository.BannerRepository;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponProduct;
import com.jifenke.lepluslive.groupon.service.GrouponProductService;
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
@Transactional(readOnly = false)
public class BannerService {

  @Inject
  private BannerRepository bannerRepository;

  @Inject
  private ProductService productService;

  @Inject
  private MerchantService merchantService;

  @Inject
  private GrouponProductService grouponProductService;

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

  //新建或修改-首页管理
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public int editBannerHomePage(Banner banner) {
    Banner DBBanner = null;
    Date date = new Date();
    Long id = banner.getId();
    Integer afterType = banner.getAfterType();//后置类型  1=链接  2=商品   3=商家  4=无跳转
    try {

      if (id != null) {
        List<Banner> listb = bannerRepository.findBannerBySidAndBannerType(banner.getSid(), banner.getBannerType());
        if (listb.size() > 1){
          return 509;
        }
        DBBanner = bannerRepository.findOne(id);
        if (DBBanner == null) {
          return 404;
        }
      } else { //新建
        List<Banner> listb = bannerRepository.findBannerBySidAndBannerType(banner.getSid(), banner.getBannerType());
        if (listb.size() > 0){
          return 509;
        }
        DBBanner = new Banner();
        DBBanner.setBannerType(banner.getBannerType());
        DBBanner.setCreateDate(date);
      }

      DBBanner.setSid(banner.getSid());
      DBBanner.setPicture(banner.getPicture());
      DBBanner.setAfterType(afterType);

      if (banner.getUrl() != null) {
        DBBanner.setUrl(banner.getUrl());
      }
      if (banner.getUrlTitle() != null) {
        DBBanner.setUrlTitle(banner.getUrlTitle());
      }

      if (banner.getMerchant() != null) {
        if (banner.getMerchant().getMerchantSid() != null) {
          Merchant merchant = merchantService.findMerchantByMerchantSid(banner.getMerchant().getMerchantSid());
          if (merchant == null) {
            return 506;
          }
          DBBanner.setMerchant(merchant);
        }else{
          return 5062;
        }
      }

      if (banner.getProduct() != null) {
        if (banner.getProduct().getId() != null){
          Product product = productService.findOneProduct(banner.getProduct().getId());
          if (product == null) {
            return 505;
          }
          DBBanner.setProduct(product);
        }else {
          return 5052;
        }
      }

      if (banner.getTitle() != null) {
        DBBanner.setTitle(banner.getTitle());
      }

      //保存城市
      if (banner.getCity() != null && banner.getCity().getId() != null) {
        DBBanner.setCity(banner.getCity());
      }
      if (banner.getArea() != null && banner.getArea().getId() != null) {
        DBBanner.setArea(banner.getArea());
      }
      //appType
      if (banner.getAppType() != null) {
        DBBanner.setAppType(banner.getAppType());
      }

      DBBanner.setIntroduce(banner.getIntroduce());
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

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteBanner(Long id) {
    bannerRepository.delete(id);
  }

  private static Specification<Banner> getSMovieWhereClause(BannerCriteria criteria) {
    return new Specification<Banner>() {
      @Override
      public Predicate toPredicate(Root<Banner> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (criteria.getType() == 15) {   //banner类型
          predicate.getExpressions().add(
                  cb.or(cb.equal(r.<Banner>get("bannerType").get("id"), 15),
                          cb.equal(r.<Banner>get("bannerType").get("id"), 16)));
        }
        return predicate;
      }
    };
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Page findSMovieBannersByPage(BannerCriteria bannerCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "sid");
    return bannerRepository
            .findAll(getSMovieWhereClause(bannerCriteria),
                    new PageRequest(bannerCriteria.getOffset() - 1, limit, sort));
  }


  public void changeSMovieProductState(Long id) {
    Banner banner = bannerRepository.findOne(id);
    Banner banner1 = new Banner();
    banner1.setCreateDate(banner.getCreateDate());
    banner1.setAfterType(banner.getAfterType());
    banner1.setAlive(banner.getAlive());
    banner1.setAppType(banner.getAppType());
    banner1.setArea(banner.getArea());
    banner1.setBannerType(banner.getBannerType());
    banner1.setCity(banner.getCity());
    banner1.setIntroduce(banner.getIntroduce());
    banner1.setLastUpDate(banner.getLastUpDate());
    banner1.setMerchant(banner.getMerchant());
    banner1.setOldPicture(banner.getOldPicture());
    banner1.setPicture(banner.getPicture());
    banner1.setPrice(banner.getPrice());
    banner1.setProduct(banner.getProduct());
    banner1.setSid(banner.getSid());
    banner1.setTitle(banner.getTitle());
    banner1.setUrl(banner.getUrl());
    banner1.setUrlTitle(banner.getUrlTitle());
    if (banner.getStatus() == 1) {
      banner1.setStatus(0);
    }
    if (banner.getStatus() == 0) {
      banner1.setStatus(1);
    }
    bannerRepository.delete(banner);
    bannerRepository.save(banner1);
  }
  //新建或修改电影票banner
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public int editBannerForSmovie(Banner banner) {
    Banner DBBanner = null;
    Date date = new Date();
    Long id = banner.getId();
    Integer afterType = banner.getAfterType();
    try {
      if (id != null) {
        DBBanner = bannerRepository.findOne(id);
        if (DBBanner == null) {
          return 404;
        } else {
          DBBanner.setBannerType(banner.getBannerType());
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

  /**
   *  团购专区Banner图管理
   */
  //新建或修改-首页管理
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public int editGrouponBanner(Banner banner) {
    Banner DBBanner = null;
    Date date = new Date();
    Long id = banner.getId();
    Integer afterType = banner.getAfterType();  //后置类型  1=链接  2=商品   3=商家  4=无跳转
    try {
      if (id != null) {
        List<Banner> listb = bannerRepository.findBannerBySidAndBannerType(banner.getSid(), banner.getBannerType());
        if (listb.size() > 1){
          return 509;
        }
        DBBanner = bannerRepository.findOne(id);
        if (DBBanner == null) {
          return 404;
        }
      } else { //新建
        List<Banner> listb = bannerRepository.findBannerBySidAndBannerType(banner.getSid(), banner.getBannerType());
        if (listb.size() > 0){
          return 509;
        }
        DBBanner = new Banner();
        DBBanner.setBannerType(banner.getBannerType());
        DBBanner.setCreateDate(date);
      }
      DBBanner.setSid(banner.getSid());
      DBBanner.setPicture(banner.getPicture());
      DBBanner.setAfterType(afterType);
      if (banner.getUrl() != null) {
        DBBanner.setUrl(banner.getUrl());
      }
      if (banner.getUrlTitle() != null) {
        DBBanner.setUrlTitle(banner.getUrlTitle());
      }
      if (banner.getMerchant() != null) {
        if (banner.getMerchant().getMerchantSid() != null) {
          Merchant merchant = merchantService.findMerchantByMerchantSid(banner.getMerchant().getMerchantSid());
          if (merchant == null) {
            return 506;
          }
          DBBanner.setMerchant(merchant);
        }else{
          return 5062;
        }
      }
      if (banner.getTitle() != null) {
        DBBanner.setTitle(banner.getTitle());
      }
      //保存城市
      if (banner.getCity() != null && banner.getCity().getId() != null) {
        DBBanner.setCity(banner.getCity());
      }
      if (banner.getArea() != null && banner.getArea().getId() != null) {
        DBBanner.setArea(banner.getArea());
      }
      String productId = banner.getIntroduce();
      if(productId!=null&&!"".equals(productId)) {
        GrouponProduct product = grouponProductService.findById(new Long(productId));
        if(product!=null) {
          DBBanner.setIntroduce(productId);
        }else {
          return 505;
        }
      }
      DBBanner.setLastUpDate(date);
      bannerRepository.save(DBBanner);
    } catch (Exception e) {
      e.printStackTrace();
      return 500;
    }
    return 200;
  }
}
