package com.jifenke.lepluslive.management.service;

import com.jifenke.lepluslive.management.domain.entities.User_role;
import com.jifenke.lepluslive.management.repository.UserRoleRepository;
import com.jifenke.lepluslive.shiro.config.MyShiroRealm;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lss on 2016/8/3.
 */
@Service
public class UserRoleService {

  @Inject
  private UserRoleRepository userRoleRepository;
  @Inject
  private MyShiroRealm myShiroRealm;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<User_role> findUserRoleByUserId(Long id) {
    return userRoleRepository.findUserRoleByUserId(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveUser_role(List<User_role> user_roleList) {
    userRoleRepository.save(user_roleList);
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public List<User_role> findUserRoleByRoleId(Long roleId) {
    return userRoleRepository.findUserRoleByRoleId(roleId);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteUserRole(List<User_role> user_roleList) {
    userRoleRepository.delete(user_roleList);
  }

  public void clearCache() {
    myShiroRealm.clearCached();
  }

}
