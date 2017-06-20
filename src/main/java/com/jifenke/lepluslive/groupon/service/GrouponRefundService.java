package com.jifenke.lepluslive.groupon.service;

import com.jifenke.lepluslive.groupon.repository.GrouponRefundRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * 退款管理 Service
 *
 * @author XF
 * @date 2017/6/20
 */
@Service
@Transactional(readOnly = true)
public class GrouponRefundService {
    @Inject
    private GrouponRefundRepository grouponRefundRepository;
}
