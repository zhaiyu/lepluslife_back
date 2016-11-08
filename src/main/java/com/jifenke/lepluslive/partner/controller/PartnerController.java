package com.jifenke.lepluslive.partner.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.partner.controller.dto.PartnerDto;
import com.jifenke.lepluslive.partner.domain.entities.Partner;
import com.jifenke.lepluslive.partner.service.PartnerService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

/**
 * Created by wcg on 16/7/21.
 */
@RestController
@RequestMapping("/manage")
public class PartnerController {

  @Inject
  private PartnerService partnerService;

  @RequestMapping(value = "/partner")
  public ModelAndView goPartnerEditPage(Model model) {
    model.addAttribute("partners", partnerService.findAll());
    model.addAttribute("partnerManagers", partnerService.findAllPartnerManagerByWallet());
    return MvUtil.go("/partner/partnerList");
  }

  @RequestMapping(value = "/partner/editUser")
  public LejiaResult editPartnerPassword(@RequestBody Partner partner, Model model) {
    model.addAttribute("partners", partnerService.findAll());
    partnerService.editPartnerPassword(partner);
    return LejiaResult.ok();
  }

  @RequestMapping(value = "/partner/edit", method = RequestMethod.GET)
  public ModelAndView goCreatePartnertPage(Long id, Model model) {
    if (id != null) {
      model.addAttribute("partnerInfo", partnerService
          .findPartnerInfoByPartner(partnerService.findPartnerById(id)));
    }
    model.addAttribute("partnerManagers", partnerService.findAllPartnerManager());
    return MvUtil.go("/partner/createPartner");
  }

  @RequestMapping(value = "/partner", method = RequestMethod.POST)
  public LejiaResult createPartner(@RequestBody PartnerDto partnerDto) {
    partnerService.createPartner(partnerDto);
    return LejiaResult.ok();
  }

  @RequestMapping(value = "/partner", method = RequestMethod.PUT)
  public LejiaResult editPartner(@RequestBody PartnerDto partnerDto) {
    partnerService.editPartner(partnerDto);
    return LejiaResult.ok();
  }

}
