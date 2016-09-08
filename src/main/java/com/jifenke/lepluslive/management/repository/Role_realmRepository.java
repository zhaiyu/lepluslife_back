package com.jifenke.lepluslive.management.repository;

import com.jifenke.lepluslive.management.domain.entities.Role_realm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2016/9/5.
 */
public interface Role_realmRepository extends JpaRepository<Role_realm,Long> {


  @Query(value = "SELECT * FROM role_realm WHERE role_id=?1", nativeQuery = true)
  List<Role_realm> findByRoleId(Long id);


  @Query(value = "SELECT * FROM role_realm WHERE realm_id=?1", nativeQuery = true)
  List<Role_realm> findByRealmId(Long id);



  @Query(value = "SELECT * FROM role_realm WHERE role_id=?1", nativeQuery = true)
  List<Role_realm> findRealmIdByRoleId(Long id);
}
