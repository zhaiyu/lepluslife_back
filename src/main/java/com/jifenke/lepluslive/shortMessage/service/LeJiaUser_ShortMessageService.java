package com.jifenke.lepluslive.shortMessage.service;

import com.jifenke.lepluslive.shortMessage.domain.entities.LeJiaUser_ShortMessage;
import com.jifenke.lepluslive.shortMessage.repository.LeJiaUser_ShortMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/10/24.
 */
@Service
@Transactional(readOnly = false)
public class LeJiaUser_ShortMessageService {

  @Inject
  private LeJiaUser_ShortMessageRepository leJiaUser_ShortMessageRepository;


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public LeJiaUser_ShortMessage findOneByUserIdAndShortMEssageId(Long shortMessageId, Long userId) {

    return leJiaUser_ShortMessageRepository
        .findOneByUserIdAndShortMEssageId(shortMessageId, userId);

  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveOne(LeJiaUser_ShortMessage leJiaUser_ShortMessage) {
    leJiaUser_ShortMessageRepository.save(leJiaUser_ShortMessage);

  }
}
