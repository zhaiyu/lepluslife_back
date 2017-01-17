package com.jifenke.lepluslive.printer.repository;

import com.jifenke.lepluslive.printer.domain.entities.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by lss on 2016/12/27.
 */
public interface ReceiptRepository extends JpaRepository<Receipt,Long> {

    Page findAll(Specification<Receipt> whereClause, Pageable pageRequest);

    @Query(value = "SELECT * from receipt where receipt_sid=?1", nativeQuery = true)
    Receipt findBySid(String sid);


    @Query(value = "  SELECT * FROM   receipt  where complete_date in (SELECT max(complete_date) from (SELECT * from receipt where  state=0 and printer_id=?1)a )", nativeQuery = true)
    Receipt printUnsuccessfulOrder(Long printer_id);

}
