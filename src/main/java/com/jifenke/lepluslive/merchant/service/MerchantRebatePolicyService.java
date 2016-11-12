package com.jifenke.lepluslive.merchant.service;

import com.jifenke.lepluslive.merchant.domain.entities.MerchantRebatePolicy;
import com.jifenke.lepluslive.merchant.repository.MerchantRebatePolicyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

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
        merchantRebatePolicy.setStageOne(10);
        merchantRebatePolicy.setStageTwo(80);
        merchantRebatePolicy.setStageThree(6);
        merchantRebatePolicy.setStageFour(3);
        merchantRebatePolicyRepository.save(merchantRebatePolicy);
    }

    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public MerchantRebatePolicy findByMerchant(Long merchantId) {
        return merchantRebatePolicyRepository.findByMerchantId(merchantId);
    }

    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public void editMerchantRebatePolicy(MerchantRebatePolicy merchantRebatePolicy) {
        MerchantRebatePolicy rebatePolicy = merchantRebatePolicyRepository.findOne(merchantRebatePolicy.getId());
        rebatePolicy.setUserScoreAScale(merchantRebatePolicy.getUserScoreAScale());
        rebatePolicy.setUserScoreBScale(merchantRebatePolicy.getUserScoreBScale());
        rebatePolicy.setUserScoreBScaleB(merchantRebatePolicy.getUserScoreBScaleB());
        rebatePolicy.setImportScoreBScale(merchantRebatePolicy.getImportScoreBScale());
        rebatePolicy.setRebateFlag(merchantRebatePolicy.getRebateFlag());
        merchantRebatePolicyRepository.save(rebatePolicy);
    }
}
