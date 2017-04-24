package com.jifenke.lepluslive.yinlian.service;

import com.jifenke.lepluslive.yinlian.domain.entities.MerchantUnionPos;
import com.jifenke.lepluslive.yinlian.repository.MerchantUnionPosRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lss on 2017/4/10.
 */
@Service
@Transactional(readOnly = true)
public class MerchantUnionPosService {
    @Inject
    private MerchantUnionPosRepository merchantUnionPosRepository;

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public MerchantUnionPos findByMerchantId(Long id) {
        return merchantUnionPosRepository.findByMerchantId(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<MerchantUnionPos> findListByMerchantId(Long id) {
        return merchantUnionPosRepository.findListByMerchantId(id);
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public MerchantUnionPos findById(Long id) {
        return merchantUnionPosRepository.findOne(id);
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void saveOne(MerchantUnionPos merchantUnionPos) {
        merchantUnionPosRepository.save(merchantUnionPos);
    }


}
