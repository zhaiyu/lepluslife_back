package com.jifenke.lepluslive.partner.service;

import com.jifenke.lepluslive.partner.domain.entities.PartnerScoreLog;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWallet;
import com.jifenke.lepluslive.partner.repository.PartnerScoreLogRepository;
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
public class PartnerScoreLogService {
    @Inject
    private PartnerScoreLogRepository partnerScoreLogRepository;


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void savePartnerScoreLog(PartnerScoreLog partnerScoreLog) {
        partnerScoreLogRepository.save(partnerScoreLog);
    }
}
