package com.jifenke.lepluslive.groupon.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.groupon.controller.dto.GrouponProductDto;
import com.jifenke.lepluslive.groupon.domain.criteria.GrouponProductCriteria;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponMerchant;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponProduct;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponProductDetail;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponScrollPicture;
import com.jifenke.lepluslive.groupon.service.GrouponMerchantService;
import com.jifenke.lepluslive.groupon.service.GrouponProductDetailService;
import com.jifenke.lepluslive.groupon.service.GrouponProductService;
import com.jifenke.lepluslive.groupon.service.GrouponScrollPictureService;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * 团购产品 Controller
 * Created by xf on 17-6-16.
 */
@Controller
@RequestMapping("/manage")
public class GrouponProductController {

    @Inject
    private GrouponProductService grouponProductService;
    @Inject
    private GrouponMerchantService grouponMerchantService;
    @Inject
    private GrouponProductDetailService grouponProductDetailService;
    @Inject
    private GrouponScrollPictureService grouponScrollPictureService;

    /**
     * 跳转到列表页面
     * Created by xf on 2017-06-21.
     */
    @RequestMapping("/grouponProduct/list")
    public ModelAndView toListPage() {
        return MvUtil.go("/groupon/productList");
    }

    /**
     * 跳转到添加页面
     * Created by xf on 2017-06-21.
     */
    @RequestMapping("/grouponProduct/add")
    public ModelAndView toAddPage() {
        return MvUtil.go("/groupon/productAdd");
    }

    /**
     * 分页展示
     * Created by xf on 2017-06-16.
     */
    @RequestMapping("/grouponProduct/findByCriteria")
    @ResponseBody
    public LejiaResult findByCriteria(@RequestBody GrouponProductCriteria productCriteria) {
        if (productCriteria.getOffset() == null) {
            productCriteria.setOffset(1);
        }
        Page<GrouponProduct> page = grouponProductService.findByCriteria(productCriteria, 10);
        List<GrouponProduct> products = page.getContent();
        Map<String, Object> data = new HashMap<>();
        data.put("data", page);
        if (products != null && products.size() > 0) {
            List<Long> merchants = grouponProductService.countMerchantByProducts(products);
            data.put("bindMerchants", merchants);
        }
        return LejiaResult.ok(data);
    }

    /**
     * 新建产品
     * Created by xf on 2017-06-20.
     */
    @RequestMapping(value = "/grouponProduct/save", method = RequestMethod.POST)
    @ResponseBody
    public LejiaResult saveProduct(@RequestBody GrouponProductDto grouponProductDto) {
        boolean result = grouponProductService.saveProduct(grouponProductDto);
        if (result) {
            return LejiaResult.ok();
        } else {
            return LejiaResult.build(500, "保存失败，请联系系统管理员！");
        }
    }

    /***
     *  编辑团购产品页面
     *  Created by xf on 2017-06-27.
     */
    @RequestMapping(value = "/grouponProduct/edit",method = RequestMethod.GET)
    public ModelAndView toAddPage(Long id, Model model) {
        GrouponProduct product = grouponProductService.findById(id);
        List<GrouponMerchant> grouponMerchants = grouponMerchantService.findByGrouponProduct(product);
        List<GrouponScrollPicture> grouponScrollPictures = grouponScrollPictureService.findByGrouponProduct(product);
        List<GrouponProductDetail> grouponProductDetails = grouponProductDetailService.findByGrouponProduct(product);
        model.addAttribute("product",product);
        model.addAttribute("merchants",grouponMerchants);
        model.addAttribute("scrollPictures",grouponScrollPictures);
        model.addAttribute("productDetails",grouponProductDetails);
        model.addAttribute("detailSize",grouponProductDetails==null?0:grouponProductDetails.size());
        model.addAttribute("scrollSize",grouponScrollPictures==null?0:grouponScrollPictures.size());
        return MvUtil.go("/groupon/productAdd");
    }


    /**
     * 保存修改
     * Created by xf on 2017-06-20.
     */
    @RequestMapping(value = "/grouponProduct/saveEdit", method = RequestMethod.POST)
    @ResponseBody
    public LejiaResult saveEdit(@RequestBody GrouponProductDto grouponProductDto) {
        boolean result = grouponProductService.saveEdit(grouponProductDto);
        if (result) {
            return LejiaResult.ok();
        } else {
            return LejiaResult.build(500, "保存失败，请联系系统管理员！");
        }
    }

    /**
     *  下架商品
     *  Created by xf on 2017-06-28.
     */
    @RequestMapping(value = "/grouponProduct/down", method = RequestMethod.GET)
    @ResponseBody
    public LejiaResult productDown(String sid) {
        GrouponProduct product = grouponProductService.findBySid(sid);
        if(product==null) {
            return LejiaResult.build(400,"下架失败");
        }else {
            grouponProductService.changeState(product,0);
            return LejiaResult.ok();
        }
    }
    /**
     *   上架商品
     *   Created by xf on 2017-06-28.
     */
    @RequestMapping(value = "/grouponProduct/up", method = RequestMethod.GET)
    @ResponseBody
    public LejiaResult productUp(String sid) {
        GrouponProduct product = grouponProductService.findBySid(sid);
        if(product==null) {
            return LejiaResult.build(400,"上架失败");
        }else {
            grouponProductService.changeState(product,1);
            return LejiaResult.ok();
        }
    }

}
