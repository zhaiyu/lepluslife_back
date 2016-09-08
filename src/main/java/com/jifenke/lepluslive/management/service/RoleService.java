package com.jifenke.lepluslive.management.service;

import com.jifenke.lepluslive.management.domain.entities.Role;
import com.jifenke.lepluslive.management.domain.entities.Role_realm;
import com.jifenke.lepluslive.management.repository.RoleRealmRepository;
import com.jifenke.lepluslive.management.repository.RoleRepository;
import com.jifenke.lepluslive.management.repository.UserRoleRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/26.
 */
@Service
@Transactional(readOnly = false)
public class RoleService {

  @Inject
  private RoleRepository roleRepository;

  @Inject
  private RoleRealmRepository roleRealmRepository;

  @Inject
  private UserRoleRepository userRoleRepository;


  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void saveRole(Role role) {
    roleRepository.save(role);
  }

  public Long serchId(String rolename) {
    return roleRepository.findIdByName(rolename);
  }

  public void saveRoleRealm(List<Role_realm> list) {
    roleRealmRepository.save(list);
  }

  public List<Role> findAllRole() {
    return roleRepository.findAll();
  }

  public List<String> findRoleRealmByID(Long id) {
    return roleRealmRepository.findRealmByID(id);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteRoleMenuByRoleID(Long id) {
    List<Role_realm> list = new ArrayList<Role_realm>();
    list = roleRealmRepository.findRoleRealmByID(id);
    if (list.size() != 0) {
      for (int i = 0; i < list.size(); i++) {
        roleRealmRepository.delete(list.get(i));
      }

    }
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteRole(Long id) {
    Role role = new Role();
    role = roleRepository.findOne(id);
    if (role != null) {
      roleRepository.delete(role);
    }
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Role findRoleById(Long id) {
    return roleRepository.findOne(id);
  }
}
