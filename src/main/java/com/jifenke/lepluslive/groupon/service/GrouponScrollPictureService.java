package com.jifenke.lepluslive.groupon.service;

import com.jifenke.lepluslive.groupon.domain.entities.GrouponProduct;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponScrollPicture;
import com.jifenke.lepluslive.groupon.repository.GrouponScrollPictureRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.inject.Inject;

/**
 * GrouponScrollPictureService
 * 产品轮播图
 *
 * @author XF
 * @date 2017/6/27
 */
@Service
@Transactional(readOnly = true)
public class GrouponScrollPictureService {
    @Inject
    private GrouponScrollPictureRepository scrollPictureRepository;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<GrouponScrollPicture> findByGrouponProduct(GrouponProduct grouponProduct) {
        return scrollPictureRepository.findByGrouponProduct(grouponProduct);
    }
}
