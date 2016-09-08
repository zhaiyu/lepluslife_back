package com.jifenke.lepluslive.management.service;

import com.jifenke.lepluslive.management.domain.entities.Role_realm;
import com.jifenke.lepluslive.management.repository.Role_realmRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by lss on 2016/9/5.
 */
@Service
@Transactional(readOnly = true)
public class Role_realmService {
  @Inject
  private Role_realmRepository role_realmRepository;



  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveRole_realm(Role_realm role_realm) {
    role_realmRepository.save(role_realm);
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<Role_realm> findByRoleId(Long id) {
  return   role_realmRepository.findByRoleId(id);
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteRole_realmList(List<Role_realm> role_realmList) {
       role_realmRepository.delete(role_realmList);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<Role_realm> findByRealmId(Long id) {
    return   role_realmRepository.findByRealmId(id);
  }
}
