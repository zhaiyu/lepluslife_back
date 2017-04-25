package com.jifenke.lepluslive.user.service;

import com.jifenke.lepluslive.user.domain.entities.BankCard;
import com.jifenke.lepluslive.user.repository.BankCardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by lss on 17-4-17.
 */
@Service
@Transactional(readOnly = true)
public class BankCardService {

    @Inject
    private BankCardRepository bankCardRepository;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveOne(BankCard bankCard){
        bankCardRepository.save(bankCard);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public BankCard findByNumber(String bankNumber){
      return   bankCardRepository.findByNumber(bankNumber);
    }


}
