package com.jifenke.lepluslive.partner.service;

import com.jifenke.lepluslive.partner.domain.entities.Partner;
import com.jifenke.lepluslive.partner.domain.entities.PartnerManager;
import com.jifenke.lepluslive.partner.repository.PartnerManagerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by xf on 17-3-17.
 */
@Service
@Transactional(readOnly = true)
public class PartnerManagerService {
    @Inject
    private PartnerManagerRepository partnerManagerRepository;


    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public PartnerManager findPartnerManagerById(Long id) {
        return partnerManagerRepository.findOne(id);
    }
}
