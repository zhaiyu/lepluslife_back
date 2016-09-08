package com.jifenke.lepluslive.management.service;

import com.jifenke.lepluslive.management.domain.entities.Realm;
import com.jifenke.lepluslive.management.repository.RealmRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by lss on 2016/9/2.
 */
@Service
@Transactional(readOnly = true)
public class RealmService {
  @Inject
  private RealmRepository realmRepository;

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<Realm> findAll() {
   return realmRepository.findAll();
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public Realm findRealmById(Long id) {
    return realmRepository.findOne(id);
  }




  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void save(Realm realm) {
     realmRepository.save(realm);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteRealm(Realm realm) {
    realmRepository.delete(realm);
  }
}
