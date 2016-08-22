package com.jifenke.lepluslive.management.repository;

import com.jifenke.lepluslive.management.domain.entities.ManageRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/7/26.
 */
public interface RoleRepository extends JpaRepository<ManageRole, Long> {

  @Query(value = "SELECT id FROM managerole WHERE role_name=?1", nativeQuery = true)
  Long findIdByName(String name);


}
