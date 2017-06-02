package com.jifenke.lepluslive.score.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.score.domain.criteria.ScoreCriteria;
import com.jifenke.lepluslive.score.service.ScoreCService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lss on 2017/6/1.
 */
@Controller
@RequestMapping("/manage/scoreC")
public class ScoreCController {

    @Inject
    private ScoreCService scoreCService;


    @RequestMapping(value = "/scoreCPage", method = RequestMethod.GET)
    public ModelAndView scoreCPage(Model model) {
        long scoreCSum = 0l;
        long useScoreCSum = 0l;
        long sendScoreCSum = 0l;
        scoreCSum = scoreCService.scoreCSum();
        useScoreCSum = scoreCService.useScoreCSum();
        sendScoreCSum = scoreCService.sendScoreCSum();
        model.addAttribute("scoreCSum", scoreCSum);
        model.addAttribute("useScoreCSum", useScoreCSum);
        model.addAttribute("sendScoreCSum", sendScoreCSum);
        return MvUtil.go("/score/scoreCList");
    }


    @RequestMapping(value = "/statistics", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult statistics(@RequestBody ScoreCriteria scoreCriteria) {
        if (scoreCriteria.getStartDate() == null || scoreCriteria.getEndDate() == null) {
            SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 10);
            Date startDate = calendar.getTime();
            String startDateStr = dft.format(startDate);
            String endDateStr = dft.format(new Date());
            scoreCriteria.setStartDate(startDateStr);
            scoreCriteria.setEndDate(endDateStr);
        }
        List<Object[]> objects = scoreCService.findScoreCStatistics(scoreCriteria.getStartDate(), scoreCriteria.getEndDate());
        List<Object[]> objects2 = pageDate(objects, scoreCriteria);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<Map<String, String>> scoreCStatistics = new ArrayList<Map<String, String>>();

        for (Object[] objs : objects2) {
            Map<String, String> map = new HashMap<String, String>();
            if (objs[0] != null) {
                map.put("scoreCStatisticsDate", objs[0].toString());
            } else {
                map.put("scoreCStatisticsDate", "--");
            }

            if (objs[1] != null) {
                map.put("scoreCStatisticsSend", objs[1].toString());
            } else {
                map.put("scoreCStatisticsSend", "--");
            }

            if (objs[2] != null) {
                map.put("scoreCStatisticsUse", objs[2].toString());
            } else {
                map.put("scoreCStatisticsUse", "--");
            }
            scoreCStatistics.add(map);
        }


        int totalPages = (int) Math.ceil(objects.size() / 10.0);
        dataMap.put("totalPages", totalPages);
        dataMap.put("totalElements", objects.size());
        dataMap.put("scoreCStatistics", scoreCStatistics);


        long sendScoreC = scoreCService.findSendScoreCByDate(scoreCriteria.getStartDate(), scoreCriteria.getEndDate());
        long useScoreC = scoreCService.findUseScoreCByDate(scoreCriteria.getStartDate(), scoreCriteria.getEndDate());
        long handScoreC=useScoreC+sendScoreC;
        dataMap.put("sendScoreC", sendScoreC);
        dataMap.put("useScoreC", useScoreC);
        dataMap.put("handScoreC", handScoreC);
        return LejiaResult.ok(dataMap);
    }


    //翻页
    public List<Object[]> pageDate(List<Object[]> objects, ScoreCriteria scoreCriteria) {

        if (scoreCriteria.getOffset() == null) {
            scoreCriteria.setOffset(1);
        }
        int objectsSize = objects.size();
        if (objectsSize < 11) {
            return objects;
        } else {
            int pageStartCount = scoreCriteria.getOffset() * 10;
            List<Object[]> objects2 = new ArrayList<Object[]>();

            if (objectsSize < pageStartCount + 10) {
                for (int i = 0; i < objectsSize - pageStartCount + 10; i++) {
                    objects2.add(objects.get(pageStartCount - objectsSize - 1 + i));
                }
            } else {
                for (int i = 0; i < 10; i++) {
                    objects2.add(objects.get(pageStartCount - 1 + i));
                }
            }
            return objects2;
        }

    }




    @RequestMapping(value = "/serchScoreCDetail", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult serchScoreCDetail(@RequestBody ScoreCriteria scoreCriteria) {
       if(scoreCriteria.getOffset()==null){
           scoreCriteria.setOffset(1);
       }
        return LejiaResult.ok();
    }


}
