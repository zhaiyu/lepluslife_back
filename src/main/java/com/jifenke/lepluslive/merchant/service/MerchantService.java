package com.jifenke.lepluslive.merchant.service;

import com.jifenke.lepluslive.barcode.BarcodeConfig;
import com.jifenke.lepluslive.barcode.service.BarcodeService;
import com.jifenke.lepluslive.filemanage.service.FileImageService;
import com.jifenke.lepluslive.global.config.Constants;
import com.jifenke.lepluslive.global.util.MD5Util;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.domain.criteria.MerchantCriteria;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantType;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantUser;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantWallet;
import com.jifenke.lepluslive.merchant.repository.MerchantRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantTypeRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantUserRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantWalletRepository;
import com.jifenke.lepluslive.order.domain.criteria.OLOrderCriteria;
import com.jifenke.lepluslive.user.domain.entities.RegisterOrigin;
import com.jifenke.lepluslive.user.repository.RegisterOriginRepository;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
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

  @Value("${bucket.ossBarCodeReadRoot}")
  private String barCodeRootUrl;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Page findMerchantsByPage(MerchantCriteria merchantCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "sid");
    return merchantRepository
        .findAll(getWhereClause(merchantCriteria),
                 new PageRequest(merchantCriteria.getOffset() - 1, limit, sort));
  }

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
    merchantRepository.save(merchant);
    RegisterOrigin registerOrigin = new RegisterOrigin();
    registerOrigin.setOriginType(3);
    registerOrigin.setMerchant(merchant);
    MerchantWallet merchantWallet = new MerchantWallet();
    merchantWallet.setMerchant(merchant);
    merchantWalletRepository.save(merchantWallet);
    registerOriginRepository.save(registerOrigin);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editMerchant(Merchant merchant) {
    Merchant origin = merchantRepository.findOne(merchant.getId());
    if (origin == null) {
      throw new RuntimeException("不存在的商户");
    }
    try {
      BeanUtils.copyProperties(origin, merchant);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    long l = merchant.getId();
    origin.setSid((int) l);
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
        }

        if (merchantCriteria.getMerchantType() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("merchantType"),
                       new MerchantType(merchantCriteria.getMerchantType())));
        }

        if (merchantCriteria.getMerchant() != null && merchantCriteria.getMerchant() != "") {
          if (merchantCriteria.getMerchant().matches("^\\d{1,6}$")) {
            predicate.getExpressions().add(
                cb.like(r.<Merchant>get("merchant").get("merchantSid"),
                        "%" + merchantCriteria.getMerchant() + "%"));
          } else {
            predicate.getExpressions().add(
                cb.like(r.<Merchant>get("merchant").get("name"),
                        "%" + merchantCriteria.getMerchant() + "%"));
          }
        }
        return predicate;
      }
    };
  }

  public List<MerchantUser> findMerchantUsersByMerchant(Merchant merchant) {
    return merchantUserRepository.findAllByMerchant(merchant);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteMerchantUser(Long id) {
    merchantUserRepository.delete(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void editMerchantUser(MerchantUser merchantUser) {
    Merchant merchant = merchantRepository.findOne(merchantUser.getMerchant().getId());
    merchantUser.setPassword(MD5Util.MD5Encode(merchantUser.getPassword(), "UTF-8"));
    merchantUser.setMerchant(merchant);
    merchantUserRepository.save(merchantUser);
  }

  public MerchantUser getMerchantUserById(Long id) {
    return merchantUserRepository.findOne(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public String qrCodeManage(Long id) {
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

    return merchant.getQrCodePicture();
  }
}
