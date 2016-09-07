package com.jifenke.lepluslive.user.repository;


import com.jifenke.lepluslive.user.domain.entities.WeiXinUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by wcg on 16/3/18.
 */
public interface WeiXinUserRepository extends JpaRepository<WeiXinUser, Long> {

  @Modifying
  @Query("UPDATE WeiXinUser u SET u.massRemain = u.massRemain - 1 WHERE u.massRemain > 0 AND DATE_FORMAT(u.sendMassDate,'%Y-%m-%d') != DATE_FORMAT(NOW(), '%Y-%m-%d')")
  public int editAllMassRemain();

  /**
   * 所有商户邀请粉丝数 16/09/07
   *
   * @param subSource 关注来源
   * @return 数量
   */
  @Query(value = "SELECT COUNT(*) FROM wei_xin_user WHERE sub_source LIKE ?1 AND sub_state=1", nativeQuery = true)
  Integer countBySubSourceAndSubState(String subSource);

  /**
   * 所有商户邀请取消关注粉丝数 16/09/07
   *
   * @param subSource 关注来源
   * @return 数量
   */
  @Query(value = "SELECT COUNT(*) FROM wei_xin_user WHERE sub_source LIKE ?1 AND sub_state=2", nativeQuery = true)
  Integer countBySubSourceAndUnSub(String subSource);

  /**
   * 所有商户邀请会员数 16/09/07
   *
   * @param subSource 关注来源
   * @return 数量
   */
  @Query(value = "SELECT COUNT(*) FROM wei_xin_user WHERE sub_source LIKE ?1 AND state=1", nativeQuery = true)
  Integer countBySubSourceAndState(String subSource);

  /**
   * 所有商户邀请会员的累计产生佣金 16/09/07
   *
   * @param subSource 关注来源
   * @return 数量
   */
  @Query(value = "SELECT SUM(lj_commission) FROM wei_xin_user u,off_line_order o WHERE u.le_jia_user_id=o.le_jia_user_id AND sub_source LIKE ?1 AND u.state=1 AND o.state=1 AND o.rebate_way=1", nativeQuery = true)
  Integer countLJCommissionByMerchants(String subSource);

  /**
   * 所有商户邀请会员的会员累计红包额和使用红包额 16/09/07
   *
   * @param subSource 关注来源
   * @return 数量
   */
  @Query(value = "SELECT SUM(a.total_score),SUM(a.total_score-a.score) FROM wei_xin_user u,scorea a WHERE u.le_jia_user_id=a.le_jia_user_id AND sub_source LIKE ?1 AND state=1", nativeQuery = true)
  List<Object[]> countScoreAByMerchants(String subSource);

}
