package com.jifenke.lepluslive.shortMessage.repository;

import com.jifenke.lepluslive.shortMessage.domain.entities.ReplyShortMessage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */
public interface ReplyShortMessageRepository extends JpaRepository<ReplyShortMessage, Long> {

  //日均接收
  @Query(value = "select count(*),DATE_FORMAT(receivetime, '%Y%m%d') from lejiauser_shortmessage where send_state=1 GROUP BY DATE_FORMAT(receivetime, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findDayReceiveCount();

  //在一段时间内退订用户
  @Query(value = "SELECT COUNT(content),DATE_FORMAT(receivetime, '%Y%m%d') FROM replyshortmessage WHERE DATE_FORMAT(receivetime, '%Y%m%d') IS NOT NULL and content = 'T' or content = 't'  GROUP BY DATE_FORMAT(receivetime, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findUserTD();

  //累计退订的人数

  @Query(value = "SELECT COUNT(content) FROM replyshortmessage WHERE  content = 'T' or content = 't' ", nativeQuery = true)
  Long findTotalUserTD();


  @Query(value = "SELECT * from replyshortmessage where phone=?1", nativeQuery = true)
  List<ReplyShortMessage> findByPhoneNumber(String phoneNumber);

//表格

  @Query(value =
      "select count(*) from short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id=b.id \n"
      + "where b.scene_sid=?1 and a.send_date between ?2 and ?3 and a.req_code='00'", nativeQuery = true)
  int sceneSucceedCount(String sceneSid, String startDate, String endDate);

  @Query(value =
      "select count(*) from short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id=b.id \n"
      + "where b.scene_sid=?1 and a.send_date between ?2 and ?3 and a.req_code!='00'", nativeQuery = true)
  int sceneDefeateCount(String sceneSid, String startDate, String endDate);

  @Query(value =
      "select count(DISTINCT ls.le_jia_user_id), DATE_FORMAT(ls.receivetime, '%Y%m%d')   from lejiauser_shortmessage ls LEFT JOIN short_message sm on ls.short_message_id=sm.id LEFT JOIN  short_message_scene sms on sm.short_message_scene_id=sms.id \n"
      + "where sms.scene_sid=?1 and ls.receivetime BETWEEN ?2 and ?3 GROUP BY DATE_FORMAT(receivetime, '%Y%m%d')", nativeQuery = true)
  List<Object[]> tuchUserCount(String sceneSid, String startDate, String endDate);

}
