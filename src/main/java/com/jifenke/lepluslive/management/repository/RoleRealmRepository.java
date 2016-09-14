package com.jifenke.lepluslive.management.repository;

import com.jifenke.lepluslive.management.domain.entities.Role_realm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/7/26.
 */
public interface RoleRealmRepository extends JpaRepository<Role_realm, Long> {

  @Query(value = "SELECT role_percode FROM  role_realm WHERE role_id=?1", nativeQuery = true)
  List<String> findRealmByID(Long id);

  @Query(value = "DELETE  FROM role_realm  WHERE role_id =?1", nativeQuery = true)
  void deleteRoleMenuByRoleID(Long id);


  @Query(value = "SELECT * FROM  role_realm WHERE role_id=?1", nativeQuery = true)
  List<Role_realm> findRoleRealmByID(Long id);


}
