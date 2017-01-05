package com.jifenke.lepluslive.product.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.product.controller.dto.LimitProductDto;
import com.jifenke.lepluslive.product.domain.entities.Product;
import com.jifenke.lepluslive.product.domain.entities.ProductCriteria;
import com.jifenke.lepluslive.product.domain.entities.ProductDetail;
import com.jifenke.lepluslive.product.domain.entities.ProductSpec;
import com.jifenke.lepluslive.product.domain.entities.ScrollPicture;
import com.jifenke.lepluslive.product.service.ProductSecKillService;
import com.jifenke.lepluslive.product.service.ProductService;
import com.jifenke.lepluslive.product.service.ProductSpecService;
import com.jifenke.lepluslive.product.service.ScrollPictureService;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 秒杀管理
 * Created by tqy on 16/12/30.
 */
@RestController
@RequestMapping("/manage")
public class ProductSecKillController {

  @Inject
  private ProductService productService;
  @Inject
  private ScrollPictureService scrollPictureService;
  @Inject
  private ProductSpecService productSpecService;
  @Inject
  private ProductSecKillService productSecKillService;

  /**
   * 秒杀概览 overview
   * @param secKillDate 秒杀日期(时段名称, 格式: 2016-12-30)
   */
  @RequestMapping(value = "/productSecKill/overview", method = RequestMethod.GET)
  public ModelAndView productSecKill_overview(@RequestParam(required = false) String secKillDate, Model model){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date1 = new Date();
    if(secKillDate==null || secKillDate.equals("")){
      secKillDate = sdf.format(date1);
    }
    List<Object[]> list1 = productSecKillService.findProductSecKillByDateAndState(secKillDate,1);//上架
    List<Object[]> list0 = productSecKillService.findProductSecKillByDateAndState(secKillDate,0);//下架
    String day1 = getDay(0,date1,sdf);//今天
    String day2 = getDay(1,date1,sdf);//明天
    String day3 = getDay(2,date1,sdf);//后天

    model.addAttribute("day1", day1);
    model.addAttribute("day2", day2);
    model.addAttribute("day3", day3);
    model.addAttribute("list1", list1);
    model.addAttribute("list0", list0);
    return MvUtil.go("/product/secKill/overview");
  }
  public String getDay(int i, Date date, SimpleDateFormat sdf) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_YEAR, i);
    return sdf.format(calendar.getTime());
  }



  /**
   * 秒杀时段
   * @param type product 类型
   */
  @RequestMapping(value = "/productSecKill_time_list", method = RequestMethod.GET)
  public ModelAndView productSecKill_time_list(@RequestParam Integer type, Model model) {
    if (type == null) {
      type = 1;
    }
    model.addAttribute("type", type);
    return MvUtil.go("/product/secKill/productSecKill_time_list");
  }

  /**
   * 秒杀商品
   * @param type product类型
   */
  @RequestMapping(value = "/productSecKill_list", method = RequestMethod.GET)
  public ModelAndView productSecKill_list(@RequestParam Integer type, Model model) {
    if (type == null) {
      type = 1;
    }
    model.addAttribute("type", type);
    return MvUtil.go("/product/secKill/productSecKill_list");
  }



  /**
   * 秒杀时段 编辑
   * @param productId 商品id
   */
  @RequestMapping(value = "/productSecKill_time/edit", method = RequestMethod.GET)
  public ModelAndView productSecKill_time_edit(@RequestParam(required = false) Long productId, Model model) {
    Product product = null;
    List<ProductDetail> detailList = null;
    List<ProductSpec> specList = null;
    List<ScrollPicture> scrollPictureList = null;
    if (productId != null) {
      product = productService.findOneProduct(productId);
    }
    if (product != null) {
      model.addAttribute("product", product);
      detailList = productService.findAllProductDetailsByProduct(product);
      specList = productSpecService.findProductSpecsByProductAndState(product);
      scrollPictureList = scrollPictureService.findAllScorllPicture(product);
      model.addAttribute("detailList", detailList);
      if (detailList == null) {
        model.addAttribute("detailSize", 1);
      } else {
        model.addAttribute("detailSize", detailList.size());
      }
      model.addAttribute("specList", specList);
      if (specList == null) {
        model.addAttribute("specSize", 1);
      } else {
        model.addAttribute("specSize", specList.size());
      }
      model.addAttribute("scrollList", scrollPictureList);
      if (scrollPictureList == null) {
        model.addAttribute("scrollSize", 1);
      } else {
        model.addAttribute("scrollSize", scrollPictureList.size());
      }
    }
    return MvUtil.go("/product/secKill/productSecKill_time_edit");
  }

  /**
   * 秒杀商品 编辑
   * @param type product类型
   */
  @RequestMapping(value = "/productSecKill/edit", method = RequestMethod.GET)
  public ModelAndView productSecKill_edit(@RequestParam Integer type, Model model) {
    if (type == null) {
      type = 1;
    }
    model.addAttribute("type", type);
    return MvUtil.go("/product/secKill/productSecKill_edit");
  }

  /**
   * 秒杀时段 保存
   */
  @RequestMapping(value = "/productSecKill_time/save", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult productSecKill_time_save(@RequestBody LimitProductDto productDto) {
    try {
      productService.saveLimitProduct(productDto);
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(303, "保存异常");
    }
    return LejiaResult.ok();
  }

  /**
   * 秒杀商品 保存
   */
  @RequestMapping(value = "/productSecKill/save", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult productSecKill_save(@RequestBody LimitProductDto productDto) {
    try {
      productService.saveLimitProduct(productDto);
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(303, "保存异常");
    }
    return LejiaResult.ok();
  }



  /**
   * 上架或下架
   *
   * @param id 商品ID
   */
  @RequestMapping(value = "/productSecKill/state/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult changeStatus(@PathVariable Long id) {
    productService.changeState(id);
    return LejiaResult.ok("ok");
  }


}
