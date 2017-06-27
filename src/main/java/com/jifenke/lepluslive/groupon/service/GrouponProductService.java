package com.jifenke.lepluslive.groupon.service;

import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.groupon.controller.dto.GrouponProductDto;
import com.jifenke.lepluslive.groupon.domain.criteria.GrouponProductCriteria;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponMerchant;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponProduct;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponProductDetail;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponScrollPicture;
import com.jifenke.lepluslive.groupon.repository.GrouponMerchantRepository;
import com.jifenke.lepluslive.groupon.repository.GrouponProductDetailRepository;
import com.jifenke.lepluslive.groupon.repository.GrouponProductRepository;
import com.jifenke.lepluslive.groupon.repository.GrouponScrollPictureRepository;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.merchant.repository.MerchantRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantUserRepository;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 团购产品 Service
 * Created by xf on 17-6-16.
 */
@Service
@Transactional(readOnly = true)
public class GrouponProductService {
    @Inject
    private GrouponProductRepository grouponProductRepository;
    @Inject
    private MerchantUserRepository merchantUserRepository;
    @Inject
    private MerchantRepository merchantRepository;
    @Inject
    private GrouponMerchantRepository grouponMerchantRepository;
    @Inject
    private GrouponProductDetailRepository grouponProductDetailRepository;
    @Inject
    private GrouponScrollPictureRepository grouponScrollPictureRepository;

    /***
     *  根据条件查询团购产品
     *  Created by xf on 2017-06-16.
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Page<GrouponProduct> findByCriteria(GrouponProductCriteria criteria, Integer limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        return grouponProductRepository.findAll(getWhereClause(criteria), new PageRequest(criteria.getOffset() - 1, limit, sort));
    }

    public static Specification<GrouponProduct> getWhereClause(GrouponProductCriteria criteria) {
        return new Specification<GrouponProduct>() {
            @Override
            public Predicate toPredicate(Root<GrouponProduct> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (criteria.getMerchantUser() != null) {
                    // 商户
                    if (criteria.getMerchantUser().matches("^\\d{1,6}$")) {
                        predicate.getExpressions().add(
                                cb.equal(root.<Merchant>get("merchantUser").get("id"), criteria.getMerchantUser()));
                    } else {
                        predicate.getExpressions().add(
                                cb.like(root.<Merchant>get("merchantUser").get("name"),
                                        "%" + criteria.getMerchantUser() + "%"));
                    }
                }
                // 团购SID
                if (criteria.getSid() != null) {
                    predicate.getExpressions().add(
                            cb.equal(root.get("sid"), criteria.getSid()));
                }
                // 团购名称
                if (criteria.getName() != null) {
                    predicate.getExpressions().add(
                            cb.like(root.get("name"), "%" + criteria.getName() + "%"));
                }
                // 团购全部状态
                if (criteria.getState() != null) {
                    predicate.getExpressions().add(
                            cb.equal(root.get("state"), criteria.getState()));
                }

                return predicate;
            }
        };
    }
    /**
     *  统计团购产品下绑定门店数
     */
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED)
    public List<Long> countMerchantByProducts(List<GrouponProduct> products) {
        List<Long> bindMerchants = new ArrayList<>();
        for (GrouponProduct product : products) {
            Long count = grouponMerchantRepository.countGrouponMerchantByGrouponProduct(product);
            bindMerchants.add(count);
        }
        return bindMerchants;
    }

    /***
     *  新建保存团购产品
     *  Created by xf on 2017-06-20.
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean saveProduct(GrouponProductDto grouponProductDto) {
        try {
            //   保存产品
            GrouponProduct product = grouponProductDto.getGrouponProduct();
            product.setState(1);
            product.setCreateDate(new Date());
            product.setSid(MvUtil.getOrderNumber());
            MerchantUser merchantUser = merchantUserRepository.findOne(product.getMerchantUser().getId());
            product.setMerchantUser(merchantUser);
            grouponProductRepository.save(product);
            //   保存产品门店对应关系
            List<Merchant> merchantList = grouponProductDto.getMerchantList();
            if (merchantList != null || merchantList.size() > 0) {
                for (Merchant merchant : merchantList) {
                    GrouponMerchant grouponMerchant = new GrouponMerchant();
                    Merchant existMerchant = merchantRepository.findOne(merchant.getId());
                    grouponMerchant.setMerchant(existMerchant);
                    grouponMerchant.setGrouponProduct(product);
                    grouponMerchantRepository.save(grouponMerchant);
                }
            }
            //   保存产品详情图
            List<GrouponProductDetail> detailList = grouponProductDto.getDelDetailList();
            for(int j=0;j<detailList.size();j++) {
                GrouponProductDetail grouponProductDetail = detailList.get(j);
                grouponProductDetail.setDescription(product.getDescription());
                grouponProductDetail.setSid(new Integer(j+1));
                grouponProductDetail.setGrouponProduct(product);
                grouponProductDetailRepository.save(grouponProductDetail);
            }
            //   保存商品轮播图
            List<GrouponScrollPicture> scorePictureList = grouponProductDto.getDelScrollList();
            for(int i=0;i<scorePictureList.size();i++) {
                GrouponScrollPicture grouponScrollPicture = scorePictureList.get(i);
                grouponScrollPicture.setDescription(product.getDescription());
                grouponScrollPicture.setSid(new Integer(i+1));
                grouponScrollPicture.setGrouponProduct(product);
                grouponScrollPictureRepository.save(grouponScrollPicture);
            }
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    /**
     *  根据 ID 查找商品
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public GrouponProduct findById(Long productId) {
        return grouponProductRepository.findOne(productId);
    }
}
