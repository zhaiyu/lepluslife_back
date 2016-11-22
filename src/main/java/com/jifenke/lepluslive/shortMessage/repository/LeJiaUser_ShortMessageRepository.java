package com.jifenke.lepluslive.shortMessage.repository;


import com.jifenke.lepluslive.shortMessage.domain.entities.LeJiaUser_ShortMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by jfk on 2016/10/20.
 */
public interface LeJiaUser_ShortMessageRepository
    extends JpaRepository<LeJiaUser_ShortMessage, Long> {


  @Query(value = "select * from lejiauser_shortmessage where short_message_id=?1 and le_jia_user_id=?2", nativeQuery = true)
  LeJiaUser_ShortMessage findOneByUserIdAndShortMEssageId(Long shortMessageId, Long userId);

  //标题
  //累计触达用户
  @Query(value = "SELECT count(*) FROM(SELECT * FROM lejiauser_shortmessage where send_state=1 GROUP BY le_jia_user_id) a", nativeQuery = true)
  Long findTotalcustomer();

  //累计发放成功
  @Query(value = "SELECT COUNT(*) FROM lejiauser_shortmessage a   WHERE a.send_state=1", nativeQuery = true)
  Long findTotalSend();

}
