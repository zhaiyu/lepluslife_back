package com.jifenke.lepluslive.merchant.service;

import com.jifenke.lepluslive.global.util.MD5Util;
import com.jifenke.lepluslive.merchant.domain.criteria.MerchantUserCriteria;
import com.jifenke.lepluslive.merchant.domain.entities.City;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantBank;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.merchant.repository.CityRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantBankRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * Created by xf on 16-11-16.
 */
@Service
@Transactional(readOnly = true)
public class MerchantUserService {

    @Inject
    private MerchantUserRepository merchantUserRepository;
    @Inject
    private CityRepository cityRepository;
    @Inject
    private MerchantBankRepository merchantBankRepository;

    /**
     * 创建子账号
     *    - 设置类型
     *    - 添加创建者
     * @param merchantUser
     */
    /*@Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public void createMerchantUser(MerchantUser merchantUser) {
        merchantUser.setType(1);
        merchantUserRepository.save(merchantUser);
    }*/

    /**
     * 创建商户管理员
     *
     * @param merchantUser
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void createMerchantUserManager(MerchantUser merchantUser) {
        City city = cityRepository.findOne(merchantUser.getCity().getId());
        merchantUser.setCity(city);
        merchantUser.setType(8);                                                 // 9-系统管理员  8-管理员(商户)
        merchantUser.setCreatedDate(new Date());
        String md5pwd = MD5Util.MD5Encode(merchantUser.getPassword(), null);     // md5加密
        merchantUser.setPassword(md5pwd);
        MerchantBank merchantBank = merchantUser.getMerchantBank();              // 保存商户银行信息
        merchantBankRepository.save(merchantBank);
        merchantUserRepository.save(merchantUser);
    }

    /**
     * 根据用户名查询商户账号
     *
     * @param name
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<MerchantUser> findByName(String name) {
        return merchantUserRepository.findByName(name);
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page findByCriteria(MerchantUserCriteria merchantUserCriteria, int limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
        PageRequest pageRequest = new PageRequest(merchantUserCriteria.getOffset() - 1, limit, sort);
        return merchantUserRepository.findAll(getWhereClause(merchantUserCriteria), pageRequest);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public static Specification<MerchantUser> getWhereClause(MerchantUserCriteria merchantUserCriteria) {
        return new Specification<MerchantUser>() {
            @Override
            public Predicate toPredicate(Root<MerchantUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (merchantUserCriteria.getLinkMan() != null) {
                    predicate.getExpressions().add(cb.like(root.get("linkMan"),"%"+merchantUserCriteria.getLinkMan()+"%"));
                }
                if (merchantUserCriteria.getPhoneNum() != null) {
                    predicate.getExpressions().add(cb.like(root.get("phoneNum"), "%"+merchantUserCriteria.getPhoneNum()+"%"));
                }
                if (merchantUserCriteria.getStartDate() != null && merchantUserCriteria.getEndDate() != null) {
                    predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createdDate"), new Date(merchantUserCriteria.getStartDate())));
                    predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createdDate"), new Date(merchantUserCriteria.getEndDate())));
                }
                if (merchantUserCriteria.getCity() != null) {
                    predicate.getExpressions().add(cb.equal(root.get("city"), merchantUserCriteria.getCity()));
                }
                if(merchantUserCriteria.getType()!=null) {
                    predicate.getExpressions().add(cb.equal(root.get("type"),merchantUserCriteria.getType()));
                }
                if(merchantUserCriteria.getKeyword()!=null) {
                    predicate.getExpressions().add(cb.like(root.get("name"),"%"+merchantUserCriteria.getKeyword()+"%"));
                }
                return predicate;
            }
        };
    }

    /**
     * 查询所有商户账号（管理员）
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public List<MerchantUser> findAllManager() {
        return merchantUserRepository.findMerchantUserByType(8);
    }

    /**
     * 根据id查询商户
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public MerchantUser findById(Long id) {
        return merchantUserRepository.findOne(id);
    }

    /**
     *  保存修改
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public void updateMerchantUser(MerchantUser merchantUser) {
        MerchantUser existMerchantUser = merchantUserRepository.findOne(merchantUser.getId());
        existMerchantUser.setName(merchantUser.getName());                         // 更新商户信息
        existMerchantUser.setLinkMan(merchantUser.getLinkMan());
        existMerchantUser.setMerchantName(merchantUser.getMerchantName());
        existMerchantUser.setPhoneNum(merchantUser.getPhoneNum());
        existMerchantUser.setMerchantBank(merchantUser.getMerchantBank());
        existMerchantUser.setLockLimit(merchantUser.getLockLimit());
        existMerchantUser.setCity(merchantUser.getCity());
        MerchantBank existBank = existMerchantUser.getMerchantBank();
        MerchantBank merchantBank = merchantUser.getMerchantBank();                 // 更新银行信息
        existBank.setBankName(merchantBank.getBankName());
        existBank.setBankNumber(merchantBank.getBankNumber());
        merchantUserRepository.save(existMerchantUser);
    }
}
