package com.jifenke.lepluslive.order.service;

import com.jifenke.lepluslive.global.config.Constants;
import com.jifenke.lepluslive.job.OrderConfirmJob;
import com.jifenke.lepluslive.order.domain.entities.Order;
import com.jifenke.lepluslive.order.repository.OrderRepository;
import com.jifenke.lepluslive.product.service.ProductService;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


/**
 * Created by wcg on 16/4/1.
 */
@Service
@Transactional(readOnly = true)
public class OrderService {

  @Inject
  private OrderRepository orderRepository;
  @Inject
  private ProductService productService;

  @Inject
  private Scheduler scheduler;

  private static String jobGroupName = "ORDER_CONFIRM_JOBGROUP_NAME";
  private static String triggerGroupName = "ORDER_CONFIRM_TRIGGERGROUP_NAME";

  public Map<String,Long> accountTurnover(){
    Long count = orderRepository.countOrder();
    Long turnover = orderRepository.countAllTurnover();

    HashMap<String, Long> map = new HashMap<>();
  map.put("orderCount",count);
    map.put("turnover",turnover);
    return map;
  }

  public Page findOrderByPage(Integer offset) {
    if(offset==null){
      offset=1;
    }
    Sort sort = new Sort(Sort.Direction.DESC, "createDate");
  return orderRepository.findAll(new PageRequest(offset - 1, 10,sort));
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void cancleOrder(Long id) {
   Order order = orderRepository.findOne(id);
    order.setState(4);
    productService.orderCancle(order.getOrderDetails());
    orderRepository.save(order);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void orderDelivery(Long id) {
    Order order = orderRepository.findOne(id);

    order.setState(2);
    //默认10天后会自动收货
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      Date time = sdf.parse(sdf.format(order.getCreateDate().getTime() + Constants.ORDER_EXPIRED));
      JobDetail completedOrderJobDetail = JobBuilder.newJob(OrderConfirmJob.class)
          .withIdentity("OrderConfirmJob" + order.getId(), jobGroupName)
          .usingJobData("orderId", order.getId())
          .build();
      Trigger completedOrderJobTrigger = TriggerBuilder.newTrigger()
          .withIdentity(
              TriggerKey.triggerKey("autoOrderConfirmJobTrigger"
                                    + order.getId(), triggerGroupName))
          .startAt(time)
          .build();
      scheduler.scheduleJob(completedOrderJobDetail, completedOrderJobTrigger);
      scheduler.start();

    } catch (Exception e) {
      e.printStackTrace();
    }
    orderRepository.save(order);

  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void changeOrderStateOver(Long id) {
    Order order = orderRepository.findOne(id);
    if(order.getState()==2) {
      order.setState(3);
    }
    orderRepository.save(order);
  }

  public Long countUserConsumptionTimes(LeJiaUser leJiaUser) {

   return orderRepository.countUserConsumptionTimes(leJiaUser.getId());

  }
}
