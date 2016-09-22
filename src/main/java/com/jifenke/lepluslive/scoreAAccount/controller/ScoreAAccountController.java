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
import com.jifenke.lepluslive.score.service.ScoreADetailService;
import com.jifenke.lepluslive.scoreAAccount.controller.view.ScoreAAccountDetailViewExcel;
import com.jifenke.lepluslive.scoreAAccount.controller.view.ScoreAAccountViewExcel;
import com.jifenke.lepluslive.scoreAAccount.domain.criteria.ScoreAAccountCriteria;
import com.jifenke.lepluslive.scoreAAccount.domain.entities.ScoreAAccount;
import com.jifenke.lepluslive.scoreAAccount.service.ScoreAAccountService;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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
    List<Map> scoreAAccountDetailList=scoreAAccountDetailList(scoreDetailCriteria,10000);
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
}
