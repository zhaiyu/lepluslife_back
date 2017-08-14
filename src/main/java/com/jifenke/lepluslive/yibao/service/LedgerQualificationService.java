package com.jifenke.lepluslive.yibao.service;


import com.jifenke.lepluslive.yibao.domain.entities.LedgerQualification;
import com.jifenke.lepluslive.yibao.domain.entities.MerchantUserLedger;
import com.jifenke.lepluslive.yibao.repository.LedgerQualificationRepository;
import com.jifenke.lepluslive.yibao.util.YbRequestUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


/**
 * Created by zhangwen on 2017/7/12.
 */
@Service
@Transactional(readOnly = true)
public class LedgerQualificationService {

  @Inject
  private LedgerQualificationRepository repository;

  @Inject
  private MerchantUserLedgerService merchantUserLedgerService;

  public LedgerQualification findByMerchantUserLedger(MerchantUserLedger ledger) {
    return repository.findByMerchantUserLedger(ledger);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void saveQualification(LedgerQualification qualification) {
    repository.save(qualification);
  }

  /**
   * 分账方资质上传 2017/7/18
   *
   * @param qualificationId 易宝子商户资质记录ID
   * @param picPath         图片保存完整路径
   * @param type            图片所属类型在易宝上传接口fileType数组属性(从0开始)
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public Map<String, String> uploadQualification(Long qualificationId, String picPath,
                                                 Integer type) {
    LedgerQualification qualification = repository.findOne(qualificationId);
    MerchantUserLedger merchantUserLedger = qualification.getMerchantUserLedger();
    Map<String, String>
        resultMap =
        YbRequestUtils
            .uploadQualification(merchantUserLedger.getLedgerNo(), picPath,
                                 type);
    if (resultMap == null) {
      resultMap = new HashMap<>();
      resultMap.put("code", "308");
      resultMap.put("msg", "提交异常");
      return resultMap;
    }
    if (!"1".equals(resultMap.get("code"))) {
      return resultMap;
    }
//     * 资质图片上传类型顺序
//     * ID_CARD_FRONT 身份证正面、0
//     * ID_CARD_BACK 身份证背面 、1
//     * BANK_CARD_FRONT 银行卡正面、2
//     * BANK_CARD_BACK 银行卡背面、3
//     * PERSON_PHOTO 手持身份证照片、4
//     * BUSSINESS_LICENSE 营业执照、5
//     * BUSSINESS_CERTIFICATES 工商证、6
//     * ORGANIZATION_CODE 组织机构代码证、7
//     * TAX_REGISTRATION 税务登记证、8
//     * BANK_ACCOUNT_LICENCE 银行开户许可证、9
    switch (type) {
      case 0:
        qualification.setIdCardFront(picPath);
        break;
      case 1:
        qualification.setIdCardBack(picPath);
        break;
      case 2:
        qualification.setBankCardFront(picPath);
        break;
      case 3:
        qualification.setBankCardBack(picPath);
        break;
      case 4:
        qualification.setPersonPhoto(picPath);
        break;
      case 5:
        qualification.setBussinessLicense(picPath);
        break;
      case 6:
        qualification.setBussinessCertificates(picPath);
        break;
      case 7:
        qualification.setOrganizationCode(picPath);
        break;
      case 8:
        qualification.setTaxRegistration(picPath);
        break;
      case 9:
        qualification.setBankAccountLicence(picPath);
        break;
      default:
        resultMap = new HashMap<>();
        resultMap.put("code", "309");
        resultMap.put("msg", "图片类型异常");
        return resultMap;
    }
    if ("true".equals(resultMap.get("active"))) {
      merchantUserLedger.setState(1);
      merchantUserLedgerService.saveLedger(merchantUserLedger);
    }
    repository.save(qualification);
    return resultMap;
  }

}
