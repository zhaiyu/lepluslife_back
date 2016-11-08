package com.jifenke.lepluslive.merchant.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.merchant.controller.dto.MerchantPosDto;
import com.jifenke.lepluslive.merchant.domain.criteria.MerchantPosCriteria;
import com.jifenke.lepluslive.merchant.domain.entities.MerchantPos;
import com.jifenke.lepluslive.merchant.service.MerchantPosService;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

/**
 * Created by wcg on 16/9/5.
 */
@RestController
@RequestMapping("/manage")
public class PosController {

  @Inject
  private MerchantPosService merchantPosService;

  @RequestMapping(value = "/pos", method = RequestMethod.GET)
  public ModelAndView posManage() {
    return MvUtil.go("/merchant/posManage");
  }

  @RequestMapping(value = "/pos", method = RequestMethod.POST)
  public LejiaResult getPosListByCriteria(@RequestBody MerchantPosCriteria merchantPosCriteria) {
    Page
        page =
        merchantPosService.findMerchantPosByCriteria(merchantPosCriteria, 10);
    List<MerchantPos> content = page.getContent();
    List<MerchantPosDto> merchantPosDtos = content.stream().map(merchantPos -> {
      MerchantPosDto merchantPosDto = new MerchantPosDto();
      merchantPosDto.setId(merchantPos.getId());
      merchantPosDto.setPosId(merchantPos.getPosId());
      merchantPosDto.setPsamCard(merchantPos.getPsamCard());
      merchantPosDto.setMerchant(merchantPos.getMerchant());
      merchantPosDto.setCreatedDate(merchantPos.getCreatedDate());
      merchantPosDto.setLjCommission(merchantPos.getLjCommission());
      merchantPosDto.setCreditCardCommission(merchantPos.getCreditCardCommission());
      merchantPosDto.setDebitCardCommission(merchantPos.getDebitCardCommission());
      merchantPosDto.setAliCommission(merchantPos.getAliCommission());
      merchantPosDto.setAliUserCommission(merchantPos.getAliUserCommission());
      merchantPosDto.setWxCommission(merchantPos.getWxCommission());
      merchantPosDto.setCeil(merchantPos.getCeil());
      merchantPosDto.setWxUserCommission(merchantPos.getWxUserCommission());
      return merchantPosService.countPosOrderFlow(merchantPosDto);
    }).collect(Collectors.toList());
    Map results = new HashMap<>();
    results.put("totalElements", page.getTotalElements());
    results.put("totalPages", page.getTotalPages());
    results.put("content", merchantPosDtos);
    return LejiaResult.ok(results);
  }

  @RequestMapping(value = "/pos/change_commission", method = RequestMethod.GET)
  public LejiaResult changeCommission(@RequestParam String id,
                                       @RequestParam BigDecimal commission) {
    merchantPosService.changePosCommission(id,commission);
    return LejiaResult.ok();
  }

  @RequestMapping(value="/pos/save_pos",method = RequestMethod.POST)
  public LejiaResult savePosMachine(@RequestBody MerchantPos merchantPos) {
    try{
      merchantPosService.savePosMachine(merchantPos);
      return LejiaResult.build(200,"信息保存成功!");
    }catch(Exception e) {
      return LejiaResult.build(500,"信息保存失败!");
    }
  }


  @RequestMapping(value="/pos/getById/{id}",method = RequestMethod.GET)
  @ResponseBody
  public MerchantPos getPosById(@PathVariable Long id) {
    MerchantPos merchantPos = merchantPosService.findPosById(id);
    return merchantPos;
  }

  @RequestMapping(value="/pos/save_ljCommision",method = RequestMethod.POST)
  @ResponseBody
  public LejiaResult saveLjCommision(@RequestBody MerchantPos merchantPos) {
    try {
      merchantPosService.saveLjCommision(merchantPos);
      return LejiaResult.build(200,"信息保存成功!");
    } catch (Exception e) {
      return LejiaResult.build(500,"信息保存失败!");
    }

  }


}
