package com.jifenke.lepluslive.groupon.repository;

import com.jifenke.lepluslive.groupon.domain.entities.GrouponOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 团购订单 Repository
 * Created by xf on 17-6-16.
 */
public interface GrouponOrderRepository extends JpaRepository<GrouponOrder,Long> {

}
