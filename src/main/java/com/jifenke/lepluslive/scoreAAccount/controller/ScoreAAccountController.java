package com.jifenke.lepluslive.scoreAAccount.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.order.domain.entities.OffLineOrder;
import com.jifenke.lepluslive.order.domain.entities.OffLineOrderShare;
import com.jifenke.lepluslive.order.service.OffLineOrderService;
import com.jifenke.lepluslive.order.service.ShareService;
import com.jifenke.lepluslive.score.domain.criteria.ScoreDetailCriteria;
import com.jifenke.lepluslive.score.domain.entities.ScoreA;
import com.jifenke.lepluslive.score.domain.entities.ScoreADetail;
import com.jifenke.lepluslive.score.domain.entities.ScoreDailyTotal;
import com.jifenke.lepluslive.score.service.ScoreADetailService;
import com.jifenke.lepluslive.score.service.ScoreDailyTotalService;
import com.jifenke.lepluslive.scoreAAccount.controller.view.ScoreAAccountDetailViewExcel;
import com.jifenke.lepluslive.scoreAAccount.controller.view.ScoreAAccountViewExcel;
import com.jifenke.lepluslive.scoreAAccount.domain.criteria.ScoreAAccountCriteria;
import com.jifenke.lepluslive.scoreAAccount.domain.criteria.ScoreAAccountDistributionCriteria;
import com.jifenke.lepluslive.scoreAAccount.domain.entities.ScoreAAccount;
import com.jifenke.lepluslive.scoreAAccount.service.ScoreAAccountService;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lss on 2016/9/12.
 */
@RestController
@RequestMapping("/manage")
public class ScoreAAccountController {
  @Inject
  private ScoreAAccountService scoreAAccountService;

  @Inject
  private ScoreADetailService scoreADetailService;

  @Inject
  private OffLineOrderService offLineOrderService;

  @Inject
  private ShareService shareService;

  @Inject
  private ScoreAAccountViewExcel scoreAAccountViewExcel;

  @Inject
  private ScoreAAccountDetailViewExcel scoreAAccountDetailViewExcel;

  @Inject
  private ScoreDailyTotalService scoreDailyTotalService;

  @RequestMapping(value = "/scoreAAccountPage", method = RequestMethod.GET)
  public ModelAndView scoreAAccountPage(Model model) {

    Long presentHoldScorea=scoreAAccountService.findPresentHoldScorea();
    Long issueScorea=scoreAAccountService.findIssueScorea();
    Long useScorea=scoreAAccountService.findUseScorea();
    Long ljCommission=scoreAAccountService.findLjCommission();
    Long shareMoney=scoreAAccountService.findShareMoney();
    model.addAttribute("presentHoldScorea",presentHoldScorea);
    model.addAttribute("issueScorea",issueScorea);
    model.addAttribute("useScorea",useScorea);
    model.addAttribute("ljCommission",ljCommission);
    model.addAttribute("shareMoney",shareMoney);

    return MvUtil.go("/scoreAAccount/scoreAAccount");
  }





  @RequestMapping(value = "/scoreAAccount", method = RequestMethod.POST)
     public
     @ResponseBody
  LejiaResult getscoreAAccount(@RequestBody ScoreAAccountCriteria scoreAAccountCriteria) {

    if (scoreAAccountCriteria.getOffset() == null) {
      scoreAAccountCriteria.setOffset(1);
    }
    Page page = scoreAAccountService.findScoreAAccountByPage(scoreAAccountCriteria, 10);
    Map<String,Object> dataMap=new HashMap<String,Object>();
    List<ScoreAAccount> scoreAAccountList=page.getContent();
    long totalElements=page.getTotalElements();
    int totalPages=page.getTotalPages();
    dataMap.put("content",scoreAAccountList);
    dataMap.put("totalElements",totalElements);
    dataMap.put("totalPages",totalPages);

    Page page2 = scoreAAccountService.findScoreAAccountByPage(scoreAAccountCriteria, 10000);
    List<ScoreAAccount> scoreAAccountList2=page.getContent();
    long useScoreA=0;
    long issuedScoreA=0;
    long commissionIncome=0;
    long jfkShare=0;
    for(ScoreAAccount scoreAAccount:scoreAAccountList2){
      if (scoreAAccount.getUseScoreA()!=null){
        useScoreA=scoreAAccount.getUseScoreA()+useScoreA;
      }
      if(scoreAAccount.getIssuedScoreA()!=null){
        issuedScoreA=scoreAAccount.getIssuedScoreA()+issuedScoreA;
      }
      if(scoreAAccount.getCommissionIncome()!=null){
        commissionIncome=scoreAAccount.getCommissionIncome()+commissionIncome;
      }
      if(scoreAAccount.getJfkShare()!=null){
        jfkShare=scoreAAccount.getJfkShare()+jfkShare;
      }
    }
    dataMap.put("useScoreA",useScoreA);
    dataMap.put("issuedScoreA",issuedScoreA);
    dataMap.put("commissionIncome",commissionIncome);
    dataMap.put("jfkShare",jfkShare);
    return LejiaResult.ok(dataMap);
  }


  @RequestMapping(value = "/scoreAAccount/export", method = RequestMethod.POST)
  public ModelAndView exporeExcel(ScoreAAccountCriteria scoreAAccountCriteria) {
      scoreAAccountCriteria.setOffset(1);
    Page page = scoreAAccountService.findScoreAAccountByPage(scoreAAccountCriteria, 10000);
    Map map = new HashMap();
    map.put("scoreAAccountList", page.getContent());
    return new ModelAndView(scoreAAccountViewExcel, map);
  }

  @RequestMapping(value = "/serchScoreAAccountDetailPage", method = RequestMethod.GET)
  public ModelAndView serchScoreAAccountDetailPage( Model model,String time) {
    model.addAttribute("time",time);
    return MvUtil.go("/scoreAAccount/scoreAAccountDetail");
  }




  @RequestMapping(value = "/scoreAAccountDetail", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult getscoreAAccountDetail(@RequestBody ScoreDetailCriteria scoreDetailCriteria) {
    if (scoreDetailCriteria.getOffset() == null) {
      scoreDetailCriteria.setOffset(1);
    }
    Map<String,Object> dataMap=new HashMap<String,Object>();
    dataMap=scoreAAccountDetailPage(scoreDetailCriteria,10);
    Integer useScoreA=0;
    Integer issuedScoreA=0;
    Integer commissionIncome=0;
    Integer jfkShare=0;
      List<Map> scoreAAccountDetailList = scoreAAccountDetailList(scoreDetailCriteria, 10);
    for(Map scoreAAccountDetailMap:scoreAAccountDetailList){
      if (scoreAAccountDetailMap.get("useScoreA")!=null){
        useScoreA=Integer.parseInt(scoreAAccountDetailMap.get("useScoreA").toString())+useScoreA;
      }
    if(scoreAAccountDetailMap.get("issuedScoreA")!=null){
      issuedScoreA=Integer.parseInt(scoreAAccountDetailMap.get("issuedScoreA").toString())+issuedScoreA;
    }
      if(scoreAAccountDetailMap.get("commissionIncome")!=null){
        commissionIncome=Integer.parseInt(scoreAAccountDetailMap.get("commissionIncome").toString())+commissionIncome;
      }
      if(scoreAAccountDetailMap.get("jfkShare")!=null){
        jfkShare=Integer.parseInt(scoreAAccountDetailMap.get("jfkShare").toString())+jfkShare;
      }
    }
    dataMap.put("useScoreA",useScoreA);
    dataMap.put("issuedScoreA",issuedScoreA);
    dataMap.put("commissionIncome",commissionIncome);
    dataMap.put("jfkShare",jfkShare);
    return LejiaResult.ok(dataMap);
  }
  @RequestMapping(value = "/scoreAAccountDetail/export", method = RequestMethod.POST)
  public ModelAndView exporeExcel(ScoreDetailCriteria scoreDetailCriteria) {
    scoreDetailCriteria.setOffset(1);
   List<Map>   scoreAAccountDetailList=scoreAAccountDetailList(scoreDetailCriteria,10000);
   Map map = new HashMap();
   map.put("scoreAAccountDetailList", scoreAAccountDetailList);
    return new ModelAndView(scoreAAccountDetailViewExcel, map);
  }

  //红包账户详情页方法
  public  Map<String,Object>  scoreAAccountDetailPage(ScoreDetailCriteria scoreDetailCriteria,Integer size){
    Page page = scoreADetailService.findScoreADetailByPage(scoreDetailCriteria, size);
    List<Map>scoreAAccountDetailList=new ArrayList<Map>();
    scoreAAccountDetailList=scoreAAccountDetailList(scoreDetailCriteria,size);
    Map<String,Object> dataMap=new HashMap<String,Object>();
    String dc=scoreDetailCriteria.getDateCreated();
    Integer ogItg=scoreDetailCriteria.getOrigin();
    Integer totalElements=100;
    if(ogItg==null){
      String dateStr = dc.replace("/","-");
      totalElements =scoreADetailService.findscoreADetailcount(dateStr);
    }else{
      String dateStr = dc.replace("/","-");
      totalElements =scoreADetailService.findscoreADetailcount(dateStr,ogItg);
    }
    double db = totalElements.intValue();
    Double dbPages=Math.ceil(db/10);
    Integer totalPages = dbPages.intValue();
    dataMap.put("content",scoreAAccountDetailList);
    dataMap.put("totalElements",totalElements);
    dataMap.put("totalPages",totalPages);
    return dataMap;
  }

  //红包账户详情List方法
  public  List<Map>  scoreAAccountDetailList(ScoreDetailCriteria scoreDetailCriteria,Integer size){
    Page page = scoreADetailService.findScoreADetailByPage(scoreDetailCriteria, size);
    List<Map>scoreAAccountDetailList=new ArrayList<Map>();
    List<ScoreADetail> scoreADetailList=page.getContent();
    for (ScoreADetail scoreADetail : scoreADetailList) {
      Map<String,String> scoreAAccountDetailMap=new HashMap<String,String>();
      Date changeDate = scoreADetail.getDateCreated();
      String orderSid = null;
      Integer origin = scoreADetail.getOrigin();
      String  operate=scoreADetail.getOperate();
      ScoreA scoreA = scoreADetail.getScoreA();
      //修改时间
      if(changeDate==null){
        scoreAAccountDetailMap.put("changeDate",null);
      }else{
        scoreAAccountDetailMap.put("changeDate", changeDate.toString());
      }
      //修改项目
      if(origin==null){
        scoreAAccountDetailMap.put("changeProject",null);
      }else{
        scoreAAccountDetailMap.put("changeProject", origin.toString());
      }
      //备注
      scoreAAccountDetailMap.put("operate", operate);

      if (scoreADetail.getNumber() > 0L) {
        //发红包
        scoreAAccountDetailMap.put("issuedScoreA",scoreADetail.getNumber().toString());

      } else {
        //用红包
        Long useScoreA=-scoreADetail.getNumber();
        scoreAAccountDetailMap.put("useScoreA",useScoreA.toString());
      }

      if (origin!= null) {
        //origin=1 2 3 4   时有 分润 佣金  serialNumber是 orderSid
        if (origin == 1 || origin == 2 || origin == 3 || origin == 4) {
          scoreAAccountDetailMap.put("serialNumber",scoreADetail.getOrderSid());
          orderSid = scoreADetail.getOrderSid();
          if (orderSid != null) {
            OffLineOrder offlineOrder = offLineOrderService.findOneByOrderSid(orderSid);
            if (offlineOrder != null) {
              scoreAAccountDetailMap.put("commissionIncome",offlineOrder.getLjCommission().toString());
              OffLineOrderShare
                  offLineOrderShar =
                  shareService.findOneByOrderId(offlineOrder.getId());
              if (offLineOrderShar != null) {
                scoreAAccountDetailMap.put("jfkShare", offLineOrderShar.getShareMoney().toString());
              }
            }
          }
        }
        if(origin != 1 && origin != 2 && origin != 3 && origin != 4)
        {
          if(origin==0){
            if (scoreA != null) {
              LeJiaUser leJiaUser = scoreA.getLeJiaUser();
              if (leJiaUser != null) {
                scoreAAccountDetailMap.put("serialNumber",leJiaUser.getUserSid());
              }
            }
          }
          //origin=56789   时  不知道有没有分润佣金  serialNumber还是orderSid
          orderSid = scoreADetail.getOrderSid();
          if (orderSid != null) {
            OffLineOrder offlineOrder = offLineOrderService.findOneByOrderSid(orderSid);
            if (offlineOrder != null) {
              scoreAAccountDetailMap.put("serialNumber",scoreADetail.getOrderSid());
              scoreAAccountDetailMap.put("commissionIncome",offlineOrder.getLjCommission().toString());
              OffLineOrderShare
                  offLineOrderShar =
                  shareService.findOneByOrderId(offlineOrder.getId());
              if (offLineOrderShar != null) {
                scoreAAccountDetailMap.put("jfkShare",offLineOrderShar.getShareMoney().toString());
              }
            }
          }else{
            if (scoreA != null) {
              LeJiaUser leJiaUser = scoreA.getLeJiaUser();
              if (leJiaUser != null) {
                scoreAAccountDetailMap.put("serialNumber",leJiaUser.getUserSid());
              }
            }
          }
        }
        scoreAAccountDetailList.add(scoreAAccountDetailMap);

      } else {
        //origin=null时没有 分润 佣金  serialNumber是userSid
        if (scoreA != null) {
          LeJiaUser leJiaUser = scoreA.getLeJiaUser();
          if (leJiaUser != null) {
            scoreAAccountDetailMap.put("serialNumber", leJiaUser.getUserSid());
          }
        }
        scoreAAccountDetailList.add(scoreAAccountDetailMap);
      }
    }
    return scoreAAccountDetailList;
  }

    //分布
    @RequestMapping(value = "/scoreAAccount/distributionList", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult getScoreaDistributionByAjax(@RequestBody ScoreAAccountDistributionCriteria scoreAAccountDistributionCriteria) {
        List<String> dateList = getDate(scoreAAccountDistributionCriteria);
        String startDate = dateList.get(0);
        String endDate = dateList.get(1);
        if (scoreAAccountDistributionCriteria.getOffset() == null) {
            scoreAAccountDistributionCriteria.setOffset(1);
        }
        List<Object[]> distributionList = scoreAAccountService.findScoreaDistribution(startDate, endDate);
        List<Object[]> pageList = new ArrayList<Object[]>();
        Long totalMoney = 0L;
        for (Object[] objects : distributionList) {
            Long money = new Long(objects[1].toString());
            totalMoney+=money;
        }
        int offset = scoreAAccountDistributionCriteria.getOffset();
        int totalElements = distributionList.size();
        double totalPages = Math.ceil(totalElements / 5.0);
        if (totalElements < offset * 5) {
            pageList = distributionList.subList((offset - 1) * 5, distributionList.size());
        } else {
            pageList = distributionList.subList((offset - 1) * 5, offset * 5);
        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("totalPages", totalPages);
        dataMap.put("totalElements", totalElements);
        dataMap.put("content", pageList);
        dataMap.put("totalMoney", totalMoney);
        return LejiaResult.ok(dataMap);
    }
    //折线图
    @RequestMapping(value = "/scoreAAccount/geMyChartByAjax", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult geMyChartByAjax(@RequestBody ScoreAAccountCriteria scoreAAccountCriteria) {
        String startDate=scoreAAccountCriteria.getStartDate();
        String endDate=scoreAAccountCriteria.getEndDate();
        if(startDate!=null&&endDate!=null){
            startDate =startDate.replaceAll("/","-");
            endDate = endDate.replaceAll("/","-");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> dateList = new ArrayList<Date>();
        List<String> dateStrList = new ArrayList<String>();
        if (startDate==null||endDate==null) {
            try {
                Calendar calendarDate = Calendar.getInstance();
                Date date = new Date();
                dateList.add(date);
                calendarDate.setTime(date);
                for (int i = 1; i < 7; i++) {
                    calendarDate.set(Calendar.DATE, calendarDate.get(Calendar.DATE) - 1);
                    dateList.add(sdf.parse(sdf.format(calendarDate.getTime())));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            Calendar cal = Calendar.getInstance();
            try {
                Date dBegin = sdf.parse(startDate);
                Date dEnd = sdf.parse(endDate);
                dateList = getDatesBetweenTwoDate(dBegin, dEnd);
                Collections.reverse(dateList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for(Date date:dateList){
            dateStrList.add(sdf.format(date));
        }
        if (startDate==null||endDate==null) {
            endDate=dateStrList.get(0).replaceAll("-","/")+" 23:59:59";
            startDate=dateStrList.get(dateStrList.size()-1).replaceAll("-","/")+" " + "00:00:00";
        }else {
            startDate = scoreAAccountCriteria.getStartDate() + " " + "00:00:00";
            endDate = scoreAAccountCriteria.getEndDate() + " 23:59:59";
        }
        scoreAAccountCriteria.setEndDate(endDate);
        scoreAAccountCriteria.setStartDate(startDate);
        Page page = scoreAAccountService.findScoreAAccountByPage(scoreAAccountCriteria, 10000);
        List<ScoreAAccount> scoreAAccountList=page.getContent();
        List<Double> useScoreAList=new ArrayList<Double>();
        List<Double> issuedScoreAList=new ArrayList<Double>();
        List<Double> commissionIncomeList=new ArrayList<Double>();
        List<Double> jfkShareList=new ArrayList<Double>();
        for(ScoreAAccount scoreAAccount :scoreAAccountList){
            useScoreAList.add( scoreAAccount.getUseScoreA().doubleValue()/100.0);
            issuedScoreAList.add(scoreAAccount.getIssuedScoreA().doubleValue()/100.0);
            commissionIncomeList.add(scoreAAccount.getCommissionIncome().doubleValue()/100.0);
            jfkShareList.add(scoreAAccount.getJfkShare().doubleValue()/100.0);
        }
        Map<String,Object> dataMap=new HashMap<String,Object>();
        dataMap.put("dateStrList",dateStrList);
        dataMap.put("useScoreAList",useScoreAList);
        dataMap.put("issuedScoreAList",issuedScoreAList);
        dataMap.put("jfkShareList",jfkShareList);
        dataMap.put("commissionIncomeList",commissionIncomeList);
        return LejiaResult.ok(dataMap);
    }


    //饼图
    @RequestMapping(value = "/scoreAAccount/getpieChartByAjax", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult getpieChartByAjax(@RequestBody ScoreAAccountDistributionCriteria scoreAAccountDistributionCriteria) {
        List<String> dateList = getDate(scoreAAccountDistributionCriteria);
        String startDate = dateList.get(0);
        String endDate = dateList.get(1);
        List<Object[]> distributionList = scoreAAccountService.findScoreaDistribution(startDate, endDate);
        List<String> nameList = new ArrayList<String>();
        List<Integer> valueList = new ArrayList<Integer>();
        for (Object[] objects : distributionList) {
            Integer origin = Integer.valueOf(objects[0].toString());
            Integer value = Integer.valueOf(objects[1].toString());
            switch (origin) {
                case 0:
                    nameList.add("关注送红包");

                    break;
                case 1:
                    nameList.add("线上返还");

                    break;
                case 2:
                    nameList.add("线上消费");

                    break;
                case 3:
                    nameList.add("线下消费");
                    break;
                case 4:
                    nameList.add("线下返还");
                    break;
                case 5:
                    nameList.add("活动返还");
                    break;
                case 6:
                    nameList.add("运动");
                    break;
                case 7:
                    nameList.add("摇一摇");
                    break;
                case 8:
                    nameList.add("APP分享");
                    break;
                case 9:
                    nameList.add("线下支付完成页注册会员");
                    break;
                case 10:
                    nameList.add("合伙人发福利");
                    break;
                case 11:
                    nameList.add("临时活动");
                    break;
                case 13:
                    nameList.add("充话费发送包");
                case 14001:
                    nameList.add("退款");
                    break;
            }
            valueList.add(value);
        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("nameList", nameList);
        dataMap.put("valueList", valueList);
        return LejiaResult.ok(dataMap);
    }

    List<String> getDate(ScoreAAccountDistributionCriteria scoreAAccountDistributionCriteria) {
        List<String> returnList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = null;
        String endDate = null;
        if (scoreAAccountDistributionCriteria.getStartDate() == null || scoreAAccountDistributionCriteria.getEndDate() == null) {
            try {
                Calendar calendarDate = Calendar.getInstance();
                Date dNow = new Date();   //当前时间
                List<Date> dateList = new ArrayList<Date>();
                for (int i = 0; i < 7; i++) {

                    Date dBefore = new Date();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dNow);
                    calendar.add(Calendar.DAY_OF_MONTH, -i);
                    dBefore = calendar.getTime();

                    String defaultStartDate = sdf.format(dBefore);
                    dateList.add(sdf.parse(defaultStartDate));

                }
                startDate = sdf.format(dateList.get(6)) + " " + "00:00:00";
                endDate = sdf.format(dateList.get(0)) + " 23:59:59";
                returnList.add(startDate);
                returnList.add(endDate);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            startDate = scoreAAccountDistributionCriteria.getStartDate() + " " + "00:00:00";
            endDate = scoreAAccountDistributionCriteria.getEndDate() + " 23:59:59";
            returnList.add(startDate);
            returnList.add(endDate);
        }
        return returnList;
    }
    public List getDatesBetweenTwoDate(Date beginDate, Date endDate) {
        List lDate = new ArrayList();
        lDate.add(beginDate);//把开始时间加入集合
        Calendar cal = Calendar.getInstance();
        //使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            //根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                lDate.add(cal.getTime());
            } else {
                break;
            }
        }
        lDate.add(endDate);//把结束时间加入集合
        return lDate;
    }

    /**
     *  2017/06/07   根据日期统计顶部红包数据
     */
    @RequestMapping(value = "/scoreAAccount/dailyTotal", method = RequestMethod.POST)
    @ResponseBody
    public LejiaResult findScoreAByDate(@RequestBody ScoreAAccountCriteria criteria) {
        String endDate = criteria.getEndDate();
        Date date = new Date(endDate);
        ScoreDailyTotal dailyTotal = scoreDailyTotalService.findByDeadLine(date);
        if(dailyTotal!=null) {
            return LejiaResult.ok(dailyTotal);
        }else {
            return LejiaResult.build(400,"该日暂无记录数据 ^_^ ！");
        }
    }
}
//
