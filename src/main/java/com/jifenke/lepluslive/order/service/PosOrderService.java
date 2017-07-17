package com.jifenke.lepluslive.order.service;

import com.jifenke.lepluslive.global.util.DataUtils;
import com.jifenke.lepluslive.merchant.domain.entities.City;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantPos;
import com.jifenke.lepluslive.merchant.repository.MerchantRepository;
import com.jifenke.lepluslive.merchant.service.MerchantPosService;
import com.jifenke.lepluslive.order.domain.criteria.OLOrderCriteria;
import com.jifenke.lepluslive.order.domain.criteria.PosOrderCriteria;
import com.jifenke.lepluslive.order.domain.entities.PosDailyBill;
import com.jifenke.lepluslive.order.domain.entities.PosErrorLog;
import com.jifenke.lepluslive.order.domain.entities.PosOrder;
import com.jifenke.lepluslive.order.repository.PosDailyBillRepository;
import com.jifenke.lepluslive.order.repository.PosErrorLogRepository;
import com.jifenke.lepluslive.order.repository.PosOrderRepository;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

import com.jifenke.lepluslive.user.repository.LeJiaUserRepository;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by wcg on 16/9/5.
 */
@Service
@Transactional(readOnly = true)
public class PosOrderService {

    @Inject
    private PosOrderRepository posOrderRepository;

    @Inject
    private PosDailyBillRepository posDailyBillRepository;

    @Inject
    private MerchantPosService merchantPosService;

    @Inject
    private PosErrorLogRepository posErrorLogRepository;

    @Inject
    private LeJiaUserRepository leJiaUserRepository;

    @Inject
    private MerchantRepository merchantRepository;

    @Inject
    private EntityManager entityManager;


    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page findOrderByPage(PosOrderCriteria posOrderCriteria, int limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdDate");
        return posOrderRepository
                .findAll(getWhereClause(posOrderCriteria),
                        new PageRequest(posOrderCriteria.getOffset() - 1, limit, sort));
    }

    public static Specification<PosOrder> getWhereClause(PosOrderCriteria orderCriteria) {
        return new Specification<PosOrder>() {
            @Override
            public Predicate toPredicate(Root<PosOrder> r, CriteriaQuery<?> q,
                                         CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();

                if (orderCriteria.getUserPhone() != null && !"".equals(orderCriteria.getUserPhone())) {
                    predicate.getExpressions().add(
                            cb.like(r.<LeJiaUser>get("leJiaUser").get("phoneNumber"),
                                    "%" + orderCriteria.getUserPhone() + "%"));
                }
                if (orderCriteria.getUserSid() != null && !"".equals(orderCriteria.getUserSid())) {
                    predicate.getExpressions().add(
                            cb.like(r.<LeJiaUser>get("leJiaUser").get("userSid"),
                                    "%" + orderCriteria.getUserSid() + "%"));
                }

                if (orderCriteria.getStartDate() != null && !"".equals(orderCriteria.getStartDate())) {
                    predicate.getExpressions().add(
                            cb.between(r.get("completeDate"), new Date(orderCriteria.getStartDate()),
                                    new Date(orderCriteria.getEndDate())));
                }

                if (orderCriteria.getTradeFlag() != null) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("tradeFlag"),
                                    orderCriteria.getTradeFlag()));
                }
                if (orderCriteria.getRebateWay() != null) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("rebateWay"),
                                    orderCriteria.getRebateWay()));
                }
                if (orderCriteria.getState() != null) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("state"),
                                    orderCriteria.getState()));
                }
                if (orderCriteria.getMerchantLocation() != null) {
                    predicate.getExpressions().add(
                            cb.equal(r.get("merchant").<City>get("city"),
                                    new City(orderCriteria.getMerchantLocation())));
                }
                if (orderCriteria.getMerchant() != null && !"".equals(orderCriteria.getMerchant())) {
                    if (orderCriteria.getMerchant().matches("^\\d{1,6}$")) {
                        predicate.getExpressions().add(
                                cb.like(r.<Merchant>get("merchant").get("merchantSid"),
                                        "%" + orderCriteria.getMerchant() + "%"));
                    } else {
                        predicate.getExpressions().add(
                                cb.like(r.<Merchant>get("merchant").get("name"),
                                        "%" + orderCriteria.getMerchant() + "%"));
                    }
                }
                return predicate;
            }
        };
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void savePosDailyBill(PosDailyBill posDailyBill) {
        posDailyBillRepository.save(posDailyBill);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Merchant checkOrder(String posId, String orderSid, String paidMoney, String transferMoney,
                               String paidResult, String completeDate, PosDailyBill posDailyBill)
            throws ParseException {
        Merchant merchant = null;
        PosOrder posOrder = posOrderRepository.findByOrderSid(orderSid);
        BigDecimal truePay = new BigDecimal(paidMoney).multiply(new BigDecimal(100));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(completeDate);
        Long
                truePayCommission =
                truePay.subtract(new BigDecimal(transferMoney).multiply(new BigDecimal(100))).longValue();
        if (posOrder != null) {//订单存在
            if("1".equals(paidResult)){

            if (posOrder.getState() == 1 && posOrder.getTruePay() == truePay.longValue()
                    && truePayCommission
                    .equals(posOrder.getTruePayCommission())) {
                return merchant;
            } else {
                //记录差错日志
                PosErrorLog posErrorLog = new PosErrorLog();
                posErrorLog.setCreateDate(date);
                posErrorLog.setOrderSid(orderSid);
                posErrorLog.setLocalState(posOrder.getState());
                posErrorLog.setLocalCommission(posOrder.getTruePayCommission());
                posErrorLog.setLocalTruePay(posOrder.getTruePay());
                posErrorLog.setRemoteCommission(truePayCommission);
                posErrorLog.setRemoteTruePay(truePay.longValue());
                posErrorLog.setPosDailyBill(posDailyBill);
                posErrorLogRepository.save(posErrorLog);

                posOrder.setTruePay(truePay.longValue());
                posOrder.setTruePayCommission(truePayCommission);
                posOrder.setState(1);
                posOrderRepository.save(posOrder);
                return posOrder.getMerchant();
            }
            }else if("0".equals(paidResult)){ //对账单显示未结算
                if(posOrder.getState()==1){ //但是posorder显示完成
                    PosErrorLog posErrorLog = new PosErrorLog();
                    posErrorLog.setCreateDate(date);
                    posErrorLog.setOrderSid(orderSid);
                    posErrorLog.setLocalState(posOrder.getState());
                    posErrorLog.setLocalCommission(posOrder.getTruePayCommission());
                    posErrorLog.setLocalTruePay(posOrder.getTruePay());
                    posErrorLog.setRemoteCommission(truePayCommission);
                    posErrorLog.setRemoteTruePay(truePay.longValue());
                    posErrorLog.setPosDailyBill(posDailyBill);
                    posErrorLog.setRemoteState(0);
                    posErrorLogRepository.save(posErrorLog);

                    posOrder.setTruePay(truePay.longValue());
                    posOrder.setTruePayCommission(truePayCommission);
                    posOrder.setState(0);
                    posOrderRepository.save(posOrder);
                    return posOrder.getMerchant();
                }
            }
        } else {//订单不存在
            posOrder.setTruePay(truePay.longValue());
            posOrder.setTruePayCommission(truePayCommission);
            posOrder.setTotalPrice(truePay.longValue());
            posOrder.setTrueScore(0L);
            posOrder.setLjCommission(truePayCommission);
            posOrder.setRebateWay(1);
            posOrder.setTransferByBank(
                    new BigDecimal(transferMoney).multiply(new BigDecimal(100)).longValue());
            posOrder.setCreatedDate(date);
            posOrder.setCompleteDate(date);
            posOrder.setState(1);
            MerchantPos merchantPos = merchantPosService.findPosByPosId(posId);
            merchant = merchantPos.getMerchant();
            posOrder.setMerchant(merchant);
            posOrder.setMerchantPos(merchantPos);
            posOrderRepository.save(posOrder);
            PosErrorLog posErrorLog = new PosErrorLog();
            posErrorLog.setCreateDate(date);
            posErrorLog.setOrderSid(orderSid);
            posErrorLog.setLocalState(2);
            posErrorLog.setLocalCommission(posOrder.getTruePayCommission());
            posErrorLog.setLocalTruePay(posOrder.getTruePay());
            posErrorLog.setRemoteCommission(truePayCommission);
            posErrorLog.setRemoteTruePay(truePay.longValue());
            posErrorLog.setPosDailyBill(posDailyBill);
            posErrorLogRepository.save(posErrorLog);
            return merchant;
        }
        return null;
    }

    /**
     * 分页查找 订单文件(有错误记录)
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<PosDailyBill> findErrorPosDailyBill(Integer offset, Integer pageSize) {
        Integer startIndex = (offset - 1) * pageSize;
        return posDailyBillRepository.findErrorBillByPage(startIndex, pageSize);
    }

    /**
     * 分页查找 订单文件-总记录数  (有错误记录)
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Long countErroPosDailyBill() {
        return posDailyBillRepository.countErrorBill();
    }

    /**
     * 分页查找 订单文件-总页数  (有错误记录)
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Integer pageCountErroPosDailyBill(Integer pageSize) {
        Long countErrorBill = posDailyBillRepository.countErrorBill();
        Integer pageCount = new Double(Math.ceil((countErrorBill / (pageSize * 1.0)))).intValue();
        return pageCount;
    }


    /***
     *  根据订单文件查找相应的错误记录
     * @param dailyBills
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<List<PosErrorLog>> findPosErrorLogByBill(List<PosDailyBill> dailyBills) {
        List<List<PosErrorLog>> errlogsList = new ArrayList<>();
        for (PosDailyBill dailyBill : dailyBills) {
            List<PosErrorLog> errorLogs = posErrorLogRepository.findByPosDailyBill(dailyBill);
            errlogsList.add(errorLogs);
        }
        return errlogsList;
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void exportPosOrderData(OutputStream out, PosOrderCriteria posOrderCriteria) {

        Integer limit = posOrderRepository.countPosData();
        Page page = findOrderByPage(posOrderCriteria, limit);
        List<PosOrder> list = page.getContent();
        String[] titleName = {"订单编号", "状态", "订单类型", "交易完成时间", "交易所在门店", "消费者", "POS编号", "支付方式", "订单金额", "使用红包"
                , "实付金额", "佣金（手续费）", "商户总入账", "商户红包入账", "商户通道入账"};
        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格
        HSSFSheet sheet = workbook.createSheet();
        //设置表格默认列宽度为15个字符
        sheet.setDefaultColumnWidth(15);
        sheet.setDefaultRowHeight((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        //生成表格标题行
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < titleName.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(titleName[i]);
            cell.setCellValue(text);
        }
        for (int i = 1; i < list.size() + 1; i++) {
            PosOrder posOrder = list.get(i - 1);
            row = sheet.createRow(i);
            HSSFCell orderNumber = row.createCell(0);
            orderNumber.setCellStyle(style);
            HSSFRichTextString orderNumberText = new HSSFRichTextString(posOrder.getOrderSid());
            orderNumber.setCellValue(orderNumberText);
            HSSFCell stateCell = row.createCell(1);
            stateCell.setCellStyle(style);
            HSSFRichTextString stateText = new HSSFRichTextString(posOrder.getState() == 0 ? "未付款" : "已支付");
            stateCell.setCellValue(stateText);
            String rebateWay = null;
            if (posOrder.getRebateWay() == null) {
                rebateWay = "未知订单";
            } else {
                if (posOrder.getRebateWay() == 1) {
                    rebateWay = "非会员普通订单";
                } else if (posOrder.getRebateWay() == 2) {
                    rebateWay = "会员订单";
                } else if (posOrder.getRebateWay() == 3) {
                    rebateWay = "导流订单";
                } else {
                    rebateWay = "会员普通订单";
                }
            }
            HSSFCell rebateWayCell = row.createCell(2);
            rebateWayCell.setCellStyle(style);
            HSSFRichTextString rebateWayText = new HSSFRichTextString(rebateWay);
            rebateWayCell.setCellValue(rebateWayText);
            String completeDate = null;
            if (posOrder.getCompleteDate() == null) {
                completeDate = "未知时间";
            } else {
                completeDate = DataUtils.datessTOString(posOrder.getCompleteDate());
            }
            HSSFCell completeDateCell = row.createCell(3);
            completeDateCell.setCellStyle(style);
            HSSFRichTextString completeDateText = new HSSFRichTextString(completeDate);
            completeDateCell.setCellValue(completeDateText);
            String merchantName = null;
            if (posOrder.getMerchant() == null) {
                merchantName = "未知店铺";
            } else {
                merchantName = posOrder.getMerchant().getName();
            }
            HSSFCell merchantNameDateCell = row.createCell(4);
            merchantNameDateCell.setCellStyle(style);
            HSSFRichTextString merhantNameText = new HSSFRichTextString(merchantName);
            merchantNameDateCell.setCellValue(merhantNameText);
            String user = null;
            if (posOrder.getLeJiaUser() == null) {
                user = "非会员";
            } else {
                user = "乐加会员(" + posOrder.getLeJiaUser().getId() + ")";
            }
            HSSFCell userCell = row.createCell(5);
            userCell.setCellStyle(style);
            HSSFRichTextString uesrText = new HSSFRichTextString(user);
            userCell.setCellValue(uesrText);
            String posId = null;
            if (posOrder.getMerchantPos() == null) {
                posId = "未知";
            } else {
                posId = posOrder.getMerchantPos().getPosId();
            }
            HSSFCell posIdCell = row.createCell(6);
            posIdCell.setCellStyle(style);
            HSSFRichTextString posIdText = new HSSFRichTextString(posId);
            posIdCell.setCellValue(posIdText);
            String tradeFlag = null;
            if (posOrder.getState() == 1) {
                if (posOrder.getTradeFlag() == null) {
                    tradeFlag = "--";
                } else if (posOrder.getTradeFlag() == 0) {
                    tradeFlag = "支付宝";
                } else if (posOrder.getTradeFlag() == 3) {
                    tradeFlag = "pos刷卡";
                } else if (posOrder.getTradeFlag() == 4) {
                    tradeFlag = "微信";
                } else if (posOrder.getTradeFlag() == 5) {
                    tradeFlag = "纯积分";
                }
            } else {
                tradeFlag = "--";
            }
            HSSFCell tradeFlagCell = row.createCell(7);
            tradeFlagCell.setCellStyle(style);
            HSSFRichTextString tradeFlagText = new HSSFRichTextString(tradeFlag);
            tradeFlagCell.setCellValue(tradeFlagText);
            HSSFCell orderTotalMoneyCell = row.createCell(8);
            orderTotalMoneyCell.setCellStyle(style);
            HSSFRichTextString orderTotalMoneyText = new HSSFRichTextString(String.valueOf(posOrder.getTotalPrice() / 100));
            orderTotalMoneyCell.setCellValue(orderTotalMoneyText);
            HSSFCell trueScoreCell = row.createCell(9);
            trueScoreCell.setCellStyle(style);
            HSSFRichTextString trueScoreText = new HSSFRichTextString(String.valueOf(posOrder.getTrueScore() / 100));
            trueScoreCell.setCellValue(trueScoreText);
            HSSFCell truePayCell = row.createCell(10);
            truePayCell.setCellStyle(style);
            HSSFRichTextString truePayText = new HSSFRichTextString(String.valueOf(posOrder.getTruePay() / 100));
            truePayCell.setCellValue(truePayText);
            String commission = null;
            if (posOrder.getState() == 1) {
                if (posOrder.getRebateWay() == 1 || posOrder.getRebateWay() == 4) {
                    commission = String.valueOf(posOrder.getLjCommission() / 100);
                } else {
                    commission = String.valueOf((100 - posOrder.getMerchant().getLjCommission().intValue()) / 10) + "折";
                }
            } else {
                commission = "未支付";
            }
            HSSFCell commissionCell = row.createCell(11);
            commissionCell.setCellStyle(style);
            HSSFRichTextString commissionText = new HSSFRichTextString(commission);
            commissionCell.setCellValue(commissionText);
            Long transferMoney = 0l;
            if (posOrder.getTransferMoney() != null) {
                transferMoney = posOrder.getTransferMoney();
            }
            HSSFCell transferMoneyCell = row.createCell(12);
            transferMoneyCell.setCellStyle(style);
            HSSFRichTextString transferMoneyText = new HSSFRichTextString(String.valueOf(transferMoney / 100));
            transferMoneyCell.setCellValue(transferMoneyText);
            Long transferByBank = 0l;
            if (posOrder.getTransferByBank() != null) {
                transferByBank = posOrder.getTransferByBank();
            }
            HSSFCell transferByBankCell = row.createCell(14);
            transferByBankCell.setCellStyle(style);
            HSSFRichTextString transferByBankText = new HSSFRichTextString(String.valueOf(transferByBank / 100));
            transferByBankCell.setCellValue(transferByBankText);
            HSSFCell transferCell = row.createCell(13);
            transferCell.setCellStyle(style);
            HSSFRichTextString transferText = new HSSFRichTextString(String.valueOf((transferMoney - transferByBank) / 100));
            transferCell.setCellValue(transferText);
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 统计某个门店POS的累计流水和累计收取红包  2017/02/10
     *
     * @param merchantId 门店ID
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Map<String, Long> countPriceByMerchant(Long merchantId) {
        List<Object[]> list = posOrderRepository.countPriceByMerchant(merchantId);
        Map<String, Long> map = new HashMap<>();
        Long totalPrice = 0L;
        Long trueScore = 0L;

        if (list != null && list.size() > 0) {
            Object[] o = list.get(0);
            totalPrice = Long.valueOf(String.valueOf(o[0] != null ? o[0] : 0));
            trueScore = Long.valueOf(String.valueOf(o[1] != null ? o[1] : 0));
        }
        map.put("totalPrice_pos", totalPrice);
        map.put("trueScore_pos", trueScore);
        return map;
    }

    /**
     * 检索条数据  2017/06/06
     *
     * @param orderCriteria
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Object[]> countOrderMoney(PosOrderCriteria orderCriteria) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select count(*),IFNULL(sum(total_price),0),IFNULL(sum(true_score),0),IFNULL(sum(true_pay),0),IFNULL(sum(transfer_money),0),IFNULL(sum(wx_commission),0) from pos_order po,merchant m ");
        sql.append(" where po.merchant_id = m.id ");
        String start = orderCriteria.getStartDate();
        String end = orderCriteria.getEndDate();
        //  日期
        if (start != null && end != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startDate = sdf.format(new Date(start));
            String endDate = sdf.format(new Date(end));
            sql.append(" and ");
            sql.append("  po.complete_date BETWEEN '" + startDate + "' and  '" + endDate + "'");
        }
        //  用户 Sid
        if (!"".equals(orderCriteria.getUserSid()) && orderCriteria.getUserSid() != null) {
            LeJiaUser leJiaUser = leJiaUserRepository.findUserBySid(orderCriteria.getUserSid());
            if(leJiaUser!=null) {
                sql.append(" and ");
                sql.append("  po.le_jia_user_id = " + leJiaUser.getId());
            }
        }
        // 用户电话
        if(orderCriteria.getUserPhone()!=null) {
            LeJiaUser leJiaUser = leJiaUserRepository.findUserByPhoneNumber(orderCriteria.getUserPhone());
            if(leJiaUser!=null) {
                sql.append(" and ");
                sql.append(" po.le_jia_user_id = "+leJiaUser.getId());
            }
        }
        // 订单类型
        if (orderCriteria.getRebateWay() != null) {
            sql.append(" and ");
            sql.append("  po.rebate_way = " + orderCriteria.getRebateWay());
        }
        //  支付方式
        if (orderCriteria.getTradeFlag() != null) {
            sql.append(" and ");
            sql.append("  po.trade_flag = " + orderCriteria.getTradeFlag());
        }
        // 状态
        if (orderCriteria.getState() != null) {
            sql.append(" and ");
            sql.append(" po.state = "+orderCriteria.getState());
        }
        // 所在城市
        if (orderCriteria.getMerchantLocation() != null) {
            sql.append(" and ");
            sql.append("  m.city_id = " + orderCriteria.getMerchantLocation());
        }
        // 商户名称  or 商户 Sid
        if (orderCriteria.getMerchant() != null && orderCriteria.getMerchant() != "") {
            String merchant = orderCriteria.getMerchant();
            if (orderCriteria.getMerchant().matches("^\\d{1,6}$")) {
                sql.append(" and ");
                sql.append("  m.merchant_sid like '%" +merchant + "%'");
            } else {
                sql.append(" and ");
                sql.append("  m.name like '%" + merchant + "%'");
            }
        }
        List<Object[]> resultList = entityManager.createNativeQuery(sql.toString()).getResultList();
        return resultList;
    }
}
