package com.jifenke.lepluslive.order.service;

import com.jifenke.lepluslive.order.domain.entities.PosDailyBill;
import com.jifenke.lepluslive.order.repository.PosDailyBillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by xf on 16-11-3.
 */
@Service
@Transactional(readOnly = true)
public class PosDailyBillService {

    @Inject
    private PosDailyBillRepository posDailyBillRepository;


    /**
     * 分页查询 pos 账单
     *
     * @param pageSize
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List findPosDailyBillByPage(Integer offSet, Integer pageSize) {
        Integer startIndex = (offSet-1) * pageSize;
        return posDailyBillRepository.findByPage(startIndex, pageSize);
    }

    /**
     * 获取pos 账单的总页数
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Integer countDailyBillPages(Integer pageSize) {
        Long totalCount = posDailyBillRepository.count();
        Integer totalPages = new Double(Math.ceil(totalCount / ((pageSize-1)* 1.0))).intValue();
        return totalPages;
    }

    /**
     * 获取pos 账单总数
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Long countDailyBillNum() {
        return posDailyBillRepository.count();
    }

    /**
     * 根据 id 获取
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public PosDailyBill findById(Long id) {
        return posDailyBillRepository.findOne(id);
    }

}
