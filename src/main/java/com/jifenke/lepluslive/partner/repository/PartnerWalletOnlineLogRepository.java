package com.jifenke.lepluslive.partner.repository;

import com.jifenke.lepluslive.partner.domain.entities.PartnerWalletLog;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWalletOnline;
import com.jifenke.lepluslive.partner.domain.entities.PartnerWalletOnlineLog;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wcg on 16/6/3.
 */
public interface PartnerWalletOnlineLogRepository extends JpaRepository<PartnerWalletOnlineLog, Long> {

}
