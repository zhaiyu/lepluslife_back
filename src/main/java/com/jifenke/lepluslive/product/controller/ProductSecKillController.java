package com.jifenke.lepluslive.product.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.product.controller.dto.LimitProductDto;
import com.jifenke.lepluslive.product.domain.entities.Product;
import com.jifenke.lepluslive.product.domain.entities.ProductDetail;
import com.jifenke.lepluslive.product.domain.entities.ProductSecKill;
import com.jifenke.lepluslive.product.domain.entities.ProductSecKillCriteria;
import com.jifenke.lepluslive.product.domain.entities.ProductSecKillTime;
import com.jifenke.lepluslive.product.domain.entities.ProductSpec;
import com.jifenke.lepluslive.product.domain.entities.ScrollPicture;
import com.jifenke.lepluslive.product.service.ProductSecKillTimeService;
import com.jifenke.lepluslive.product.service.ProductSecKillService;
import com.jifenke.lepluslive.product.service.ProductService;
import com.jifenke.lepluslive.product.service.ProductSpecService;
import com.jifenke.lepluslive.product.service.ScrollPictureService;

import org.springframework.data.domain.Page;
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
 * 秒杀管理 秒杀时段
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
  private ProductSecKillTimeService productSecKillTimeService;
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
    String day1 = getDay(0,date1,sdf);//今天
    String day2 = getDay(1,date1,sdf);//明天
    String day3 = getDay(2,date1,sdf);//后天
    model.addAttribute("day1", day1);
    model.addAttribute("day2", day2);
    model.addAttribute("day3", day3);

    return MvUtil.go("/product/secKill/overview");
  }
  public String getDay(int i, Date date, SimpleDateFormat sdf) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_YEAR, i);
    return sdf.format(calendar.getTime());
  }

  /**
   * 秒杀概览 ajaxList
   * @param criteria
   */
  @RequestMapping(value = "/productSecKill/ajaxList", method = RequestMethod.POST)
  @ResponseBody
  public LejiaResult productSecKill_ajaxList(@RequestBody ProductSecKillCriteria criteria) {

    if (criteria.getOffset() == null) {
      criteria.setOffset(1);
    }
    criteria.setType(3);//秒杀商品 商品类型type=3
    Map result = productSecKillService.findProductSecKillByPage(criteria);

    return LejiaResult.ok(result);
  }


  /**
   * 秒杀商品 编辑页面
   * @param pskId 秒杀概览id(非必需)
   */
  @RequestMapping(value = "/productSecKill/editPage", method = RequestMethod.GET)
  public ModelAndView productSecKill_editPage(@RequestParam(required = false) Integer pskId, Model model) {
    ProductSecKill productSecKill = null;
    Product product = null;                         //秒杀商品
    ProductSecKillTime productSecKillTime = null;   //秒杀时段
    Product linkProduct = null;                     //关联商品
    Integer edit_psktId = -1;                       //秒杀时段编辑id
    Integer isLinkProduct = 0;                      //有无关联商品 0无 1有

    List<ProductDetail> detailList = null;
    List<ProductSpec> specList = null;
    List<ScrollPicture> scrollPictureList = null;
    if (pskId != null) {
      productSecKill = productSecKillService.findById(pskId);
      if (productSecKill != null){
        model.addAttribute("psk_id", productSecKill.getId());

        product = productSecKill.getProduct();
        productSecKillTime = productSecKill.getProductSecKillTime();
        if (productSecKillTime != null){
          edit_psktId = productSecKillTime.getId();
          model.addAttribute("productSecKillTime", productSecKillTime);
        }
        linkProduct = productSecKill.getLinkProduct();
        if (linkProduct != null){
          model.addAttribute("linkProduct", linkProduct);
        }
        isLinkProduct = productSecKill.getIsLinkProduct();
      }

    }
    model.addAttribute("edit_psktId", edit_psktId);
    model.addAttribute("isLinkProduct", isLinkProduct);

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
    //查询所有秒杀时段
    List<ProductSecKillTime> list_t = productSecKillTimeService.findAll();
    model.addAttribute("list_t", list_t);

    return MvUtil.go("/product/secKill/secKillEdit");
  }

  /**
   * 秒杀商品 保存
   */
  @RequestMapping(value = "/productSecKill/save", method = RequestMethod.POST)
  public
  @ResponseBody
  LejiaResult productSecKill_save(@RequestBody LimitProductDto productDto) {
    try {
      productSecKillService.saveProductSecKill(productDto);
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



  /********************************************* 秒杀时段 start *****************************************************/
  /**
   * 秒杀时段 list页面
   * @param
   */
  @RequestMapping(value = "/productSecKill_time", method = RequestMethod.GET)
  public ModelAndView productSecKill_time(Model model) {
    return MvUtil.go("/product/secKill/secKillTimeList");
  }
  /**
   * 秒杀时段 ajaxList
   * @param
   */
  @RequestMapping(value = "/productSecKill_time/ajaxList", method = RequestMethod.POST)
  @ResponseBody
  public LejiaResult productSecKill_time_ajaxList(@RequestBody ProductSecKillCriteria criteria) {
    if (criteria != null) {
      if (criteria.getOffset() == null) {
        criteria.setOffset(1);
      }
    }
    Page page = productSecKillTimeService.findByCriteria(criteria);
    return LejiaResult.ok(page);
  }
  /**
   * 秒杀时段 编辑
   * @param
   */
  @RequestMapping(value = "/productSecKill_time/find/{id}", method = RequestMethod.GET)
  @ResponseBody
  public LejiaResult productSecKill_time_findById(@PathVariable Integer id) {
    ProductSecKillTime productSecKillTime = productSecKillTimeService.findById(id);
    return LejiaResult.ok(productSecKillTime);
  }
  /**
   * 秒杀时段 保存
   */
  @RequestMapping(value = "/productSecKill_time/save", method = RequestMethod.POST)
  @ResponseBody
  public LejiaResult productSecKill_time_save(@RequestBody ProductSecKillTime productSecKillTime) {
    try {
      productSecKillTimeService.saveProductSecKillTime(productSecKillTime);
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(303, "保存异常");
    }
    return LejiaResult.ok();
  }
  /**
   * 秒杀时段 删除
   */
  @RequestMapping(value = "/productSecKill_time/delete/{id}", method = RequestMethod.POST)
  public LejiaResult productSecKill_time_delete(@PathVariable Integer id) {
    try {
      productSecKillTimeService.deleteProductSecKillTime(id);
      return LejiaResult.build(200, "删除成功");
    } catch (Exception e) {
      e.printStackTrace();
      return LejiaResult.build(500, "删除失败");
    }
  }

  /********************************************* 秒杀时段 end *****************************************************/

}
