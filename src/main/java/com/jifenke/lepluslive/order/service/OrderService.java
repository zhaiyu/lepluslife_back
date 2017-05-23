package com.jifenke.lepluslive.order.service;

import com.jifenke.lepluslive.global.config.Constants;
import com.jifenke.lepluslive.job.OrderConfirmJob;
import com.jifenke.lepluslive.order.domain.entities.OnLineOrder;
import com.jifenke.lepluslive.order.domain.criteria.OrderCriteria;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.DartUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


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

  public Map<String, Object> accountTurnover() {
    Long count = orderRepository.countOrder();
    Long turnover = orderRepository.countAllTurnover();

    Long totalB = orderRepository.sumTotalScoreB();
    Long currB = orderRepository.sumCurrScoreb();
    Long totalA = orderRepository.sumTotalScoreA();
    Long currA = orderRepository.sumCurrScoreA();
    Object[] importOrder = orderRepository.importOrderCount().get(0);//导流订单总数和总金额
    Long share = orderRepository.totalShare(); //分润总金额
    Long membersCount = orderRepository.membersCount();

    Long unionMerchantCount = orderRepository.unionMerchantCount();

    HashMap<String, Object> map = new HashMap<>();
    map.put("totalB", totalB);
    map.put("totalA", totalA);
    map.put("currB", currB);
    map.put("currA", currA);
    map.put("orderCount", count);
    map.put("turnover", turnover);
    map.put("importOrderTotalPrice", importOrder[1]);
    map.put("importOrderCount", importOrder[0]);
    map.put("unionMerchantCount", unionMerchantCount);
    map.put("membersCount", membersCount);
    map.put("share", share);
    return map;
  }

  public Page findOrderByPage(OrderCriteria orderCriteria, Integer limit) {
    Sort sort = new Sort(Sort.Direction.DESC, "createDate");
    return orderRepository
        .findAll(getWhereClause(orderCriteria),
                 new PageRequest(orderCriteria.getOffset() - 1, limit, sort));
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void cancleOrder(Long id) {
    OnLineOrder onLineOrder = orderRepository.findOne(id);
    onLineOrder.setState(4);
    productService.orderCancle(onLineOrder.getOrderDetails());
    orderRepository.save(onLineOrder);
  }

  /**
   * 线上自提订单的确认完成  16/09/26
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void finishOrder(Long id) {
    OnLineOrder onLineOrder = orderRepository.findOne(id);
    Date date = new Date();
    onLineOrder.setDeliveryDate(date);
    onLineOrder.setConfirmDate(date);
    onLineOrder.setState(3);
    orderRepository.save(onLineOrder);
  }

  /**
   * 确认发货
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void orderDelivery(OnLineOrder onLineOrder, String expressCompany, String expressNumber) {

    onLineOrder.setExpressCompany(expressCompany);
    onLineOrder.setExpressNumber(expressNumber);
    onLineOrder.setDeliveryDate(new Date());
    onLineOrder.setState(2);
    //默认10天后会自动收货
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      Date
          time =
          sdf.parse(sdf.format(onLineOrder.getCreateDate().getTime() + Constants.ORDER_EXPIRED));
      JobDetail completedOrderJobDetail = JobBuilder.newJob(OrderConfirmJob.class)
          .withIdentity("OrderConfirmJob" + onLineOrder.getId(), jobGroupName)
          .usingJobData("orderId", onLineOrder.getId())
          .build();
      Trigger completedOrderJobTrigger = TriggerBuilder.newTrigger()
          .withIdentity(
              TriggerKey.triggerKey("autoOrderConfirmJobTrigger"
                                    + onLineOrder.getId(), triggerGroupName))
          .startAt(time)
          .build();
      scheduler.scheduleJob(completedOrderJobDetail, completedOrderJobTrigger);
      scheduler.start();

    } catch (Exception e) {
      e.printStackTrace();
    }
    orderRepository.save(onLineOrder);

  }

  /**
   * 修改物流信息
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void orderEditDelivery(OnLineOrder onLineOrder, String expressCompany,
                                String expressNumber) {

    onLineOrder.setExpressCompany(expressCompany);
    onLineOrder.setExpressNumber(expressNumber);
    orderRepository.save(onLineOrder);

  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public void changeOrderStateOver(Long id) {
    OnLineOrder onLineOrder = orderRepository.findOne(id);
    if (onLineOrder.getState() == 2) {
      onLineOrder.setState(3);
    }
    orderRepository.save(onLineOrder);
  }

  public Long countUserConsumptionTimes(LeJiaUser leJiaUser) {

    return orderRepository.countUserConsumptionTimes(leJiaUser.getId());

  }

  public static Specification<OnLineOrder> getWhereClause(OrderCriteria orderCriteria) {
    return new Specification<OnLineOrder>() {
      @Override
      public Predicate toPredicate(Root<OnLineOrder> r, CriteriaQuery<?> q,
                                   CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction();
        if (orderCriteria.getState() != null && orderCriteria.getState() != 5) {
          predicate.getExpressions().add(
              cb.equal(r.get("state"),
                       orderCriteria.getState()));
        }
        if (orderCriteria.getType() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("type"),
                       orderCriteria.getType()));
        }
        if (orderCriteria.getTransmitWay() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("transmitWay"),
                       orderCriteria.getTransmitWay()));
        }
        if (orderCriteria.getSource() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("source"),
                       orderCriteria.getSource()));
        }
        if (orderCriteria.getOrderSid() != null) {
          predicate.getExpressions().add(
              cb.like(r.get("orderSid"), "%" + orderCriteria.getOrderSid() + "%"));
        }

        if (orderCriteria.getStartDate() != null && !"".equals(orderCriteria.getStartDate())) {
          predicate.getExpressions().add(
              cb.between(r.get("createDate"), new Date(orderCriteria.getStartDate()),
                         new Date(orderCriteria.getEndDate())));
        }
        if (orderCriteria.getStartPayDate() != null && !""
            .equals(orderCriteria.getStartPayDate())) {
          predicate.getExpressions().add(
              cb.between(r.get("payDate"), new Date(orderCriteria.getStartPayDate()),
                         new Date(orderCriteria.getEndPayDate())));
        }
        if (orderCriteria.getPhoneNumber() != null && !"".equals(orderCriteria.getPhoneNumber())) {
          predicate.getExpressions().add(
              cb.like(r.get("address").get("phoneNumber"),
                      "%" + orderCriteria.getPhoneNumber() + "%"));
        }
        if (orderCriteria.getUserName() != null && !"".equals(orderCriteria.getUserName())) {
          predicate.getExpressions().add(
              cb.like(r.get("address").get("name"), "%" + orderCriteria.getUserName() + "%"));
        }

        if (orderCriteria.getMinTruePrice() != null) {
          predicate.getExpressions().add(
              cb.greaterThanOrEqualTo(r.get("truePrice"), orderCriteria.getMinTruePrice()));
        }

        if (orderCriteria.getMaxTruePrice() != null) {
          predicate.getExpressions().add(
              cb.lessThanOrEqualTo(r.get("truePrice"), orderCriteria.getMaxTruePrice()));
        }
        if (orderCriteria.getPayOrigin() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("payOrigin").get("payFrom"), orderCriteria.getPayOrigin()));
        }
        if (orderCriteria.getPayWay() != null) {
          predicate.getExpressions().add(
              cb.equal(r.get("payOrigin").get("payType"), orderCriteria.getPayWay()));
        }
        return predicate;
      }
    };
  }


  /**
   * 根据订单id查询线上订单信息
   */
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public OnLineOrder findOnLineOrderById(Long id) {
    return orderRepository.findOne(id);
  }
}
