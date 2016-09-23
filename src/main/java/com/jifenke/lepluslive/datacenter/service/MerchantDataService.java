package com.jifenke.lepluslive.datacenter.service;

import com.jifenke.lepluslive.datacenter.domain.criteria.MerchantCriteriaEx;
import com.jifenke.lepluslive.datacenter.util.ExportUtils;
import com.jifenke.lepluslive.merchant.domain.criteria.MerchantCriteria;
import com.jifenke.lepluslive.merchant.domain.entities.City;
import com.jifenke.lepluslive.merchant.domain.entities.Merchant;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantType;
import com.jifenke.lepluslive.merchant.repository.CityRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
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
  public void exportExcel(List<SalesStaff> staffs, List<Merchant> merchants, List<Integer> binds,
                          List<Integer> timeBinds, List<Long> orderNum,
                          List<Double> orderTotal, List<Long> leadOrderNum, List<Double> leadTotal,
                          List<Double> leadCommission,
                          String[] titles, ServletOutputStream outputStream) throws IOException {
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
    }
    // 设置列宽
    for(int z=0;z<10;z++) {
      sheet.autoSizeColumn(z);
    }
    // 输出流
    workbook.write(outputStream);
    outputStream.flush();
    outputStream.close();
  }

}
