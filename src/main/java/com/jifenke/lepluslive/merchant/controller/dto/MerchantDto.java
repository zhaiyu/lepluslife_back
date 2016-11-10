package com.jifenke.lepluslive.merchant.controller.dto;

import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantRebatePolicy;

/**
 * Created by xf on 16-11-9.
 */
public class MerchantDto {
    private Merchant merchant;
    private MerchantRebatePolicy merchantRebatePolicy;

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public MerchantRebatePolicy getMerchantRebatePolicy() {
        return merchantRebatePolicy;
    }

    public void setMerchantRebatePolicy(MerchantRebatePolicy merchantRebatePolicy) {
        this.merchantRebatePolicy = merchantRebatePolicy;
    }
}
