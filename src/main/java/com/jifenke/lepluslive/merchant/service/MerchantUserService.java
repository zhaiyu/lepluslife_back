package com.jifenke.lepluslive.merchant.service;

import com.jifenke.lepluslive.global.util.MD5Util;
import com.jifenke.lepluslive.merchant.domain.criteria.MerchantUserCriteria;
import com.jifenke.lepluslive.merchant.domain.entities.City;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantBank;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantWallet;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  @Inject
  private MerchantService merchantService;

  @Inject
  private MerchantPosService merchantPosService;

  @Inject
  private MerchantSettlementService merchantSettlementService;

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
                if(merchantUserCriteria.getMerchantName()!=null) {
                    predicate.getExpressions().add(cb.like(root.get("merchantName"),"%"+merchantUserCriteria.getMerchantName()+"%"));
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
        existMerchantUser.setPartner(merchantUser.getPartner());
        MerchantBank existBank = existMerchantUser.getMerchantBank();
        MerchantBank merchantBank = merchantUser.getMerchantBank();                 // 更新银行信息
        existBank.setBankName(merchantBank.getBankName());
        existBank.setBankNumber(merchantBank.getBankNumber());
        existBank.setType(merchantBank.getType());
        existBank.setPayee(merchantBank.getPayee());
        merchantBankRepository.save(existBank);
        merchantUserRepository.save(existMerchantUser);
    }

  /**
   * 根据商户id获取商户名称  16/12/29
   *
   * @param id 商户ID
   */
 public String findMerchantNameById(Long id){
    return merchantUserRepository.findMerchantNameById(id);
  }

  /**
   * 根据商户列表获取每个商户的概要信息  2017/01/04
   * @param list 商户列表
   * @return
   */
  @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
  public List<Map<String,Object>> getMerchantUserInfo(List<MerchantUser> list) {
    List<Map<String,Object>> result = new ArrayList<>();
    for (MerchantUser user : list){
      Map<String,Object> map = new HashMap<>();
      map.put("id",user.getId());
      if(user.getCity() != null){
        map.put("cityName",user.getCity().getName());
      }
      map.put("merchantName",user.getMerchantName());
      map.put("createdDate",user.getCreatedDate());
      //商户号数量
      List<Object[]> settles = merchantSettlementService.countByMerchantUser(user.getId());
      if(settles != null && settles.size() > 0){
        map.put("total_settle",settles.get(0)[0]);
        map.put("LM_settle",settles.get(0)[1]);
        map.put("PT_settle",settles.get(0)[2]);
      }
      //名下门店
      List<Object[]> merchants = merchantService.countByMerchantUser(user.getId());
      if(merchants != null){
        if(merchants.size() == 1){  //单门店
          Long merchantId = Long.valueOf("" + merchants.get(0)[0]);
          Merchant merchant = new Merchant();
          merchant.setId(merchantId);
          map.put("total_shops",1);
          if("1".equals("" + merchants.get(0)[1])){
            map.put("LM_shops",1);
            map.put("PT_shops",0);
          } else {
            map.put("LM_shops",0);
            map.put("PT_shops",1);
          }
          map.put("pos",merchantPosService.countByMerchant(merchantId));
          MerchantWallet wallet = merchantService.findMerchantWalletByMerchant(merchant);
          if(wallet != null){
            map.put("availableBalance",wallet.getAvailableBalance());
            map.put("totalMoney",wallet.getTotalMoney());
          }
          map.put("total_user",merchants.get(0)[2]); //锁定会员上限
          map.put("binding_user",merchantService.countByBindMerchant(merchantId)); //已锁定会员
        } else {  //多门店
          map.put("total_shops",merchants.size());
          int LM_shops = 0;
          long total_user = 0;
          long binding_user = 0;
          long pos = 0;
          long availableBalance = 0;
          long totalMoney = 0;
          for (Object[] o : merchants){
            total_user += Long.valueOf("" + o[2]);
            if("1".equals("" + o[1])){
              LM_shops++;
              Long merchantId = Long.valueOf("" + o[0]);
              Merchant merchant = new Merchant();
              merchant.setId(merchantId);
              pos += merchantPosService.countByMerchant(merchantId);
              MerchantWallet wallet = merchantService.findMerchantWalletByMerchant(merchant);
              if(wallet != null){
                availableBalance += wallet.getAvailableBalance();
                totalMoney += wallet.getTotalMoney();
              }
              binding_user += merchantService.countByBindMerchant(merchantId);
            }
          }
          map.put("LM_shops",LM_shops);
          map.put("PT_shops",merchants.size() - LM_shops);
          map.put("pos",pos);
          map.put("availableBalance",availableBalance);
          map.put("totalMoney",totalMoney);
          map.put("total_user",total_user); //锁定会员上限
          map.put("binding_user",binding_user); //已锁定会员
        }
      }
      result.add(map);
    }
    return result;
  }

  /**
   * 获取商户已经绑定的用户数量  2017/01/04
   * @param merchantUserId 商户ID
   */
  @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
  public Integer getBindNumber(Long merchantUserId) {
      Integer binding_user = 0;
      //名下门店
      List<Object[]> merchants = merchantService.countByMerchantUser(merchantUserId);
      if(merchants != null){
        if(merchants.size() == 1){  //单门店
          binding_user = merchantService.countByBindMerchant(Long.valueOf("" + merchants.get(0)[0]));
        } else {  //多门店
          for (Object[] o : merchants){
            if("1".equals("" + o[1])){
              binding_user += merchantService.countByBindMerchant(Long.valueOf("" + o[0]));
            }
          }
        }
      }
    return binding_user;
  }
}
