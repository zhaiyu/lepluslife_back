package com.jifenke.lepluslive.merchant.service;

import com.jifenke.lepluslive.merchant.domain.entities.TemporaryMerchantUserShop;
import com.jifenke.lepluslive.order.service.ScanCodeOrderService;
import com.jifenke.lepluslive.global.util.MD5Util;
import com.jifenke.lepluslive.merchant.domain.criteria.MerchantUserCriteria;
import com.jifenke.lepluslive.merchant.domain.entities.City;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantBank;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUserShop;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantWallet;
import com.jifenke.lepluslive.merchant.repository.CityRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantBankRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantUserRepository;
import com.jifenke.lepluslive.order.service.OffLineOrderService;
import com.jifenke.lepluslive.order.service.PosOrderService;

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

  @Inject
  private MerchantUserShopService merchantUserShopService;

  @Inject
  private MerchantWeiXinUserService merchantWeiXinUserService;

  @Inject
  private OffLineOrderService offLineOrderService;

  @Inject
  private ScanCodeOrderService scanCodeOrderService;

  @Inject
  private PosOrderService posOrderService;

  @Inject
  private TemporaryMerchantUserShopService temporaryMerchantUserShopService;

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
    // 判断创建者是否为空,如果为主商户，创建者为自身
    if(merchantUser.getCreateUserId()==null) {
      merchantUser.setCreateUserId(merchantUser.getId());
      merchantUserRepository.save(merchantUser);
    }
  }

  /**
   * 根据用户名查询商户账号
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
  public static Specification<MerchantUser> getWhereClause(
      MerchantUserCriteria merchantUserCriteria) {
    return new Specification<MerchantUser>() {
      @Override
      public Predicate toPredicate(Root<MerchantUser> root, CriteriaQuery<?> query,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (merchantUserCriteria.getLinkMan() != null) {
          predicate.getExpressions()
              .add(cb.like(root.get("linkMan"), "%" + merchantUserCriteria.getLinkMan() + "%"));
        }
        if (merchantUserCriteria.getPhoneNum() != null) {
          predicate.getExpressions()
              .add(cb.like(root.get("phoneNum"), "%" + merchantUserCriteria.getPhoneNum() + "%"));
        }
        if (merchantUserCriteria.getStartDate() != null
            && merchantUserCriteria.getEndDate() != null) {
          predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createdDate"), new Date(
              merchantUserCriteria.getStartDate())));
          predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createdDate"), new Date(
              merchantUserCriteria.getEndDate())));
        }
        if (merchantUserCriteria.getCity() != null) {
          predicate.getExpressions()
              .add(cb.equal(root.get("city"), merchantUserCriteria.getCity()));
        }
        if (merchantUserCriteria.getType() != null) {
          predicate.getExpressions()
              .add(cb.equal(root.get("type"), merchantUserCriteria.getType()));
        }
        if (merchantUserCriteria.getKeyword() != null) {
          predicate.getExpressions()
              .add(cb.like(root.get("name"), "%" + merchantUserCriteria.getKeyword() + "%"));
        }
        if (merchantUserCriteria.getMerchantName() != null) {
          predicate.getExpressions().add(cb.like(root.get("merchantName"),
                                                 "%" + merchantUserCriteria.getMerchantName()
                                                 + "%"));
        }
        return predicate;
      }
    };
  }

  /**
   * 查询所有商户账号（管理员）
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<MerchantUser> findAllManager() {
    return merchantUserRepository.findMerchantUserByType(8);
  }

  /**
   * 根据id查询商户
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public MerchantUser findById(Long id) {
    return merchantUserRepository.findOne(id);
  }

  /**
   * 保存修改
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
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
  public String findMerchantNameById(Long id) {
    return merchantUserRepository.findMerchantNameById(id);
  }

  /**
   * 根据商户列表获取每个商户的概要信息  2017/01/04
   *
   * @param list 商户列表
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<Map<String, Object>> getMerchantUserInfo(List<MerchantUser> list) {
    List<Map<String, Object>> result = new ArrayList<>();
    for (MerchantUser user : list) {
      Map<String, Object> map = new HashMap<>();
      map.put("id", user.getId());
      if (user.getCity() != null) {
        map.put("cityName", user.getCity().getName());
      }
      map.put("merchantName", user.getMerchantName());
      map.put("createdDate", user.getCreatedDate());
      map.putAll(collection(user.getId()));
      result.add(map);
    }
    return result;
  }

  /**
   * 获取商户的统计数据(商户号、门店等)  2017/01/24
   *
   * @param id 商户ID
   */
  public Map<String, Object> collection(Long id) {
    Map<String, Object> map = new HashMap<>();
    //商户号数量
    List<Object[]> settles = merchantSettlementService.countByMerchantUser(id);
    if (settles != null && settles.size() > 0) {
      map.put("total_settle", settles.get(0)[0]);
      map.put("LM_settle", settles.get(0)[1]);
      map.put("PT_settle", settles.get(0)[2]);
    }
    //名下门店
    List<Object[]> merchants = merchantService.countByMerchantUser(id);
    if (merchants != null) {
      if (merchants.size() == 1) {  //单门店
        Long merchantId = Long.valueOf("" + merchants.get(0)[0]);
        Merchant merchant = new Merchant();
        merchant.setId(merchantId);
        map.put("total_shops", 1);
        if ("1".equals("" + merchants.get(0)[1])) {
          map.put("LM_shops", 1);
          map.put("PT_shops", 0);
        } else {
          map.put("LM_shops", 0);
          map.put("PT_shops", 1);
        }
        map.put("pos", merchantPosService.countByMerchant(merchantId));
        MerchantWallet wallet = merchantService.findMerchantWalletByMerchant(merchant);
        if (wallet != null) {
          map.put("availableBalance", wallet.getAvailableBalance());
          map.put("totalMoney", wallet.getTotalMoney());
        }
        map.put("total_user", merchants.get(0)[2]); //锁定会员上限
        map.put("binding_user", merchantService.countByBindMerchant(merchantId)); //已锁定会员

        map.putAll(offLineOrderService.countPriceByMerchant(merchantId)); //乐加支付 累计流水和累计收取红包
        map.putAll(scanCodeOrderService.countPriceByMerchant(merchantId)); //富友支付 累计流水和累计收取红包
        map.putAll(posOrderService.countPriceByMerchant(merchantId)); //POS支付 累计流水和累计收取红包
      } else {  //多门店
        map.put("total_shops", merchants.size());
        int LM_shops = 0;
        long total_user = 0;
        long binding_user = 0;
        long pos = 0;
        long availableBalance = 0;
        long totalMoney = 0;
        long totalPrice_lj = 0;
        long trueScore_lj = 0;
        long totalPrice_fy = 0;
        long trueScore_fy = 0;
        long totalPrice_pos = 0;
        long trueScore_pos = 0;
        for (Object[] o : merchants) {
          total_user += Long.valueOf("" + o[2]);
          if ("1".equals("" + o[1])) {
            LM_shops++;
          }
          Long merchantId = Long.valueOf("" + o[0]);
          Merchant merchant = new Merchant();
          merchant.setId(merchantId);
          pos += merchantPosService.countByMerchant(merchantId);
          MerchantWallet wallet = merchantService.findMerchantWalletByMerchant(merchant);
          if (wallet != null) {
            availableBalance += wallet.getAvailableBalance();
            totalMoney += wallet.getTotalMoney();
          }
          binding_user += merchantService.countByBindMerchant(merchantId);
          //乐加支付 累计流水和累计收取红包
          Map<String, Long> map_lj = offLineOrderService.countPriceByMerchant(merchantId);
          totalPrice_lj += map_lj.get("totalPrice_lj");
          trueScore_lj += map_lj.get("trueScore_lj");
          //富友支付 累计流水和累计收取红包
          Map<String, Long> map_fy = scanCodeOrderService.countPriceByMerchant(merchantId);
          totalPrice_fy += map_fy.get("totalPrice_fy");
          trueScore_fy += map_fy.get("trueScore_fy");
          //POS支付 累计流水和累计收取红包
          Map<String, Long> map_pos = posOrderService.countPriceByMerchant(merchantId);
          totalPrice_pos += map_pos.get("totalPrice_pos");
          trueScore_pos += map_pos.get("trueScore_pos");
        }
        map.put("LM_shops", LM_shops);
        map.put("PT_shops", merchants.size() - LM_shops);
        map.put("pos", pos);
        map.put("availableBalance", availableBalance);
        map.put("totalMoney", totalMoney);
        map.put("total_user", total_user); //锁定会员上限
        map.put("binding_user", binding_user); //已锁定会员

        map.put("totalPrice_lj", totalPrice_lj);
        map.put("trueScore_lj", trueScore_lj);
        map.put("totalPrice_fy", totalPrice_fy);
        map.put("trueScore_fy", trueScore_fy);
        map.put("totalPrice_pos", totalPrice_pos);
        map.put("trueScore_pos", trueScore_pos);
      }
    }
    return map;
  }

  /**
   * 获取商户已经绑定的用户数量  2017/01/04
   *
   * @param merchantUserId 商户ID
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Integer getBindNumber(Long merchantUserId) {
    Integer binding_user = 0;
    //名下门店
    List<Object[]> merchants = merchantService.countByMerchantUser(merchantUserId);
    if (merchants != null) {
      if (merchants.size() == 1) {  //单门店
        binding_user = merchantService.countByBindMerchant(Long.valueOf("" + merchants.get(0)[0]));
      } else {  //多门店
        for (Object[] o : merchants) {
          if ("1".equals("" + o[1])) {
            binding_user += merchantService.countByBindMerchant(Long.valueOf("" + o[0]));
          }
        }
      }
    }
    return binding_user;
  }

  /**
   * 获取商户下所有的账户信息  2017/02/07
   *
   * @param createUserId 所属商户（管理员） ID
   */
  public List<MerchantUser> findMerchantUsersByCreateUser(Long createUserId) {
    return merchantUserRepository.findAllByCreateUserId(createUserId);
  }

  /**
   * 保存账户 2017/02/09
   *
   * @param merchantUser 账户
   * @param shops        账户 可管理 门店
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public Map<String, String> editMerchantUser(MerchantUser merchantUser,
                                              List<MerchantUserShop> shops) {
    MerchantUser origin = null;
    Map<String, String> result = new HashMap<>();
    result.put("status", "200");
    if (merchantUser.getId() != null) {
      origin = merchantUserRepository.findOne(merchantUser.getId());
    } else {
      origin = new MerchantUser();
      origin.setCreateUserId(merchantUser.getCreateUserId());
      List<Object[]>
          merchants =
          merchantService.countByMerchantUser(merchantUser.getCreateUserId());
      if (merchants != null && merchants.size() == 1) {
        Merchant m = new Merchant();
        m.setId(Long.valueOf("" + merchants.get(0)[0]));
        origin.setMerchant(m);
      } else if (shops.size() == 1) {
        origin.setMerchant(shops.get(0).getMerchant());
      }
    }
    if ((merchantUser.getId() != null && (!merchantUser.getName().equals(origin.getName())))
        || merchantUser.getId() == null) {
      //判断用户名是否重复
      List<MerchantUser> list = merchantUserRepository.findByName(merchantUser.getName());
      if (list != null && list.size() > 0) {
        result.put("status", "201");
        result.put("msg", "用户名已存在");
        return result;
      }
    }

    if (!merchantUser.getPassword().equals(origin.getPassword())) {
      // 编辑的时候判断密码是否修改
      origin.setPassword(MD5Util.MD5Encode(merchantUser.getPassword(), "UTF-8"));
    } else if (merchantUser.getId() == null) {
      // 创建的时候直接添加
      origin.setPassword(MD5Util.MD5Encode(merchantUser.getPassword(), "UTF-8"));
    }
    origin.setName(merchantUser.getName());
    origin.setType(merchantUser.getType());
    merchantUserRepository.save(origin);
    // 判断创建者是否为空,如果为管理员，创建者为自身
    if(origin.getCreateUserId()==null) {
      origin.setCreateUserId(origin.getId());
      merchantUserRepository.save(origin);
    }
    List<MerchantUserShop> dels = merchantUserShopService.countByMerchantUser(origin);
    for (MerchantUserShop shop : dels) {
      merchantUserShopService.deleteShop(shop);
    }
    for (MerchantUserShop shop : shops) {
      shop.setMerchantUser(origin);
      merchantUserShopService.saveShop(shop);
    }
    return result;
  }

  /**
   * 账号删除  2017/02/09
   *
   * @param id 账号ID
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteMerchantUser(Long id) {
    MerchantUser user = merchantUserRepository.findOne(id);
    merchantWeiXinUserService.unBindMerchantUser(user);
    List<MerchantUserShop> dels = merchantUserShopService.countByMerchantUser(user);
    for (MerchantUserShop shop : dels) {
      merchantUserShopService.deleteShop(shop);
    }
    List<TemporaryMerchantUserShop>
        userShops =
        temporaryMerchantUserShopService.findAllByMerchantUser(user);
    for (TemporaryMerchantUserShop userShop : userShops) {
      temporaryMerchantUserShopService.deleteShop(userShop);
    }
    merchantUserRepository.delete(user);
  }

  //  /**
//   * 商户详情页的统计数据  2017/01/24
//   *
//   * @param user 商户
//   */
//  public Map<String, Object> merchantUserIndex(MerchantUser user) {
//    Map<String, Object> map = new HashMap<>();
//    map.putAll(collection(user.getId()));
////    List<Merchant> list = merchantService.countByMerchantUser(user);
////    if (list != null && list.size() > 0) {
////      for (Merchant m : list) {
////        MerchantWallet wallet = merchantService.findMerchantWalletByMerchant(m);
////        map.put("totalTransferMoney", wallet.getTotalTransferMoney());
////      }
////    } else {
////
////    }
//    return map;
//  }

  /**
   * 根据名称查询商户（门店管理员）
   */
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  public MerchantUser findMerchantManagerByName(String name) {
    return merchantUserRepository.findMerchantUserByType(name, 8);
  }
}
