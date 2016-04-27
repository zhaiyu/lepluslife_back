package com.jifenke.lepluslive.user.service;

import com.jifenke.lepluslive.user.repository.LeJiaUserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by wcg on 16/4/22.
 */
@Service
@Transactional(readOnly = true)
public class UserService {
  @Inject
  private LeJiaUserRepository leJiaUserRepository;


  public Page findUserByPage(Integer offset){
    if(offset==null){
      offset=1;
    }
    Sort sort = new Sort(Sort.Direction.DESC, "createDate");
    return leJiaUserRepository.findAll(new PageRequest(offset - 1, 10,sort));
  }

}
