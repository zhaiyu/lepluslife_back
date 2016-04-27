package com.jifenke.lepluslive.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wcg on 16/4/15.
 */
@Service
@Transactional(readOnly = true)
public class WeiXinUserService {

//  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
//  public

}
