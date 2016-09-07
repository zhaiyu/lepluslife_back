package com.jifenke.lepluslive.management.service;

import com.jifenke.lepluslive.user.domain.entities.ManageUser;
import com.jifenke.lepluslive.user.repository.ManageUserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.util.List;

/**
 * Created by lss on 2016/8/3.
 */

@Service
@Transactional(readOnly = false)
public class ManagementUserService {

  @Inject
  private ManageUserRepository manageUserRepository;


  public List<ManageUser> findAllManagementUser() {
    return manageUserRepository.findAll();
  }

  public ManageUser findManagementUserByID(Long id) {
    return manageUserRepository.findOne(id);
  }

  public void saveManagementUser(ManageUser manageuser) {
    manageUserRepository.saveAndFlush(manageuser);
  }

  public void deleteUser(Long id) {
    manageUserRepository.delete(id);
  }
}
