package com.jifenke.lepluslive.activity.service;

import com.jifenke.lepluslive.activity.domain.entities.ActivityCodeBurse;
import com.jifenke.lepluslive.activity.repository.ActivityCodeBurseRepository;

import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.weixin.domain.entities.WeiXinQrCode;
import com.jifenke.lepluslive.weixin.repository.WeiXinQrCodeRepository;
import com.jifenke.lepluslive.weixin.service.WeiXinService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by wcg on 16/3/17.
 */
@Service
@Transactional(readOnly = true)
public class ActivityCodeBurseService {

  @Inject
  private ActivityCodeBurseRepository activityCodeBurseRepository;

  @Inject
  private WeiXinService weiXinService;

  @Inject
  private WeiXinQrCodeRepository weiXinQrCodeRepository;

  @Value("${bucket.ossBarCodeReadRoot}")
  private String barCodeRootUrl;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Page findByPage(Integer offset, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "createDate");
    return activityCodeBurseRepository.findAll(getWhereClause(),
                                               new PageRequest(offset - 1, limit, sort));
  }

  public Specification<ActivityCodeBurse> getWhereClause() {

    return new Specification<ActivityCodeBurse>() {
      @Override
      public Predicate toPredicate(Root<ActivityCodeBurse> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        predicate.getExpressions().add(
            cb.equal(r.get("type"), 1));

        return predicate;
      }
    };
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public ActivityCodeBurse findCodeBurseById(Long id) {
    return activityCodeBurseRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void changeState(Long id) {
    ActivityCodeBurse codeBurse = activityCodeBurseRepository.findOne(id);
    if (codeBurse == null) {
      throw new RuntimeException("不存在的活动");
    }
    if (codeBurse.getState() == 1) {
      codeBurse.setState(0);
    } else {
      codeBurse.setState(1);
    }

    activityCodeBurseRepository.save(codeBurse);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public int editCodeBurse(ActivityCodeBurse codeBurse) {
    ActivityCodeBurse activityCodeBurse = null;
    Long id = codeBurse.getId();
    try {
      if (id != null) {
        activityCodeBurse = findCodeBurseById(id);
        if (activityCodeBurse == null) {
          return 404;
        }
        activityCodeBurse.setBeginDate(codeBurse.getBeginDate());
        activityCodeBurse.setEndDate(codeBurse.getEndDate());
        activityCodeBurse.setBudget(codeBurse.getBudget());
        activityCodeBurse.setSingleMoney(codeBurse.getSingleMoney());
        activityCodeBurse.setTitle(codeBurse.getTitle());
        activityCodeBurseRepository.save(activityCodeBurse);
      } else { //新建活动，创建一个对应的永久二维码
        String parameter = "Y" + MvUtil.getRandomStr();  //y永久二维码开头为“Y”
        Map map = weiXinService.createForeverQrCode(parameter);
        if (map.get("errcode") == null || Integer.valueOf(map.get("errcode").toString()) == 0) {
          codeBurse.setParameter(parameter);
          codeBurse.setTicket(map.get("ticket").toString());
          //在永久二维码表中添加记录
          WeiXinQrCode qrCode = new WeiXinQrCode();
          qrCode.setParameter(parameter);
          qrCode.setTicket(map.get("ticket").toString());
          qrCode.setType(2);
          weiXinQrCodeRepository.save(qrCode);
          activityCodeBurseRepository.save(codeBurse);
        } else {
          return 504;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      return 500;
    }
    return 200;
  }
}
