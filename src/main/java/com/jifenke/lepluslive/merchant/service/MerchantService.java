package com.jifenke.lepluslive.merchant.service;

import com.jifenke.lepluslive.barcode.BarcodeConfig;
import com.jifenke.lepluslive.barcode.service.BarcodeService;
import com.jifenke.lepluslive.filemanage.service.FileImageService;
import com.jifenke.lepluslive.global.config.Constants;
import com.jifenke.lepluslive.global.util.MD5Util;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.criteria.MerchantCriteria;
import com.jifenke.lepluslive.merchant.domain.entities.City;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantInfo;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantPos;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantType;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantWallet;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantWalletOnline;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantWeiXinUser;
import com.jifenke.lepluslive.merchant.repository.MerchantInfoRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantPosRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantProtocolRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantTypeRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantUserRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantWalletOnlineRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantWalletRepository;
import com.jifenke.lepluslive.order.domain.entities.FinancialStatistic;
import com.jifenke.lepluslive.partner.domain.entities.Partner;
import com.jifenke.lepluslive.sales.domain.entities.SalesStaff;
import com.jifenke.lepluslive.user.domain.entities.RegisterOrigin;
import com.jifenke.lepluslive.user.repository.LeJiaUserRepository;
import com.jifenke.lepluslive.user.repository.RegisterOriginRepository;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
public class MerchantService {

  @Inject
  private MerchantRepository merchantRepository;

  @Inject
  private MerchantTypeRepository merchantTypeRepository;

  @Inject
  private RegisterOriginRepository registerOriginRepository;

  @Inject
  private MerchantUserRepository merchantUserRepository;

  @Inject
  private BarcodeService barcodeService;

  @Inject
  private FileImageService fileImageService;

  @Inject
  private MerchantWalletRepository merchantWalletRepository;

  @Inject
  private MerchantProtocolRepository merchantProtocolRepository;

  @Inject
  private LeJiaUserRepository leJiaUserRepository;

  @Inject
  private MerchantInfoRepository merchantInfoRepository;

  @Inject
  private MerchantWeiXinUserService merchantWeiXinUserService;

  @Inject
  private MerchantPosRepository merchantPosRepository;

  @Inject
  private WeiXinService weiXinService;

  @Inject
  private WeiXinQrCodeRepository weiXinQrCodeRepository;

  @Inject
  private MerchantWalletOnlineRepository walletOnlineRepository;

  @Value("${bucket.ossBarCodeReadRoot}")
  private String barCodeRootUrl;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Page findMerchantsByPage(MerchantCriteria merchantCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "sid");
    return merchantRepository
        .findAll(getWhereClause(merchantCriteria),
                 new PageRequest(merchantCriteria.getOffset() - 1, limit, sort));
  }

  /**
   * 邀请码页面  16/09/07
   *
   * @param merchantCriteria 筛选条件
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Page findMerchantInfoByPage(MerchantCriteria merchantCriteria) {
    Sort sort = new Sort(Sort.Direction.DESC, "merchantInfo.qrCode");
    return merchantRepository
        .findAll(getWhereClause(merchantCriteria),
                 new PageRequest(merchantCriteria.getOffset() - 1, 10, sort));
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Merchant findMerchantById(Long id) {
    return merchantRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void createMerchant(Merchant merchant) {
    if (merchant.getId() != null) {
      throw new RuntimeException("新建商户ID不能存在");
    }
    merchant.setSid((int) merchantRepository.count());
    String merchantSid = MvUtil.getMerchantSid();
    while (merchantRepository.findByMerchantSid(merchantSid).isPresent()) {
      merchantSid = MvUtil.getMerchantSid();
    }
    merchant.setMerchantSid(merchantSid);
    byte[]
        bytes =
        new byte[0];
    try {
      bytes = barcodeService.qrCode(Constants.MERCHANT_URL + merchant.getMerchantSid(),
                                    BarcodeConfig.QRCode.defaultConfig());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    String filePath = MvUtil.getFilePath(Constants.BAR_CODE_EXT);

    merchant.setQrCodePicture(barCodeRootUrl + "/" + filePath);
    final byte[] finalBytes = bytes;
    new Thread(() -> {
      fileImageService.SaveBarCode(finalBytes, filePath);
    }).start();

    MerchantInfo merchantInfo = new MerchantInfo();
    merchantInfoRepository.save(merchantInfo);
    merchant.setMerchantInfo(merchantInfo);
    merchantRepository.save(merchant);
    RegisterOrigin registerOrigin = new RegisterOrigin();
    registerOrigin.setOriginType(3);
    registerOrigin.setMerchant(merchant);
    MerchantWallet merchantWallet = new MerchantWallet();
    merchantWallet.setMerchant(merchant);
    MerchantWalletOnline walletOnline = new MerchantWalletOnline();
    walletOnline.setMerchant(merchant);
    merchant.getMerchantProtocols().stream().map(merchantProtocol -> {
      merchantProtocol.setMerchant(merchant);
      merchantProtocolRepository.save(merchantProtocol);
      return merchantProtocol;
    }).collect(Collectors.toList());
    merchantWalletRepository.save(merchantWallet);
    walletOnlineRepository.save(walletOnline);
    registerOriginRepository.save(registerOrigin);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editMerchant(Merchant merchant) {
    Merchant origin = merchantRepository.findOne(merchant.getId());
    origin.getMerchantProtocols().stream().map(merchantProtocol -> {
      merchantProtocolRepository.delete(merchantProtocol);
      return null;
    }).collect(Collectors.toList());
    String sid = origin.getMerchantSid();
    if (origin == null) {
      throw new RuntimeException("不存在的商户");
    }
    origin.setSalesStaff(merchant.getSalesStaff());
    origin.setLjBrokerage(merchant.getLjBrokerage());
    origin.setLjCommission(merchant.getLjCommission());
    origin.setName(merchant.getName());
    origin.setLocation(merchant.getLocation());
    origin.setPartner(merchant.getPartner());
    origin.setArea(merchant.getArea());
    origin.setUserLimit(merchant.getUserLimit());
    origin.setCity(merchant.getCity());
    origin.setContact(merchant.getContact());
    origin.setPayee(merchant.getPayee());
    origin.setCycle(merchant.getCycle());
    origin.setMerchantBank(merchant.getMerchantBank());
    origin.setMerchantPhone(merchant.getMerchantPhone());
    origin.setMerchantProtocols(merchant.getMerchantProtocols());
    origin.setScoreARebate(merchant.getScoreARebate());
    origin.setScoreBRebate(merchant.getScoreBRebate());
    origin.setMerchantType(merchant.getMerchantType());
    origin.setReceiptAuth(merchant.getReceiptAuth());
    origin.setPartnership(merchant.getPartnership());
    origin.setMemberCommission(merchant.getMemberCommission());
    long l = merchant.getId();
    origin.setSid((int) l);
    origin.setMerchantSid(sid);
    origin.getMerchantProtocols().stream().map(merchantProtocol -> {
      merchantProtocol.setMerchant(origin);
      merchantProtocolRepository.save(merchantProtocol);
      return null;
    }).collect(Collectors.toList());
    merchantRepository.save(origin);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void disableMerchant(Long id) {
    Merchant merchant = merchantRepository.findOne(id);
    if (merchant == null) {
      throw new RuntimeException("不存在的商户");
    }
    merchant.setState(0);
    merchantRepository.save(merchant);
  }

  public List<MerchantType> findAllMerchantTypes() {
    return merchantTypeRepository.findAll();
  }

  public static Specification<Merchant> getWhereClause(MerchantCriteria merchantCriteria) {
    return new Specification<Merchant>() {
      @Override
      public Predicate toPredicate(Root<Merchant> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (merchantCriteria.getPartnership() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("partnership"),
                       merchantCriteria.getPartnership()));
        } else {
          predicate.getExpressions().add(
              cb.notEqual(r.get("partnership"),
                          2));
        }

        if (merchantCriteria.getMerchantType() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("merchantType"),
                       new MerchantType(merchantCriteria.getMerchantType())));
        }

        if (merchantCriteria.getMerchantName() != null
            && merchantCriteria.getMerchantName() != "") {

          predicate.getExpressions().add(
              cb.like(r.get("name"),
                      "%" + merchantCriteria.getMerchantName() + "%"));

        }
        if (merchantCriteria.getMerchantSid() != null && merchantCriteria.getMerchantSid() != "") {

          predicate.getExpressions().add(
              cb.like(r.get("merchantSid"),
                      "%" + merchantCriteria.getMerchantSid() + "%"));

        }

        if (merchantCriteria.getReceiptAuth() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("receiptAuth"),
                       merchantCriteria.getReceiptAuth()));
        }

        if (merchantCriteria.getStoreState() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("state"),
                       merchantCriteria.getStoreState()));
        }

        if (merchantCriteria.getStartDate() != null && merchantCriteria.getStartDate() != "") {
          predicate.getExpressions().add(
              cb.between(r.get("createDate"), new Date(merchantCriteria.getStartDate()),
                         new Date(merchantCriteria.getEndDate())));
        }

        if (merchantCriteria.getCity() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("city"),
                       new City(merchantCriteria.getCity())));
        }
//根据销售筛选
        if (merchantCriteria.getSalesStaff() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("salesStaff"),
                       merchantCriteria.getSalesStaff()));
        }

        return predicate;
      }
    };
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public List<MerchantUser> findMerchantUsersByMerchant(Merchant merchant) {
    if (!merchantUserRepository.findByMerchantAndType(merchant, 1).isPresent()) {
      MerchantUser merchantUser = new MerchantUser();
      merchantUser.setMerchant(merchant);
      merchantUser.setType(1);
      merchantUserRepository.save(merchantUser);
    }
    return merchantUserRepository.findAllByMerchant(merchant);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public void deleteMerchantUser(Long id) {

    merchantWeiXinUserService.unBindMerchantUser(merchantUserRepository.findOne(id));
    merchantUserRepository.delete(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editMerchantUser(MerchantUser merchantUser) {
    MerchantUser origin = merchantUser;
    if (merchantUser.getId() != null) {
      origin = merchantUserRepository.findOne(merchantUser.getId());
    } else {
      origin.setMerchant(findMerchantById(merchantUser.getMerchant().getId()));
      origin.setType(0);
    }
    origin.setPassword(MD5Util.MD5Encode(merchantUser.getPassword(), "UTF-8"));
    origin.setName(merchantUser.getName());
    merchantUserRepository.save(origin);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public MerchantUser getMerchantUserById(Long id) {
    return merchantUserRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public Merchant qrCodeManage(Long id) {
    Merchant merchant = merchantRepository.findOne(id);

    if (merchant.getQrCodePicture() == null) {
      byte[]
          bytes =
          new byte[0];
      try {
        bytes = barcodeService.qrCode(Constants.MERCHANT_URL + merchant.getMerchantSid(),
                                      BarcodeConfig.QRCode.defaultConfig());
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      String filePath = MvUtil.getFilePath(Constants.BAR_CODE_EXT);
      fileImageService.SaveBarCode(bytes, filePath);

      merchant.setQrCodePicture(barCodeRootUrl + "/" + filePath);

      merchantRepository.save(merchant);
    }

    return merchant;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<MerchantUser> findMerchantUserByMerchant(Merchant merchant) {
    return merchantUserRepository.findAllByMerchant(merchant);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public int findSalesMerchantCount(String id) {
    return merchantRepository.findSalesMerchantCount(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void openStore(Merchant merchant) {
    Merchant origin = merchantRepository.findOne(merchant.getId());
    MerchantInfo merchantInfo = origin.getMerchantInfo();
    MerchantInfo info = merchant.getMerchantInfo();
    if (merchantInfo != null) {
      merchantInfo.setCard(info.getCard());
      merchantInfo.setPark(info.getPark());
      merchantInfo.setPerSale(info.getPerSale());
      merchantInfo.setStar(info.getStar());
      merchantInfo.setWifi(info.getWifi());
      merchantInfo.setDescription(info.getDescription());
      merchantInfo.setDiscount(info.getDiscount());
      merchantInfo.setFeature(info.getFeature());
      merchantInfo.setReason(info.getReason());
      merchantInfo.setVipPicture(info.getVipPicture());
      merchantInfo.setDoorPicture(info.getDoorPicture());
      merchantInfoRepository.save(merchantInfo);
    } else {
      merchantInfoRepository.save(info);
      origin.setMerchantInfo(info);
    }

    origin.setState(1);

    origin.setPicture(merchant.getPicture());

    origin.setLat(merchant.getLat());

    origin.setLng(merchant.getLng());

    origin.setOfficeHour(merchant.getOfficeHour());

    origin.setPhoneNumber(merchant.getPhoneNumber());

    merchantRepository.save(origin);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void closeStore(Long merchantId) {
    Merchant origin = merchantRepository.findOne(merchantId);

    origin.setState(0);

    merchantRepository.save(origin);
  }

  //获取每个合伙人的锁定会员数
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<Integer> findBindLeJiaUsers(List<Merchant> merchants) {

    List<Integer> binds = new ArrayList<>();
    int count = 0;
    for (Merchant merchant : merchants) {
      count = leJiaUserRepository.countByBindMerchant(merchant.getId());
      binds.add(count);
    }
    return binds;
  }

  /**
   * 获取商户邀请码列表页面所需数据 16/09/06
   *
   * @param merchants 商家列表
   * @return 数据
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<Map> findMerchantCodeData(List<Merchant> merchants) {
    List<Map> mapList = new ArrayList<>();
    List<Integer> binds = new ArrayList<>();
    Integer count = null;
    List<Object[]> scoreAs = null;
    List<Object[]> list = null;
    for (Merchant merchant : merchants) {
      Map<String, Object> map = new HashMap<>();
      String subSource = "4_0_" + merchant.getId();  //关注来源
      //获取注册来源为该商家的用户总数
      count = leJiaUserRepository.countBySubSource(subSource);
      if (count == 0) {
        map.put("inviteM", 0);//邀请会员数
        map.put("inviteU", 0);//邀请粉丝数
        map.put("commission", 0);//邀请会员的累计产生佣金
        map.put("totalA", 0); //邀请会员的会员累计获得红包额
        map.put("usedA", 0);  //邀请会员的会员累计使用红包额
        //邀请会员的各种订单类型数量
        map.put("order_1", 0);
        map.put("order_3", 0);
        map.put("order_5", 0);
      } else {
        //邀请会员数
        count = leJiaUserRepository.countBySubSourceAndState(subSource);
        map.put("inviteM", count);
        //邀请粉丝数
        count = leJiaUserRepository.countBySubSourceAndSubState(subSource);
        map.put("inviteU", count);
        //邀请会员的累计产生佣金
        count = leJiaUserRepository.countLJCommissionByMerchant(subSource);
        map.put("commission", count);
        //邀请会员的会员累计红包额和使用红包额
        scoreAs = leJiaUserRepository.countScoreAByMerchant(subSource);
        map.put("totalA", scoreAs.get(0)[0]);
        map.put("usedA", scoreAs.get(0)[1]);
        //邀请会员的各种订单类型数量
        list = leJiaUserRepository.countOrderByMerchant(subSource);
        for (Object[] o : list) {
          map.put("order_" + o[0], o[1]);
        }
        if (map.get("order_1") == null) {
          map.put("order_1", 0);
        }
        if (map.get("order_3") == null) {
          map.put("order_3", 0);
        }
        if (map.get("order_5") == null) {
          map.put("order_5", 0);
        }
      }
      //绑定会员数量
      count = leJiaUserRepository.countByBindMerchant(merchant.getId());
      map.put("bindM", count);

      map.put("id", merchant.getId());
      map.put("merchantSid", merchant.getMerchantSid());
      map.put("name", merchant.getName());
      map.put("userLimit", merchant.getUserLimit());
      map.put("partnership", merchant.getPartnership());
      map.put("qrCode", merchant.getMerchantInfo().getQrCode());
      map.put("ticket", merchant.getMerchantInfo().getTicket());
      mapList.add(map);
    }
    return mapList;
  }

  /**
   * 创建商户邀请码(永久二维码)  16/09/06
   *
   * @param merchantId 商户ID
   * @return 状态
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public int createQrCode(Long merchantId) throws Exception {
    Merchant merchant = findMerchantById(merchantId);
    MerchantInfo info = merchant.getMerchantInfo();
    try {
      if (info != null) {
        if (info.getQrCode() == null || info.getQrCode() == 0) {
          //创建一个对应的永久二维码  以“M”开头
          String parameter = "M" + MvUtil.getRandomStr();  //商户永久二维码开头为“M”
          Map map = weiXinService.createForeverQrCode(parameter);
          if (map.get("errcode") == null || Integer.valueOf(map.get("errcode").toString()) == 0) {
            info.setQrCode(1);
            info.setParameter(parameter);
            info.setTicket(map.get("ticket").toString());
            //在永久二维码表中添加记录
            WeiXinQrCode qrCode = new WeiXinQrCode();
            qrCode.setParameter(parameter);
            qrCode.setTicket(map.get("ticket").toString());
            qrCode.setType(1);
            weiXinQrCodeRepository.save(qrCode);
            merchantInfoRepository.save(info);
            return 200;
          } else {
            return 504;
          }
        } else {
          return 408;
        }
      } else {
        return 404;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return 500;
    }
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public MerchantWallet findMerchantWalletByMerchant(Merchant merchant) {
    return merchantWalletRepository.findByMerchant(merchant);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void changeMerchantWalletTotalTransferMoney(FinancialStatistic financialStatistic) {
    MerchantWallet merchantWallet = findMerchantWalletByMerchant(financialStatistic.getMerchant());
    merchantWallet.setTotalTransferMoney(
        merchantWallet.getTotalTransferMoney() + financialStatistic.getTransferPrice());
    merchantWalletRepository.save(merchantWallet);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public Merchant pureQrCodeManage(Long id) {
    Merchant merchant = merchantRepository.findOne(id);

    if (merchant.getPureQrCode() == null) {
      byte[]
          bytes =
          new byte[0];
      try {
        bytes =
            barcodeService
                .qrCode(Constants.MERCHANT_URL + merchant.getMerchantSid() + "?pure=access",
                        BarcodeConfig.QRCode.defaultConfig());
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      String filePath = MvUtil.getFilePath(Constants.BAR_CODE_EXT);
      fileImageService.SaveBarCode(bytes, filePath);

      merchant.setPureQrCode(barCodeRootUrl + "/" + filePath);

      merchantRepository.save(merchant);
    }

    return merchant;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<Merchant> findMerchantBySalesStaffId(String id1) {
    return merchantRepository.findMerchantBySaleId(id1);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveMerchant(Merchant merchant) {
    merchantRepository.saveAndFlush(merchant);
  }

  public List findAllPosByMerchant(Merchant merchant) {
    return merchantPosRepository.findAllByMerchant(merchant);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveMerchantWallet(MerchantWallet merchantWallet) {
    merchantWalletRepository.saveAndFlush(merchantWallet);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Merchant findMerchantByMerchantSid(String merchantSid) {
    Optional<Merchant> optional = merchantRepository.findByMerchantSid(merchantSid);
    if (optional.isPresent()) {
      return optional.get();
    } else {
      return null;
    }
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<MerchantWeiXinUser> findmerchantWeixinUserByMerchanUsers(
      List<MerchantUser> merchantUsers) {
    List<MerchantWeiXinUser> results = new ArrayList<>();
    merchantUsers.stream().map(merchantUser -> {
      List<MerchantWeiXinUser>
          merchantWeiXinUsers =
          merchantWeiXinUserService.findMerchantWeiXinUserByMerchantUser(merchantUser);
      results.addAll(merchantWeiXinUsers);
      return merchantUser;
    }).collect(Collectors.toList());
    return results;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editPartnerVirtualMerchant(Partner origin) {
    Merchant merchant = merchantRepository.findByPartnerAndPartnership(origin, 2);
    merchant.setName(origin.getPartnerName() + "(合伙人)");
    merchantRepository.save(merchant);
  }
}
