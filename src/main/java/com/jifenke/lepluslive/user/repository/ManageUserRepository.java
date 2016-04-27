package com.jifenke.lepluslive.user.repository;

import com.jifenke.lepluslive.user.domain.entities.ManageUser;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wcg on 16/3/31.
 */
public interface ManageUserRepository extends JpaRepository<ManageUser,Long> {

  ManageUser findUserByName(String name);
}
