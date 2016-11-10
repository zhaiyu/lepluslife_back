package com.jifenke.lepluslive.shortMessage.service;

import com.jifenke.lepluslive.shortMessage.domain.entities.ReplyShortMessage;
import com.jifenke.lepluslive.shortMessage.repository.ReplyShortMessageRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/10/31.
 */

@Service
@Transactional(readOnly = false)
public class ReplyShortMessageService {

  @Inject
  private ReplyShortMessageRepository replyShortMessageRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<ReplyShortMessage> findByPhoneNumber(String phoneNumber) {

    return replyShortMessageRepository.findByPhoneNumber(phoneNumber);

  }

}
