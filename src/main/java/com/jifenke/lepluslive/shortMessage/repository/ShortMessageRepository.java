package com.jifenke.lepluslive.shortMessage.repository;

import com.jifenke.lepluslive.shortMessage.domain.entities.ShortMessage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */
public interface ShortMessageRepository extends JpaRepository<ShortMessage, Long> {

  Page findAll(Specification<ShortMessage> whereClause, Pageable pageRequest);

  @Query(value = "SELECT * from short_message where reqid=?1", nativeQuery = true)
  ShortMessage findByreqId(String reqId);

  @Query(value =
      "select b.user_sid,c.head_image_url,c.nickname,b.phone_number,s.send_date,a.send_state,s.content,s.user_amount from lejiauser_shortmessage a LEFT JOIN le_jia_user b on a.le_jia_user_id=b.id LEFT JOIN wei_xin_user c\n"
      +
      "on c.le_jia_user_id=b.id  LEFT JOIN short_message s on s.id=a.short_message_id  where a.short_message_id=?1 order by b.id", nativeQuery = true)
  List<Object[]> findShortMessageDetailsById(Long shortMessageId);


  @Query(value = "SELECT id from short_message where reqid=?1", nativeQuery = true)
  Long findIdByReqId(String reqId);


  //获得注册时短信所需要的变量
  @Query(value =
      "select w.nickname memberName  ,sa.total_score scoreATotal,sa.score scoreANow ,sb.score scoreBNow ,sb.total_score scoreBTotal,l.phone_number memberPhone  ,sad.number scoreARegister from le_jia_user l LEFT JOIN wei_xin_user w on l.id=w.le_jia_user_id LEFT JOIN scorea sa on l.id=sa.le_jia_user_id\n"
      + "          LEFT JOIN scorea_detail sad on sa.id=sad.scorea_id LEFT JOIN scoreb sb on l.id=sb.le_jia_user_id LEFT JOIN scoreb_detail sbd on sb.id=sbd.scoreb_id\n"
      + "          where l.user_sid=?1", nativeQuery = true)
  List<Object[]> findPublicVariable(String userSid);

  @Query(value =
      "select sbd.number scoreBRegister from le_jia_user l LEFT JOIN wei_xin_user w on l.id=w.le_jia_user_id LEFT JOIN scorea sa on l.id=sa.le_jia_user_id\n"
      + "LEFT JOIN scorea_detail sad on sa.id=sad.scorea_id LEFT JOIN scoreb sb on l.id=sb.le_jia_user_id LEFT JOIN scoreb_detail sbd on sb.id=sbd.scoreb_id\n"
      + "where l.user_sid=?1  and sbd.operate=\"注册送礼\"", nativeQuery = true)
  List<Object> findRegistryScoreB(String userSid);


  @Query(value =
      "select sad.number scoreARegister from le_jia_user l LEFT JOIN wei_xin_user w on l.id=w.le_jia_user_id LEFT JOIN scorea sa on l.id=sa.le_jia_user_id\n"
      + "          LEFT JOIN scorea_detail sad on sa.id=sad.scorea_id LEFT JOIN scoreb sb on l.id=sb.le_jia_user_id LEFT JOIN scoreb_detail sbd on sb.id=sbd.scoreb_id\n"
      + "          where l.user_sid=?1 and sad.operate=\"注册送礼\"", nativeQuery = true)
  List<Object> findRegistryScoreA(String userSid);

  @Query(value = "select sad.number from le_jia_user l LEFT JOIN scorea sa on l.id=sa.le_jia_user_id LEFT JOIN scorea_detail sad on sa.id=sad.scorea_id where l.user_sid=?1  and sad.origin=8 ORDER BY sad.date_created", nativeQuery = true)
  List<Object> findShareScoreA(String userSid);

  @Query(value = "select sbd.number from le_jia_user l LEFT JOIN scoreb sb on l.id=sb.le_jia_user_id LEFT JOIN scoreb_detail sbd on sb.id=sbd.scoreb_id where l.user_sid=?1  and sbd.origin=8 ORDER BY sbd.date_created", nativeQuery = true)
  List<Object> findShareScoreB(String userSid);

  @Query(value =
      "select ol.create_date,ol.order_sid,ol.total_price,ol.true_price,ol.true_score,ol.pay_backa from on_line_order ol LEFT JOIN  le_jia_user l on ol.le_jia_user_id=l.id "
      + "LEFT JOIN scorea sa on l.id=sa.le_jia_user_id LEFT JOIN scorea_detail sad on sa.id=sad.scorea_id LEFT JOIN scoreb sb on l.id=sb.le_jia_user_id "
      + "LEFT JOIN scoreb_detail sbd on sb.id=sbd.scoreb_id where ol.order_sid=?1", nativeQuery = true)
  List<Object[]> findOnLineOrderVariable(String onLineOrderSid);

  @Query(value =
      "select of.total_price,of.complete_date,of.order_sid,of.true_score,of.rebate,of.scoreb,m.name from off_line_order of LEFT JOIN  merchant m on of.merchant_id=m.id "
      + " LEFT JOIN  le_jia_user l on of.le_jia_user_id=l.id  where of.order_sid=?1", nativeQuery = true)
  List<Object[]> findOffLineOrderVariable(String offLineOrderSid);

  //发送条数
  @Query(value = "SELECT SUM(user_amount),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL GROUP BY DATE_FORMAT(send_date, '%Y%m%d') ", nativeQuery = true)
  List<Object[]> findSendCountAndDate();

  //触达用户
  @Query(value = "select count(DISTINCT le_jia_user_id),DATE_FORMAT(receivetime, '%Y%m%d') FROM lejiauser_shortmessage where  send_state=1 GROUP BY DATE_FORMAT(receivetime, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findSendSuccessedCount();

  //表格
  //手动发送成功条数
  @Query(value = "SELECT COUNT(*),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id = b.id WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND b.category='手动发送' AND a.submit_state = 1 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findSendByHand();

  //手动发送失败条数
  @Query(value = "SELECT COUNT(*),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id = b.id WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND b.category='手动发送' AND a.submit_state = 0 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findSendByHand0();

  //手动发送触达客户
  @Query(value = "SELECT COUNT(*),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id = b.id LEFT JOIN lejiauser_shortmessage c ON a.id = c.short_message_id WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND b.category = '手动发送'AND c.send_state = 1 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findSendByHandCus();


  //成功邀请好友成功条数
  @Query(value = "SELECT COUNT(*),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id = b.id WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND b.category='成功邀请好友' AND a.submit_state = 1 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findInviteFriend();

  //成功邀请好友失败条数
  @Query(value = "SELECT COUNT(*),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id = b.id WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND b.category='成功邀请好友' AND a.submit_state = 0 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findInviteFriend0();

  //邀请好友触达客户数
  @Query(value = "SELECT COUNT(*),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id = b.id LEFT JOIN lejiauser_shortmessage c ON a.id = c.short_message_id WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND b.category = '成功邀请好友'AND c.send_state = 1 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findInviteFriendCus();

  //线下消费发送成功条数
  @Query(value = "SELECT COUNT(*),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id = b.id WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND b.category='线下消费' AND a.submit_state = 1 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findOffLine();

  //线下消费发送失败条数
  @Query(value = "SELECT COUNT(*),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id = b.id WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND b.category='线下消费' AND a.submit_state = 0 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findOffLine0();

  //线下消费触达客户数
  @Query(value = "SELECT COUNT(*),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id = b.id LEFT JOIN lejiauser_shortmessage c ON a.id = c.short_message_id WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND b.category = '线下消费'AND c.send_state = 1 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findOffLineCus();


  //商品下单的发送成功条数
  @Query(value = "SELECT COUNT(*),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id = b.id WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND b.category='商品下单' AND a.submit_state = 1 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findProduct();

  //商品下单的发送失败条数
  @Query(value = "SELECT COUNT(*),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id = b.id WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND b.category='商品下单' AND a.submit_state = 0 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findProduct0();

  //商品下单触达的客户数
  @Query(value = "SELECT COUNT(*),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id = b.id LEFT JOIN lejiauser_shortmessage c ON a.id = c.short_message_id WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND b.category = '商品下单'AND c.send_state = 1 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findProductCus();

  //公众号注册发送成功条数
  @Query(value = "SELECT COUNT(*),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id = b.id WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND b.category='公共号注册' AND a.submit_state = 1 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findRegist();

  //公共号注册发送失败条数
  @Query(value = "SELECT COUNT(*),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id = b.id WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND b.category='公共号注册' AND a.submit_state = 0 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findRegist0();

  //公共号触达的客户数
  @Query(value = "SELECT COUNT(*),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id = b.id LEFT JOIN lejiauser_shortmessage c ON a.id = c.short_message_id WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND b.category = '公共号注册'AND c.send_state = 1 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findRegistCus();

  //APP邀请注册成功条数
  @Query(value = "SELECT COUNT(*),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id = b.id WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND b.category='APP邀请注册' AND a.submit_state = 0 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findAPPRegist();

  //APP邀请注册失败条数
  @Query(value = "SELECT COUNT(*),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id = b.id WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND b.category='APP邀请注册' AND a.submit_state = 1 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findAPPRegist0();

  //APP邀请触达的客户数
  @Query(value = "SELECT COUNT(*),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id = b.id LEFT JOIN lejiauser_shortmessage c ON a.id = c.short_message_id WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND b.category = 'APP邀请注册'AND c.send_state = 0 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findAPPRegistCus();


  //一段时间内累计发送失败的条数
  @Query(value = "SELECT SUM(user_amount),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND a.submit_state = 0 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findtotalfileintime();

  //一段时间内累计发送成功条数
  @Query(value = "SELECT SUM(user_amount),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND a.submit_state = 1 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findtotalsuccessintime();

  //一段时间内触达总客户数
  @Query(value = "SELECT COUNT(*),DATE_FORMAT(send_date, '%Y%m%d') FROM short_message a LEFT JOIN short_message_scene b ON a.short_message_scene_id = b.id LEFT JOIN lejiauser_shortmessage c ON a.id = c.short_message_id WHERE DATE_FORMAT(send_date, '%Y%m%d') IS NOT NULL AND  c.send_state = 1 GROUP BY DATE_FORMAT(send_date, '%Y%m%d')", nativeQuery = true)
  List<Object[]> findtotalcustomerintime();
}
