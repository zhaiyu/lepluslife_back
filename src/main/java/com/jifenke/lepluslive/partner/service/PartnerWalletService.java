package com.jifenke.lepluslive.partner.service;

import com.jifenke.lepluslive.partner.domain.entities.PartnerWallet;
import com.jifenke.lepluslive.partner.repository.PartnerWalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by lss on 16-11-21.
 */
@Service
@Transactional(readOnly = false)
public class PartnerWalletService {
    @Inject
    private PartnerWalletRepository partnerWalletRepository;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PartnerWallet findOne(Long id) {
        return partnerWalletRepository.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void savePartnerWallet(PartnerWallet partnerWallet) {
         partnerWalletRepository.save(partnerWallet);
    }

}
