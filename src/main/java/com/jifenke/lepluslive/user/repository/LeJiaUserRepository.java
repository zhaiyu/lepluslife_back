package com.jifenke.lepluslive.user.repository;

import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * Created by wcg on 16/3/24.
 */
public interface LeJiaUserRepository extends JpaRepository<LeJiaUser, Long> {

  Page findAll(Specification<LeJiaUser> whereClause, Pageable pageRequest);

  /**
   * 某个商户绑定会员数量 16/09/06
   *
   * @param merchantId 商户ID
   * @return 绑定会员数量
   */
  @Query(value = "SELECT COUNT(*) FROM le_jia_user WHERE le_jia_user.bind_merchant_id = ?1", nativeQuery = true)
  Integer countByBindMerchant(Long merchantId);

  /**
   * 统计某个注册来源的数量 16/09/06
   *
   * @param subSource 关注来源
   * @return 数量
   */
  @Query(value = "SELECT COUNT(*) FROM wei_xin_user WHERE sub_source=?1", nativeQuery = true)
  Integer countBySubSource(String subSource);

  /**
   * 某个商户邀请粉丝数 16/09/06
   *
   * @param subSource 关注来源
   * @return 数量
   */
  @Query(value = "SELECT COUNT(*) FROM wei_xin_user WHERE sub_source=?1 AND sub_state=1", nativeQuery = true)
  Integer countBySubSourceAndSubState(String subSource);

  /**
   * 某个商户邀请会员数 16/09/06
   *
   * @param subSource 关注来源
   * @return 数量
   */
  @Query(value = "SELECT COUNT(*) FROM wei_xin_user WHERE sub_source=?1 AND state=1", nativeQuery = true)
  Integer countBySubSourceAndState(String subSource);

  /**
   * 某个商户邀请会员的累计产生佣金 16/09/06
   *
   * @param subSource 关注来源
   * @return 数量
   */
  @Query(value = "SELECT SUM(lj_commission) FROM wei_xin_user u,off_line_order o WHERE u.le_jia_user_id=o.le_jia_user_id AND sub_source=?1 AND u.state=1 AND o.state=1 AND o.rebate_way=1", nativeQuery = true)
  Integer countLJCommissionByMerchant(String subSource);

  /**
   * 某个商户邀请会员的会员累计红包额和使用红包额 16/09/06
   *
   * @param subSource 关注来源
   * @return 数量
   */
  @Query(value = "SELECT SUM(a.total_score),SUM(a.total_score-a.score) FROM wei_xin_user u,scorea a WHERE u.le_jia_user_id=a.le_jia_user_id AND sub_source=?1 AND state=1", nativeQuery = true)
  List<Object[]> countScoreAByMerchant(String subSource);

  /**
   * 某个商户邀请会员的各种订单类型数量 16/09/06
   *
   * @param subSource 关注来源
   * @return 数量
   */
  @Query(value = "SELECT rebate_way,COUNT(*) FROM wei_xin_user u,off_line_order o WHERE u.le_jia_user_id=o.le_jia_user_id AND sub_source=?1 AND u.state=1 AND o.state=1 GROUP BY rebate_way", nativeQuery = true)
  List<Object[]> countOrderByMerchant(String subSource);

  /**
   * 某个商户绑定会员数量 16/09/21
   *
   * @param merchantId 商户ID
   * @return 绑定会员数量 (时间段)
   */
  @Query(value = "SELECT COUNT(*) FROM le_jia_user WHERE le_jia_user.bind_merchant_id = ?1 AND bind_merchant_date BETWEEN ?2 AND  ?3 ", nativeQuery = true)
  Integer countByBindMerchantAndDate(Long merchantId,String startDate,String endDate);
}
