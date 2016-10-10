package com.jifenke.lepluslive.datacenter.service;

import com.jifenke.lepluslive.datacenter.domain.criteria.MerchantCriteriaEx;
import com.jifenke.lepluslive.datacenter.util.ExportUtils;
import com.jifenke.lepluslive.merchant.domain.criteria.MerchantCriteria;
import com.jifenke.lepluslive.merchant.domain.entities.City;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantType;
import com.jifenke.lepluslive.merchant.repository.CityRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantRepository;
import com.jifenke.lepluslive.merchant.repository.MerchantTypeRepository;
import com.jifenke.lepluslive.order.repository.OffLineOrderRepository;
import com.jifenke.lepluslive.sales.domain.entities.SalesStaff;
import com.jifenke.lepluslive.sales.repository.SalesStaffRepository;
import com.jifenke.lepluslive.user.repository.LeJiaUserRepository;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;

/**
 * Created by xf on 2016/9/20.
 */
@Service
@Transactional(readOnly = true)
public class MerchantDataService {

  @Inject
  private CityRepository cityRepository;                      //  城市
  @Inject
  private MerchantTypeRepository merchantTypeRepository;      //  商户类型
  @Inject
  private SalesStaffRepository salesStaffRepository;          //  销售类型
  @Inject
  private LeJiaUserRepository leJiaUserRepository;
  @Inject
  private OffLineOrderRepository offeLineOrderRepository;
  @Inject
  private MerchantRepository merchantRepository;
  @Inject
  private EntityManager entityManager;

  public List<City> findAllCitys() {
    return cityRepository.findAll();
  }

  public List<MerchantType> findAllMerchantTypes() {
    return merchantTypeRepository.findAll();
  }

  public List<SalesStaff> findAllStaffList() {
    return salesStaffRepository.findAll();
  }

  /**
   * 根据条件查询商户及订单数量
   */
  public Map<String, List> findMerchantAndCountByCriteria(MerchantCriteriaEx merchantCriteria) {
    StringBuffer
        sql =
        new StringBuffer(
            "select merchant.id,count(1),sum(total_price) money from merchant inner join off_line_order on merchant.id = merchant_id ");
    sql.append(" where off_line_order.complete_date BETWEEN '" + merchantCriteria.getStartDate()
               + "' and '" + merchantCriteria.getEndDate() + "'");
    if (merchantCriteria.getMerchantName() != null && !"".equals(           // 商户名称
                                                                            merchantCriteria
                                                                                .getMerchantName()
                                                                                .trim())) {
      sql.append(" and merchant.`name` like '%" + merchantCriteria.getMerchantName() + "%'");
    }
    if (merchantCriteria.getMerchantCreateStart() != null
        && merchantCriteria.getEndDate() != null
        && !"".equals(merchantCriteria.getEndDate())
        && !"".equals(merchantCriteria.getEndDate())) {
      sql.append(" and merchant.create_date BETWEEN '" + merchantCriteria.getMerchantCreateStart()
                 + "' and '" + merchantCriteria.getMerchantCreateEnd() + "' ");
    }
    if (merchantCriteria.getMerchant() != null && !"".equals(merchantCriteria.getMerchant())) {
      sql.append(" and merchant.id = " + merchantCriteria.getMerchant());
    }
    if (merchantCriteria.getSalesStaff() != null) {                         // 销售名称
      sql.append(" and merchant.sales_staff_id = " + merchantCriteria.getSalesStaff());
    }
    if (merchantCriteria.getCity() != null) {
      sql.append(" and merchant.city_id = " + merchantCriteria.getCity()); // 城市
    }
    if (merchantCriteria.getMerchantType() != null) {
      sql.append(" and merchant.merchant_type_id = " + merchantCriteria.getMerchantType()); // 商户类型
    }
    if (merchantCriteria.getValidAmount() != null) {
      sql.append(" and total_price>" + merchantCriteria.getValidAmount()); //  大于该金额算有效订单
    }
    sql.append(" group by merchant_id");
    if (merchantCriteria.getNeedNum() != null) {
      sql.append(" having count(1)  >=" + merchantCriteria.getNeedNum());       // 订单数量
    }
    sql.append(" order by count(1)  desc");                                   // 根据订单量排序
    if (merchantCriteria.getOffset() != null) {
      sql.append(" limit " + ((merchantCriteria.getOffset() - 1) * 10) + ",10 ");        // 分页
    }
    Query nativeQuery = entityManager.createNativeQuery(sql.toString());
    List<Object[]> list = nativeQuery.getResultList();
    Map<String, List> map = new HashMap<>();
    List<Merchant> merchants = new ArrayList<>();
    List<Long> orderNum = new ArrayList<>();
    List<Double> orderTotal = new ArrayList<>();
    if (list != null && list.size() > 0) {
      for (int i = 0; i < list.size(); i++) {
        Object[] objects = list.get(i);
        if (objects[0] != null) {
          Long id = new Long(objects[0].toString());
          Merchant merchant = merchantRepository.findOne(id);
          merchants.add(merchant);
        }
        if (objects[1] != null) {
          orderNum.add(new Long(objects[1].toString()));
        } else {
          orderNum.add(0L);
        }
        if (objects[2] != null) {
          double dtotal = new Long(objects[2].toString());
          orderTotal.add((dtotal / 100));                               // 1:100
        } else {
          orderTotal.add(0.0);
        }
      }
    }
    String
        countSql =
        new String("select count(1) from ( " + sql.toString().replace(
            " limit " + (merchantCriteria.getOffset() == null ? 1
                                                              : (merchantCriteria.getOffset() - 1)
                                                                * 10) + ",10 ", "") + " ) records");
    Query countQuery = entityManager.createNativeQuery(countSql);
    List<BigInteger> details = countQuery.getResultList();
    map.put("merchants", merchants);
    map.put("orderNum", orderNum);
    map.put("orderTotal", orderTotal);
    map.put("totalElements", details);
    return map;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<Merchant> findMerchantNotInOffLineOrder(List<Merchant> merchants) {
    StringBuffer sql =
        new StringBuffer(
            "select merchant.id from merchant");
    List<Merchant> otherMerchants = new ArrayList<>();
    List<Long> otherMerchantsIds = new ArrayList<>();
    if (merchants != null && merchants.size() > 0) {
      //  取出商户 id 存入 list
      for (Merchant merchant : merchants) {
        otherMerchantsIds.add(merchant.getId());
      }
      //  将id如 [0,1,2] 转为 (0,1,2)
      String ids = otherMerchantsIds.toString().replace("[", "(").replace("]", ")");
      sql.append(" where merchant.id not in " + ids);
      Query countQuery = entityManager.createNativeQuery(sql.toString());
      List<BigInteger> merchantIds = countQuery.getResultList();
      //  将查询出的 id 转为 merchant
      for (BigInteger merchantId : merchantIds) {
        Merchant merchant = merchantRepository.findOne(merchantId.longValue());
        otherMerchants.add(merchant);
      }
    }
    return otherMerchants;
  }

  /**
   * 查询锁定用户
   */
  public List<SalesStaff> findStaffByMerchant(List<Merchant> merchants) {
    List<SalesStaff> staffs = new ArrayList<>();
    for (Merchant merchant : merchants) {
      if (merchant.getSalesStaff() != null) {
        staffs.add(merchant.getSalesStaff());
      } else {
        SalesStaff staff = new SalesStaff();
        staff.setName("待定");
        staffs.add(staff);
      }

    }
    return staffs;
  }

  /**
   * 查询时间段内锁定用户
   */
  public List<Integer> findBindLeJiaUsersByDate(List<Merchant> merchants,
                                                MerchantCriteria merchantCriteria) {
    String start = merchantCriteria.getStartDate();
    String end = merchantCriteria.getEndDate();
    List<Integer> bindsTime = new ArrayList<>();
    if (merchants != null) {
      for (Merchant merchant : merchants) {
        Integer
            count =
            leJiaUserRepository.countByBindMerchantAndDate(merchant.getId(), start, end);
        bindsTime.add(count);
      }
    }
    return bindsTime;
  }

  /**
   * 查询商户的有效订单数量
   */
  public List<Long> findOrderNumByMerchant(List<Merchant> merchants,
                                           MerchantCriteriaEx merchantCriteria) {
    String start = merchantCriteria.getStartDate();
    String end = merchantCriteria.getEndDate();
    Long validAmount = merchantCriteria.getValidAmount();
    List<Long> orderNum = new ArrayList<>();
    if (merchants != null) {
      for (Merchant merchant : merchants) {
        Long
            num =
            offeLineOrderRepository
                .countOrderNumByMerchant(merchant.getId(), start, end, validAmount);
        if (num != null) {
          orderNum.add(num);
        } else {
          orderNum.add(0L);
        }
      }
    }
    return orderNum;
  }

  /**
   * 查询有效订单流水
   */
  public List<Double> findOrderTotalByMerchant(List<Merchant> merchants,
                                               MerchantCriteriaEx merchantCriteria) {
    String start = merchantCriteria.getStartDate();
    String end = merchantCriteria.getEndDate();
    Long validAmount = merchantCriteria.getValidAmount();
    List<Double> orderTotal = new ArrayList<>();
    if (merchants != null) {
      for (Merchant merchant : merchants) {
        Long
            num =
            offeLineOrderRepository
                .countOrderTotalByMerchant(merchant.getId(), start, end, validAmount);
        if (num != null) {
          double dtotal = num;
          orderTotal.add((dtotal / 100));               // 1:100
        } else {
          orderTotal.add(0.0);
        }
      }
    }
    return orderTotal;
  }

  /**
   * 查询销售名称
   */
  public SalesStaff findStaff(MerchantCriteria merchantCriteria) {
    SalesStaff staff = null;
    if (merchantCriteria.getSalesStaff() != null) {
      staff = salesStaffRepository.findOne(new Long(merchantCriteria.getSalesStaff()));
    }
    return staff;
  }

  /**
   * 查询导流订单数量/流水/佣金
   */
  public Map<String, List> findLeadOrder(List<Merchant> merchants,
                                         MerchantCriteriaEx merchantCriteria) {
    String start = merchantCriteria.getStartDate();
    String end = merchantCriteria.getEndDate();
    Long validAmount = merchantCriteria.getValidAmount();
    List<Long> leadOrderNum = new ArrayList<>();
    List<Double> leadTotal = new ArrayList<>();
    List<Double> leadCommission = new ArrayList<>();
    Map<String, List> map = new HashMap<>();
    for (Merchant merchant : merchants) {
      List<Object[]> list =
          offeLineOrderRepository
              .countLeadOrderByMidAndWay(merchant.getId(), 1L, start, end, validAmount);
      if (list != null && list.size() > 0) {
        Object[] objects = list.get(0);
        if (objects[0] != null) {
          leadOrderNum.add(new Long(objects[0].toString()));
        } else {
          leadOrderNum.add(0L);
        }
        if (objects[1] != null) {
          double dtotal = new Long(objects[1].toString());
          leadTotal.add((dtotal / 100));                               // 1:100
        } else {
          leadTotal.add(0.0);
        }
        if (objects[2] != null) {
          double dnum = new Long(objects[2].toString());
          leadCommission.add((dnum / 100));                            // 1:100
        } else {
          leadCommission.add(0.0);
        }
      }
    }
    map.put("leadOrderNum", leadOrderNum);
    map.put("leadTotal", leadTotal);
    map.put("leadCommission", leadCommission);
    return map;
  }

  /**
   * 导出 Excel 报表
   *
   * @ merchants
   * @ merchantCriteria
   * @ titles
   * @ outStream
   */
  public void exportExcel(List<SalesStaff> staffs, List<Merchant> merchants,
                          List<Merchant> otherMerchants, List<Integer> binds,
                          List<Integer> otherBinds, List<Integer> timeBinds,
                          List<Integer> otherTimeBinds, List<Long> orderNum,
                          List<Double> orderTotal, List<Long> leadOrderNum, List<Double> leadTotal,
                          List<Double> leadCommission,
                          String[] titles, ServletOutputStream outputStream) throws IOException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //  创建表格对象
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet();
    ExportUtils exportUtils = new ExportUtils(workbook, sheet);
    //  获取头部和身体样式
    HSSFCellStyle headStyle = exportUtils.getHeadStyle();
    HSSFCellStyle bodyStyle = exportUtils.getBodyStyle();
    //  构建表头
    HSSFCell cell = null;
    HSSFRow headRow = sheet.createRow(0);
    for (int i = 0; i < titles.length; i++) {
      cell = headRow.createCell(i);
      cell.setCellStyle(headStyle);
      cell.setCellValue(titles[i]);
    }
    //  构建表体
    for (int j = 0; j < merchants.size(); j++) {
      HSSFRow bodyRow = sheet.createRow(j + 1);
      HSSFCell cell0 = bodyRow.createCell(0);
      cell0.setCellValue(staffs.get(j).getName());
      cell0.setCellStyle(bodyStyle);
      HSSFCell cell1 = bodyRow.createCell(1);
      cell1.setCellValue(merchants.get(j).getName());
      cell1.setCellStyle(bodyStyle);
      HSSFCell cell2 = bodyRow.createCell(2);
      cell2.setCellValue(merchants.get(j).getMerchantType().getName());
      cell2.setCellStyle(bodyStyle);
      HSSFCell cell3 = bodyRow.createCell(3);
      cell3.setCellValue(binds.get(j));
      cell3.setCellStyle(bodyStyle);
      HSSFCell cell4 = bodyRow.createCell(4);
      cell4.setCellValue(timeBinds.get(j));
      cell4.setCellStyle(bodyStyle);
      HSSFCell cell5 = bodyRow.createCell(5);
      cell5.setCellValue(orderTotal.get(j));
      cell5.setCellStyle(bodyStyle);
      HSSFCell cell6 = bodyRow.createCell(6);
      cell6.setCellValue(orderNum.get(j));
      cell6.setCellStyle(bodyStyle);
      HSSFCell cell7 = bodyRow.createCell(7);
      cell7.setCellValue(leadOrderNum.get(j));
      cell7.setCellStyle(bodyStyle);
      HSSFCell cell8 = bodyRow.createCell(8);
      cell8.setCellValue(leadTotal.get(j));
      cell8.setCellStyle(bodyStyle);
      HSSFCell cell9 = bodyRow.createCell(9);
      cell9.setCellValue(leadCommission.get(j));
      cell9.setCellStyle(bodyStyle);
      HSSFCell cell10 = bodyRow.createCell(10);
      cell10.setCellValue(merchants.get(j).getMerchantSid());
      cell10.setCellStyle(bodyStyle);
      HSSFCell cell11 = bodyRow.createCell(11);
      cell11.setCellValue(sdf.format(merchants.get(j).getCreateDate()));
      cell11.setCellStyle(bodyStyle);
    }
    // 如果不为空
    if (otherMerchants != null && otherMerchants.size() > 0) {
      for (int a = 0; a < otherMerchants.size(); a++) {
        HSSFRow bodyRow = sheet.createRow(sheet.getLastRowNum() + 1);
        HSSFCell cell0 = bodyRow.createCell(0);
        if (otherMerchants.get(a).getSalesStaff() != null
            && otherMerchants.get(a).getSalesStaff().getName() != null) {
          cell0.setCellValue(otherMerchants.get(a).getSalesStaff().getName());
        } else {
          cell0.setCellValue("待定");
        }
        cell0.setCellStyle(bodyStyle);
        HSSFCell cell1 = bodyRow.createCell(1);
        cell1.setCellValue(otherMerchants.get(a).getName());
        cell1.setCellStyle(bodyStyle);
        HSSFCell cell2 = bodyRow.createCell(2);
        cell2.setCellValue(otherMerchants.get(a).getMerchantType().getName());
        cell2.setCellStyle(bodyStyle);
        HSSFCell cell3 = bodyRow.createCell(3);
        cell3.setCellValue(otherBinds.get(a));
        cell3.setCellStyle(bodyStyle);
        HSSFCell cell4 = bodyRow.createCell(4);
        cell4.setCellValue(otherTimeBinds.get(a));
        cell4.setCellStyle(bodyStyle);
        HSSFCell cell5 = bodyRow.createCell(5);
        cell5.setCellValue(0);
        cell5.setCellStyle(bodyStyle);
        HSSFCell cell6 = bodyRow.createCell(6);
        cell6.setCellValue(0);
        cell6.setCellStyle(bodyStyle);
        HSSFCell cell7 = bodyRow.createCell(7);
        cell7.setCellValue(0);
        cell7.setCellStyle(bodyStyle);
        HSSFCell cell8 = bodyRow.createCell(8);
        cell8.setCellValue(0);
        cell8.setCellStyle(bodyStyle);
        HSSFCell cell9 = bodyRow.createCell(9);
        cell9.setCellValue(0);
        cell9.setCellStyle(bodyStyle);
        HSSFCell cell10 = bodyRow.createCell(10);
        cell10.setCellValue(0);
        cell10.setCellStyle(bodyStyle);
        HSSFCell cell11 = bodyRow.createCell(11);
        cell11.setCellValue(0);
        cell11.setCellStyle(bodyStyle);
      }
    }

    // 设置列宽
    for (int z = 0; z < 12; z++) {
      sheet.autoSizeColumn(z);
    }
    // 输出流
    workbook.write(outputStream);
    outputStream.flush();
    outputStream.close();
  }

  //到表格的查询方法
  public Map<String, List> findMerchantAndCountByCriteriaForExcel(
      MerchantCriteriaEx merchantCriteria) {
    StringBuffer
        sql =
        new StringBuffer(
            "select merchant.id,count(1),sum(total_price) money from merchant inner join off_line_order on merchant.id = merchant_id ");
    sql.append(" where off_line_order.complete_date BETWEEN '" + merchantCriteria.getStartDate()
               + "' and '" + merchantCriteria.getEndDate() + "'");
    if (merchantCriteria.getMerchantName() != null && !"".equals(           // 商户名称
                                                                            merchantCriteria
                                                                                .getMerchantName()
                                                                                .trim())) {
      sql.append(" and merchant.`name` like '%" + merchantCriteria.getMerchantName() + "%'");
    }
    if (merchantCriteria.getMerchantCreateStart() != null
        && merchantCriteria.getEndDate() != null
        && !"".equals(merchantCriteria.getEndDate())
        && !"".equals(merchantCriteria.getEndDate())) {
      sql.append(" and merchant.create_date BETWEEN '" + merchantCriteria.getMerchantCreateStart()
                 + "' and '" + merchantCriteria.getMerchantCreateEnd() + "' ");
    }
    if (merchantCriteria.getMerchant() != null && !"".equals(merchantCriteria.getMerchant())) {
      sql.append(" and merchant.id = " + merchantCriteria.getMerchant());
    }
    if (merchantCriteria.getSalesStaff() != null) {                         // 销售名称
      sql.append(" and merchant.sales_staff_id = " + merchantCriteria.getSalesStaff());
    }
    if (merchantCriteria.getCity() != null) {
      sql.append(" and merchant.city_id = " + merchantCriteria.getCity()); // 城市
    }
    if (merchantCriteria.getMerchantType() != null) {
      sql.append(" and merchant.merchant_type_id = " + merchantCriteria.getMerchantType()); // 商户类型
    }
    if (merchantCriteria.getValidAmount() != null) {
      sql.append(" and total_price>" + merchantCriteria.getValidAmount()); //  大于该金额算有效订单
    }
    sql.append(" group by merchant_id");
    if (merchantCriteria.getNeedNum() != null) {
      sql.append(" having count(1)  >=" + merchantCriteria.getNeedNum());       // 订单数量
    }
    sql.append(" order by count(1)  desc");                                   // 根据订单量排序
//    if(merchantCriteria.getOffset()!=null) {
//      sql.append(" limit "+((merchantCriteria.getOffset()-1)*10)+",10 ");        // 分页
//    }
    Query nativeQuery = entityManager.createNativeQuery(sql.toString());
    List<Object[]> list = nativeQuery.getResultList();
    Map<String, List> map = new HashMap<>();
    List<Merchant> merchants = new ArrayList<>();
    List<Long> orderNum = new ArrayList<>();
    List<Double> orderTotal = new ArrayList<>();
    if (list != null && list.size() > 0) {
      for (int i = 0; i < list.size(); i++) {
        Object[] objects = list.get(i);
        if (objects[0] != null) {
          Long id = new Long(objects[0].toString());
          Merchant merchant = merchantRepository.findOne(id);
          merchants.add(merchant);
        }
        if (objects[1] != null) {
          orderNum.add(new Long(objects[1].toString()));
        } else {
          orderNum.add(0L);
        }
        if (objects[2] != null) {
          double dtotal = new Long(objects[2].toString());
          orderTotal.add((dtotal / 100));                               // 1:100
        } else {
          orderTotal.add(0.0);
        }
      }
    }
    String
        countSql =
        new String("select count(1) from ( " + sql.toString().replace(
            " limit " + (merchantCriteria.getOffset() == null ? 1
                                                              : (merchantCriteria.getOffset() - 1)
                                                                * 10) + ",10 ", "") + " ) records");
    Query countQuery = entityManager.createNativeQuery(countSql);
    List<BigInteger> details = countQuery.getResultList();
    map.put("merchants", merchants);
    map.put("orderNum", orderNum);
    map.put("orderTotal", orderTotal);
    map.put("totalElements", details);
    return map;
  }


}
