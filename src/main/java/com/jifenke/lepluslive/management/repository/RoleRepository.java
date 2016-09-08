package com.jifenke.lepluslive.management.repository;

import com.jifenke.lepluslive.management.domain.entities.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/7/26.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

  @Query(value = "SELECT id FROM role WHERE role_name=?1", nativeQuery = true)
  Long findIdByName(String name);


}
