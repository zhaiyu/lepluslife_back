package com.jifenke.lepluslive.Address.repository;

import com.jifenke.lepluslive.Address.domain.entities.Address;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import com.jifenke.lepluslive.user.domain.entities.WeiXinUser;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wcg on 16/3/21.
 */
public interface AddressRepository extends JpaRepository<Address,Long>{
    Address findByLeJiaUser(LeJiaUser leJiaUser);
}
