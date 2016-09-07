package com.jifenke.lepluslive.user.repository;


import com.jifenke.lepluslive.user.domain.entities.WeiXinUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by wcg on 16/3/18.
 */
public interface WeiXinUserRepository extends JpaRepository<WeiXinUser, Long> {

  @Modifying
  @Query("UPDATE WeiXinUser u SET u.massRemain = u.massRemain - 1 WHERE u.massRemain > 0 AND DATE_FORMAT(u.sendMassDate,'%Y-%m-%d') != DATE_FORMAT(NOW(), '%Y-%m-%d')")
  public int editAllMassRemain();

}
