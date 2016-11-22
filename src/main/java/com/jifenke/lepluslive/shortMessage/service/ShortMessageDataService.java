package com.jifenke.lepluslive.shortMessage.service;


import com.jifenke.lepluslive.shortMessage.repository.LeJiaUser_ShortMessageRepository;
import com.jifenke.lepluslive.shortMessage.repository.ReplyShortMessageRepository;
import com.jifenke.lepluslive.shortMessage.repository.ShortMessageRepository;
import com.jifenke.lepluslive.shortMessage.repository.ShortMessageSceneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;


@Service
@Transactional(readOnly = false)

public class ShortMessageDataService {

  @Inject
  private ShortMessageRepository shortMessageRepository;
  @Inject
  private ReplyShortMessageRepository replyShortMessageRepository;
  @Inject
  private LeJiaUser_ShortMessageRepository leJiaUser_shortMessageRepository;
  @Inject
  private ShortMessageSceneRepository shortMessageSceneRepository;

  //折线图
  //查找发送条数
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<Object[]> findSendCountAndDate() {
    return shortMessageRepository.findSendCountAndDate();
  }

  //日均接收
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<Object[]> findDayReceiveCount() {
    return replyShortMessageRepository.findDayReceiveCount();
  }

  //触达客户
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<Object[]> findSendSuccessedCount() {
    return shortMessageRepository.findSendSuccessedCount();
  }

  //累计退订用户
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<Object[]> findUserTD() {
    return replyShortMessageRepository.findUserTD();
  }
//表格

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public int sceneSucceedCount(String sceneSid, String startDate, String endDate) {
    return replyShortMessageRepository.sceneSucceedCount(sceneSid, startDate, endDate);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public int sceneDefeateCount(String sceneSid, String startDate, String endDate) {
    return replyShortMessageRepository.sceneDefeateCount(sceneSid, startDate, endDate);
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<Object[]> tuchUserCount(String sceneSid, String startDate, String endDate) {
    return replyShortMessageRepository.tuchUserCount(sceneSid, startDate, endDate);
  }
}
