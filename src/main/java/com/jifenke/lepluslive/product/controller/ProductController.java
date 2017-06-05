package com.jifenke.lepluslive.product.controller;

import com.jifenke.lepluslive.banner.domain.entities.Banner;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.product.controller.dto.LimitProductDto;
import com.jifenke.lepluslive.product.controller.dto.ProductDto;
import com.jifenke.lepluslive.product.controller.view.GoldProductViewExcel;
import com.jifenke.lepluslive.product.controller.view.ProductViewExcel;
import com.jifenke.lepluslive.product.domain.entities.*;
import com.jifenke.lepluslive.product.service.ProductService;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.PaginationUtil;
import com.jifenke.lepluslive.product.service.ProductShareService;
import com.jifenke.lepluslive.product.service.ProductSpecService;
import com.jifenke.lepluslive.product.service.ScrollPictureService;
import com.jifenke.lepluslive.weixin.service.CategoryService;

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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by wcg on 16/3/9.
 */
@RestController
@RequestMapping("/manage")
public class ProductController {

    @Inject
    private ProductService productService;

    @Inject
    private ScrollPictureService scrollPictureService;

    @Inject
    private ProductSpecService productSpecService;

    @Inject
    private CategoryService categoryService;

    @Inject
    private ProductShareService productShareService;

    @Inject
    private ProductViewExcel productViewExcel;

    @Inject
    private GoldProductViewExcel goldProductViewExcel;
    /**
     * 普通商品列表页   16/11/03
     */
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public ModelAndView findPageProduct(Model model) {

        List<ProductType> productTypes = productService.findAllProductType();
        model.addAttribute("productTypes", productTypes);
        model.addAttribute("markList", categoryService.findAllByCategory(1));
        return MvUtil.go("/product/common/index");
    }

    /**
     * 分页获取普通商品列表  16/11/03
     */
    @RequestMapping(value = "/product/ajaxList", method = RequestMethod.POST)
    public LejiaResult ajaxList(@RequestBody ProductCriteria criteria) {
        return LejiaResult.ok(productService.findCommonProductByPage(criteria,10));
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public LejiaResult deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return LejiaResult.ok();
        } catch (Exception e) {
            return new LejiaResult(500, "删除商品失败", null);
        }
    }

    @RequestMapping(value = "/product/edit", method = RequestMethod.GET)
    public ModelAndView goEditProductPage(@RequestParam(required = false) Long id, Model model) {

        Product product = null;
        List<ProductDetail> detailList = null;
        List<ScrollPicture> scrollPictureList = null;
        if (id != null) {
            product = productService.findOneProduct(id);
        } else {
            model.addAttribute("sid", productService.getTotalCount());
        }
        if (product != null) {
            model.addAttribute("product", product);
            detailList = productService.findAllProductDetailsByProduct(product);
            scrollPictureList = scrollPictureService.findAllScorllPicture(product);
            model.addAttribute("detailList", detailList);
            if (detailList == null) {
                model.addAttribute("detailSize", 1);
            } else {
                model.addAttribute("detailSize", detailList.size());
            }
            model.addAttribute("scrollList", scrollPictureList);
            if (scrollPictureList == null) {
                model.addAttribute("scrollSize", 1);
            } else {
                model.addAttribute("scrollSize", scrollPictureList.size());
            }
        }
        model.addAttribute("productTypes", productService.findAllProductType());
        model.addAttribute("markList", categoryService.findAllByCategory(1));

        return MvUtil.go("/product/common/productCreate");
    }

    /**
     * 普通商品保存 16/11/03
     */
    @RequestMapping(value = "/product/save", method = RequestMethod.POST)
    public LejiaResult ajaxSave(@RequestBody LimitProductDto productDto) {
        try {
            productService.saveCommonProduct(productDto);
        } catch (Exception e) {
            e.printStackTrace();
            return LejiaResult.build(303, "保存异常");
        }
        return LejiaResult.ok();
    }

    @RequestMapping(value = "/product/putOn/{id}")
    public LejiaResult putProduct(@PathVariable Long id) {
        return productService.putOnProduct(id);
    }

    @RequestMapping(value = "/product/putOff/{id}")
    public LejiaResult pullProduct(@PathVariable Long id) {
        return productService.pullOffProduct(id);
    }

    @RequestMapping(value = "/product/productType", method = RequestMethod.PUT)
    public void editProductType(@RequestBody ProductType productType) {
        productService.editProductType(productType);
    }

    @RequestMapping(value = "/product/productType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView goProductTypePage(Model model) {
        List<ProductType> productTypes = productService.findAllProductType();
        model.addAttribute("productTypes", productTypes);
        return MvUtil.go("/product/productTypeList");
    }

    @RequestMapping(value = "/product/productType/{id}", method = RequestMethod.DELETE)
    public void deleteProductType(@PathVariable Integer id) {
        productService.deleteProductType(id);
    }

    @RequestMapping(value = "/product/productType", method = RequestMethod.POST)
    public void addProductType(@RequestBody ProductType productType) {
        productService.addProductType(productType);
    }

    /**
     * 普通商品规格管理页面  16/11/03
     *
     * @param id 商品ID
     */
    @RequestMapping(value = "/product/specManage", method = RequestMethod.GET)
    public ModelAndView goSpecManagePage(@RequestParam Long id, Model model) {
        Product product = productService.findOneProduct(id);
        model.addAttribute("product", product);
        model.addAttribute("productSpecs", productSpecService.findProductSpecsByProduct(product));
        return MvUtil.go("/product/common/productSpec");
    }

    @RequestMapping(value = "/product/qrCode", method = RequestMethod.GET)
    public
    @ResponseBody
    LejiaResult productQrCode(@RequestParam Long id) {
        return LejiaResult.build(200, productService.qrCodeManage(id));
    }





    @RequestMapping(value = "/product/shareProduct/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    LejiaResult shareProduct(@PathVariable Long id) {
        ProductShare productShare = productShareService.findByProductId(id);
        return LejiaResult.ok(productShare);
    }



    @RequestMapping(value = "/product/shareProductConfirm", method = RequestMethod.POST)
    public LejiaResult shareProductConfirm(@RequestBody ProductShare productShare) {
        Product product = productShare.getProduct();
        ProductShare productShare1 = productShareService.findByProductId(product.getId());

        if (productShare1 == null) {
            ProductShare productShare2 = new ProductShare();
            productShare2.setDateCreated(new Date());
            productShare2.setDescription(productShare.getDescription());
            productShare2.setPicture(productShare.getPicture());
            productShare2.setTitle(productShare.getTitle());
            Product product1 = productService.findOneProduct(product.getId());
            productShare2.setProduct(product1);
            try {
                productShareService.saveOne(productShare2);
                return LejiaResult.ok();
            } catch (Exception e) {
                return LejiaResult.build(500, "serverError");
            }
        } else {
            productShare1.setTitle(productShare.getTitle());
            productShare1.setPicture(productShare.getPicture());
            productShare1.setDescription(productShare.getDescription());
            try {
                productShareService.saveOne(productShare1);
                return LejiaResult.ok();
            } catch (Exception e) {
                return LejiaResult.build(500, "serverError");
            }

        }


    }

    @RequestMapping(value = "/product/common/export", method = RequestMethod.POST)
    public ModelAndView exportExcel(ProductCriteria productCriteria) {
        if (productCriteria.getOffset() == null) {
            productCriteria.setOffset(1);
        }
        Map<String, Object> result=productService.findCommonProductByPage(productCriteria,10000);

        Map map = new HashMap();
        map.put("result",result);

        return new ModelAndView(productViewExcel, map);
    }

    @RequestMapping(value = "/product/gold/export", method = RequestMethod.POST)
    public ModelAndView exportGoldExcel(ProductCriteria productCriteria) {
        if (productCriteria.getOffset() == null) {
            productCriteria.setOffset(1);
        }
        Map<String, Object> goldResult=productService.findCommonProductByPage(productCriteria,10000);

        Map map = new HashMap();
        map.put("goldResult",goldResult);

        return new ModelAndView(goldProductViewExcel, map);
    }





}
