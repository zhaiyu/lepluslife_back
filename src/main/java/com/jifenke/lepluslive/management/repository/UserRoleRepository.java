package com.jifenke.lepluslive.management.repository;

import com.jifenke.lepluslive.management.domain.entities.User_role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public interface UserRoleRepository extends JpaRepository<User_role, Long> {


  @Query(value = "SELECT * FROM user_role WHERE user_id=?1", nativeQuery = true)
  List<User_role> findUserRoleByUserId(Long id);


  @Query(value = "SELECT * FROM user_role WHERE role_id=?1", nativeQuery = true)
  List<User_role> findUserRoleByRoleId(Long id);
}
