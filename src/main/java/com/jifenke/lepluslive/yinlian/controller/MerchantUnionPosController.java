package com.jifenke.lepluslive.yinlian.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.service.MerchantService;
import com.jifenke.lepluslive.yinlian.domain.criteria.MerchantUnionPosCriteria;
import com.jifenke.lepluslive.yinlian.domain.entities.MerchantUnionPos;
import com.jifenke.lepluslive.yinlian.service.MerchantUnionPosService;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lss on 2017/4/11.
 */
@RestController
@RequestMapping("/manage/merchantUnionPos")
public class MerchantUnionPosController {


    @Inject
    private MerchantService merchantService;
    @Inject
    private MerchantUnionPosService merchantUnionPosService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult add(@RequestBody MerchantUnionPosCriteria merchantUnionPosCriteria) {
        String merchantSid = merchantUnionPosCriteria.getMerchantSid();
        Merchant merchant = merchantService.findMerchantByMerchantSid(merchantSid);
        if (merchant == null) {
            return LejiaResult.build(501, "未找到相关乐加门店信息");
        } else {
            List<MerchantUnionPos> merchantUnionPosList = merchantUnionPosService.findListByMerchantId(merchant.getId());
            if (merchantUnionPosList.size() > 0) {
                return LejiaResult.build(501, "银联商务结算规则已存在，pos添加成功");
            } else {
                MerchantUnionPos merchantUnionPos = new MerchantUnionPos();
                merchantUnionPos.setCommission(BigDecimal.valueOf(Double.valueOf(merchantUnionPosCriteria.getCommission())));
                merchantUnionPos.setMerchantId(merchant.getId());
                merchantUnionPos.setScoreARebate(BigDecimal.valueOf(Double.valueOf(merchantUnionPosCriteria.getScoreARebate())));
                merchantUnionPos.setScoreBRebate(BigDecimal.valueOf(Double.valueOf(merchantUnionPosCriteria.getScoreBRebate())));
                merchantUnionPos.setUseCommission(merchantUnionPosCriteria.getUseCommission());
                merchantUnionPos.setUserScoreARebate(BigDecimal.valueOf(Double.valueOf(merchantUnionPosCriteria.getUserScoreARebate())));
                merchantUnionPos.setUserScoreBRebate(BigDecimal.valueOf(Double.valueOf(merchantUnionPosCriteria.getUserScoreBRebate())));
                merchantUnionPos.setUserGeneralACommission(BigDecimal.valueOf(Double.valueOf(merchantUnionPosCriteria.getUserGeneralACommission())));
                merchantUnionPos.setIsNonCardCommission(merchantUnionPosCriteria.getIsNonCardCommission());
                merchantUnionPosService.saveOne(merchantUnionPos);
            }
        }
        return LejiaResult.ok();
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult edit(@RequestBody MerchantUnionPosCriteria merchantUnionPosCriteria) {
        String merchantSid = merchantUnionPosCriteria.getMerchantSid();
        MerchantUnionPos merchantUnionPos = merchantUnionPosService.findById(merchantUnionPosCriteria.getMerchantUnionPosId());
        Merchant merchant2 = merchantService.findMerchantByMerchantSid(merchantSid);
        if (merchantUnionPos == null) {
            return LejiaResult.build(501, "未找到相关银联商务结算规则");
        } else {
            if (merchant2 == null) {
                return LejiaResult.build(501, "未找到相关乐加门店信息");
            } else {
                List<MerchantUnionPos> merchantUnionPosList = merchantUnionPosService.findListByMerchantId(merchant2.getId());
                if (merchantUnionPosList.size() > 1) {
                    return LejiaResult.build(501, "乐加门店相关的银联商务结算规则信息重复，联系管理员");
                } else {
                    merchantUnionPos.setCommission(BigDecimal.valueOf(Double.valueOf(merchantUnionPosCriteria.getCommission())));
                    merchantUnionPos.setMerchantId(merchant2.getId());
                    merchantUnionPos.setScoreARebate(BigDecimal.valueOf(Double.valueOf(merchantUnionPosCriteria.getScoreARebate())));
                    merchantUnionPos.setScoreBRebate(BigDecimal.valueOf(Double.valueOf(merchantUnionPosCriteria.getScoreBRebate())));
                    merchantUnionPos.setUseCommission(merchantUnionPosCriteria.getUseCommission());
                    merchantUnionPos.setUserScoreARebate(BigDecimal.valueOf(Double.valueOf(merchantUnionPosCriteria.getUserScoreARebate())));
                    merchantUnionPos.setUserScoreBRebate(BigDecimal.valueOf(Double.valueOf(merchantUnionPosCriteria.getUserScoreBRebate())));
                    merchantUnionPos.setUserGeneralACommission(BigDecimal.valueOf(Double.valueOf(merchantUnionPosCriteria.getUserGeneralACommission())));
                    merchantUnionPos.setIsNonCardCommission(merchantUnionPosCriteria.getIsNonCardCommission());
                    merchantUnionPosService.saveOne(merchantUnionPos);
                }

            }
        }
        return LejiaResult.ok();
    }

}
