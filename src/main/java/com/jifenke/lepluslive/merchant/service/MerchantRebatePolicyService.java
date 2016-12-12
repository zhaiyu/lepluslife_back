package com.jifenke.lepluslive.merchant.service;

import com.jifenke.lepluslive.merchant.domain.entities.MerchantRebatePolicy;
import com.jifenke.lepluslive.merchant.repository.MerchantRebatePolicyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;

/**
 * Created by xf on 16-11-9.
 */
@Transactional(readOnly = true)
@Service
public class MerchantRebatePolicyService {
    @Inject
    private MerchantRebatePolicyRepository merchantRebatePolicyRepository;

    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public void saveMerchantRebatePolicy(MerchantRebatePolicy merchantRebatePolicy) {
        // 默认值：日后需改动
        merchantRebatePolicy.setStageOne(0);
        merchantRebatePolicy.setStageTwo(0);
        merchantRebatePolicy.setStageThree(0);
        merchantRebatePolicy.setStageFour(0);
        if(merchantRebatePolicy.getRebateFlag()==0) {
            merchantRebatePolicy.setUserScoreBScaleB(new BigDecimal(0));
        }
        if(merchantRebatePolicy.getRebateFlag()==1) {
            merchantRebatePolicy.setUserScoreBScale(new BigDecimal(0));
            merchantRebatePolicy.setUserScoreAScale(new BigDecimal(0));
        }
        if(merchantRebatePolicy.getRebateFlag()==2) {
            policyReset(merchantRebatePolicy);
        }
        merchantRebatePolicyRepository.save(merchantRebatePolicy);
    }

    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public MerchantRebatePolicy findByMerchant(Long merchantId) {
        return merchantRebatePolicyRepository.findByMerchantId(merchantId);
    }

    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public void editMerchantRebatePolicy(MerchantRebatePolicy merchantRebatePolicy) {
        MerchantRebatePolicy rebatePolicy = merchantRebatePolicyRepository.findOne(merchantRebatePolicy.getId());
        rebatePolicy.setImportScoreBScale(merchantRebatePolicy.getImportScoreBScale());
        rebatePolicy.setRebateFlag(merchantRebatePolicy.getRebateFlag());
        if(merchantRebatePolicy.getRebateFlag()==0) {
            rebatePolicy.setUserScoreBScaleB(new BigDecimal(0));
            rebatePolicy.setUserScoreAScale(merchantRebatePolicy.getUserScoreAScale());
            rebatePolicy.setUserScoreBScale(merchantRebatePolicy.getUserScoreBScale());
        }
        if(merchantRebatePolicy.getRebateFlag()==1) {
            rebatePolicy.setUserScoreBScaleB(merchantRebatePolicy.getUserScoreBScaleB());
            rebatePolicy.setUserScoreBScale(new BigDecimal(0));
            rebatePolicy.setUserScoreAScale(new BigDecimal(0));
        }
        if(merchantRebatePolicy.getRebateFlag()==2) {
            policyReset(rebatePolicy);
        }
        merchantRebatePolicyRepository.save(rebatePolicy);
    }

    public void policyReset(MerchantRebatePolicy rebatePolicy) {
        rebatePolicy.setImportScoreBScale(new BigDecimal(0));
        rebatePolicy.setUserScoreBScaleB(new BigDecimal(0));
        rebatePolicy.setUserScoreBScale(new BigDecimal(0));
        rebatePolicy.setUserScoreAScale(new BigDecimal(0));
    }
}
