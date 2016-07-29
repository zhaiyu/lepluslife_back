package com.jifenke.lepluslive.user.service;

import com.jifenke.lepluslive.merchant.domain.entities.City;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.service.CityService;
import com.jifenke.lepluslive.partner.domain.entities.Partner;
import com.jifenke.lepluslive.user.domain.criteria.LeJiaUserCriteria;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import com.jifenke.lepluslive.user.domain.entities.WeiXinUser;
import com.jifenke.lepluslive.user.repository.LeJiaUserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by wcg on 16/4/22.
 */
@Service
@Transactional(readOnly = true)
public class UserService {

  @Inject
  private LeJiaUserRepository leJiaUserRepository;

  @Inject
  private CityService cityService;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public LeJiaUser findUserById(Long id) {
    return leJiaUserRepository.findOne(id);
  }


  public Page findUserByPage(Integer offset) {
    if (offset == null) {
      offset = 1;
    }
    Sort sort = new Sort(Sort.Direction.DESC, "createDate");
    return leJiaUserRepository.findAll(new PageRequest(offset - 1, 10, sort));
  }

  public Page findLeJiaUserByPage(LeJiaUserCriteria leJiaUserCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "createDate");
    return leJiaUserRepository
        .findAll(getWhereClause(leJiaUserCriteria),
                 new PageRequest(leJiaUserCriteria.getOffset() - 1, limit, sort));
  }

  public Page findAllLeJiaUserByCriteria(LeJiaUserCriteria leJiaUserCriteria) {

    return leJiaUserRepository
        .findAll(getWhereClause(leJiaUserCriteria), new PageRequest(0, 15000));
  }

  public Specification<LeJiaUser> getWhereClause(LeJiaUserCriteria userCriteria) {

    return new Specification<LeJiaUser>() {
      @Override
      public Predicate toPredicate(Root<LeJiaUser> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (userCriteria.getUserSid() != null && (!""
            .equals(userCriteria.getUserSid()))) {    //会员ID
          predicate.getExpressions().add(
              cb.like(r.get("userSid"),
                      "%" + userCriteria.getUserSid() + "%"));

        }
        if (userCriteria.getNickname() != null && (!""
            .equals(userCriteria.getNickname()))) {    //会员昵称
          predicate.getExpressions().add(
              cb.like(r.<WeiXinUser>get("weiXinUser").get("nickname"),
                      "%" + userCriteria.getNickname() + "%"));
        }
        if (userCriteria.getPhoneNumber() != null && (!""
            .equals(userCriteria.getPhoneNumber()))) { //会员手机号
          predicate.getExpressions().add(
              cb.like(r.get("phoneNumber"),
                      "%" + userCriteria.getPhoneNumber() + "%"));
        }
        if (userCriteria.getMerchant() != null && (!""
            .equals(userCriteria.getMerchant()))) {  //绑定商户名称或ID
          if (userCriteria.getMerchant().matches("^\\d{1,6}$")) {
            predicate.getExpressions().add(
                cb.like(r.<Merchant>get("bindMerchant").get("merchantSid"),
                        "%" + userCriteria.getMerchant() + "%"));
          } else {
            predicate.getExpressions().add(
                cb.like(r.<Merchant>get("bindMerchant").get("name"),
                        "%" + userCriteria.getMerchant() + "%"));
          }
        }
        if (userCriteria.getPartner() != null && (!""
            .equals(userCriteria.getPartner()))) {  //绑定合伙人名称
          predicate.getExpressions().add(
              cb.like(r.<Partner>get("bindPartner").get("name"),
                      "%" + userCriteria.getPartner() + "%"));
        }

        if (userCriteria.getMassRemain() != null) {  //本月群发余数
          if (userCriteria.getMassRemain() == 5) {
            predicate.getExpressions().add(
                cb.gt(r.<WeiXinUser>get("weiXinUser").get("massRemain"), 0));
          } else {
            predicate.getExpressions().add(
                cb.equal(r.<WeiXinUser>get("weiXinUser").get("massRemain"),
                         userCriteria.getMassRemain()));
          }
        }

        if (userCriteria.getUserType() != null) {  //会员类型
          if (userCriteria.getUserType() == 0) {
            predicate.getExpressions().add(
                cb.equal(r.<WeiXinUser>get("weiXinUser").get("state"), 0));
          } else if (userCriteria.getUserType() == 1) {
            predicate.getExpressions().add(
                cb.notEqual(r.<WeiXinUser>get("weiXinUser").get("state"), 0));
          }
        }

        //所在城市
        if (userCriteria.getCity() != null) {
          City city = cityService.findCityById(userCriteria.getCity());
          if (city != null) {
            predicate.getExpressions()
                .add(cb.or(cb.equal(r.<WeiXinUser>get("weiXinUser").get("city"),
                                    city.getEnName()),
                           cb.equal(r.<WeiXinUser>get("weiXinUser").get("city"),
                                    city.getName())));
          }
        }

        //所在省份
        if (userCriteria.getProvince() != null) {
          City city = cityService.findCityById(userCriteria.getProvince());
          if (city != null) {
            predicate.getExpressions()
                .add(cb.or(cb.equal(r.<WeiXinUser>get("weiXinUser").get("province"),
                                    city.getEnProvince()),
                           cb.equal(r.<WeiXinUser>get("weiXinUser").get("province"),
                                    city.getProvince())));
          }
        }

        if (userCriteria.getSubState() != null) {  //关注状态
          if (userCriteria.getSubState() == 1) {
            predicate.getExpressions().add(
                cb.equal(r.<WeiXinUser>get("weiXinUser").get("subState"), 1));
          } else if (userCriteria.getSubState() == 0) {
            predicate.getExpressions().add(
                cb.notEqual(r.<WeiXinUser>get("weiXinUser").get("subState"), 1));
          }
        }

        if (userCriteria.getStartDate() != null && (!""
            .equals(userCriteria.getStartDate()))) { //创建时间
          predicate.getExpressions().add(
              cb.between(r.get("createDate"), new Date(userCriteria.getStartDate()),
                         new Date(userCriteria.getEndDate())));
        }

        return predicate;
      }
    };
  }

}
