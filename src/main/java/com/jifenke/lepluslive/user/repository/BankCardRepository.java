package com.jifenke.lepluslive.user.repository;

import com.jifenke.lepluslive.user.domain.entities.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by lss on 17-4-17.
 */
public interface BankCardRepository extends JpaRepository<BankCard, Long> {


    @Query(value = "SELECT * FROM bank_card   where number=?1 and state=1", nativeQuery = true)
    BankCard findByNumber(String bankNumber);

}
