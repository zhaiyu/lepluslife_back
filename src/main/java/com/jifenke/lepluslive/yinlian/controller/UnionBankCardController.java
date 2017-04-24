package com.jifenke.lepluslive.yinlian.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.user.domain.entities.BankCard;
import com.jifenke.lepluslive.user.domain.entities.LeJiaUser;
import com.jifenke.lepluslive.user.service.BankCardService;
import com.jifenke.lepluslive.user.service.UserService;
import com.jifenke.lepluslive.yinlian.domain.criteria.UnionBankCardCriteria;
import com.jifenke.lepluslive.yinlian.domain.entities.UnionBankCard;
import com.jifenke.lepluslive.yinlian.service.UnionBankCardService;
import com.jifenke.lepluslive.yinlian.service.UnionPayStoreService;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by lss on 17-4-17.
 */
@RestController
@RequestMapping("/manage/unionBankCard")
public class UnionBankCardController {
    @Inject
    private UnionPayStoreService unionPayStoreService;

    @Inject
    private UnionBankCardService unionBankCardService;

    @Inject
    private UserService userService;

    @Inject
    private BankCardService bankCardService;


    @RequestMapping("/unionBankCardPage")
    public ModelAndView unionBankCardPage(Model model) {
        return MvUtil.go("/yinlian/unionBankCardList");
    }


    @RequestMapping(value = "/getUnionBankCardByAjax", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult getUnionBankCardByAjax(@RequestBody UnionBankCardCriteria unionBankCardCriteria) {
        if (unionBankCardCriteria.getOffset() == null) {
            unionBankCardCriteria.setOffset(1);
        }
        Page page = unionBankCardService.findUnionBankCardByPage(unionBankCardCriteria, 10);
        List<UnionBankCard> list = page.getContent();
        List<BankCard> bankCards = new ArrayList<>();
        Map map = new HashMap();
        map.put("page", page);

        for (UnionBankCard unionBankCard : list) {
            String bankNumber = unionBankCard.getNumber();
            BankCard bankCard = bankCardService.findByNumber(bankNumber);
            bankCards.add(bankCard);
        }
        map.put("bankCards", bankCards);
        return LejiaResult.ok(map);
    }


    @RequestMapping(value = "/registerByHand", method = RequestMethod.POST)
    public
    @ResponseBody
    LejiaResult registerByHand(@RequestBody UnionBankCardCriteria unionBankCardCriteria) {
        String bankNumber = unionBankCardCriteria.getNumber();
        String phone = unionBankCardCriteria.getPhoneNumber();
        String userSid = unionBankCardCriteria.getUserSid();

        if (userSid != null && bankNumber != null && phone != null && userSid != "" && bankNumber != "" && phone != "") {
            LeJiaUser leJiaUser = userService.findUserBySid(userSid);
            UnionBankCard unionBankCard= unionBankCardService.findUnionBankCardByNumber(bankNumber);
            if (leJiaUser != null) {
                if (unionBankCard == null) {
                    unionBankCard = new UnionBankCard();
                    unionBankCard.setNumber(bankNumber);
                    unionBankCard.setCreateDate(new Date());
                }
                unionBankCard.setPhoneNumber(phone);
                unionBankCard.setRegisterWay(3);
                unionBankCard.setUserSid(leJiaUser.getUserSid());
                if (unionBankCard.getState() != 1) {
                    Map map = unionPayStoreService.register(bankNumber, phone);

                    if ("0000".equals(map.get("msg_rsp_code"))) {
                        unionBankCard.setState(1);
                        BankCard bankCard = new BankCard();
                        Date date = new Date();
                        bankCard.setLeJiaUser(leJiaUser);
                        bankCard.setBindDate(date);
                        bankCard.setState(1);
                        bankCard.setNumber(bankNumber);
                        bankCard.setCardLength(bankNumber.length());
                        bankCard.setPrefixNum(bankNumber.substring(0, 6));
                        bankCardService.saveOne(bankCard);
                        unionBankCardService.saveOne(unionBankCard);
                        return LejiaResult.ok();
                    }else {
                        return LejiaResult.build(501, "注册失败");
                    }
                }else {
                    return LejiaResult.build(501, "该会员卡已注册");
                }
            } else {
                return LejiaResult.build(501, "未找到乐加会员");
            }

        } else {
            return LejiaResult.build(501, "所填数据不能为空");
        }


    }


}
