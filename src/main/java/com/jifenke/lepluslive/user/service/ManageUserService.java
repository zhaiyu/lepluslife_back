package com.jifenke.lepluslive.user.service;

import com.jifenke.lepluslive.user.domain.entities.ManageUser;
import com.jifenke.lepluslive.user.repository.ManageUserRepository;

import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by wcg on 16/3/31.
 */
@Service
public class ManageUserService  {
  @Inject
  private ManageUserRepository manageUserRepository;


  public ManageUser checkUserExistOrNot(String name){
   return manageUserRepository.findUserByName(name);
  }

}
