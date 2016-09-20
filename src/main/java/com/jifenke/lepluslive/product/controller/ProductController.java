package com.jifenke.lepluslive.product.controller;

import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.product.controller.dto.ProductDto;
import com.jifenke.lepluslive.product.domain.entities.Product;
import com.jifenke.lepluslive.product.domain.entities.ProductCriteria;
import com.jifenke.lepluslive.product.domain.entities.ProductType;
import com.jifenke.lepluslive.product.service.ProductService;
import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.PaginationUtil;
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

import java.util.List;

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

  //分页
  @RequestMapping(value = "/product", method = RequestMethod.GET)
  public ModelAndView findPageProduct(
      @RequestParam(value = "page", required = false) Integer offset,
      @RequestParam(value = "productType", required = false) Integer productType,
      @RequestParam(required = false) Integer state,
      Model model) {
    //拼接查询条件
    ProductCriteria productCriteria = new ProductCriteria();
    productCriteria.setType(1);
    if (offset == null) {
      offset = 1;
    }
    if (state == null) {
      state = 1;
    }
    List<ProductType> productTypes = productService.findAllProductType();
    productCriteria.setState(state);
    if (productType != null) {
      for (ProductType type : productTypes) {
        if (type.getId() == productType) {
          productCriteria.setProductType(type);
        }
      }
    }

    Page
        page =
        productService.findProductsByPage(offset, productCriteria);
    model.addAttribute("products", page.getContent());
    model.addAttribute("pages", page.getTotalPages());
    model.addAttribute("productCriteria", productCriteria);
    model.addAttribute("currentPage", offset);
    model.addAttribute("productTypes",productTypes );
    return MvUtil.go("/product/productList");
  }

  @RequestMapping(value = "/product/create")
  public ModelAndView goCreateProductPage(Model model) {
    model.addAttribute("sid", productService.getTotalCount());
    model.addAttribute("productTypes", productService.findAllProductType());
    return MvUtil.go("/product/productCreate");
  }

  @RequestMapping(value = "/product", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult createProduct(@RequestBody ProductDto productDto) {
    try {
      productService.editProduct(productDto);
      return new LejiaResult().ok();
    } catch (Exception e) {
      return new LejiaResult(500, "创建商品失败", null);
    }
  }

  @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult deleteProduct(@PathVariable Long id) {
    try {
      productService.deleteProduct(id);
      return new LejiaResult().ok();
    } catch (Exception e) {
      return new LejiaResult(500, "删除商品失败", null);
    }
  }

  @RequestMapping(value = "/product/edit", method = RequestMethod.GET)
  public ModelAndView goEditProductPage(@RequestParam Long id, Model model) {
    Product product = productService.findOneProduct(id);
    model.addAttribute("product", product);
    model.addAttribute("productTypes", productService.findAllProductType());
    model.addAttribute("productSpecs", productSpecService.findProductSpecsByProduct(product));
    return MvUtil.go("/product/productCreate");
  }

  @RequestMapping(value = "/product", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public LejiaResult editProduct(@RequestBody ProductDto productDto) {
    try {
      productService.editProduct(productDto);
      return new LejiaResult().ok();
    } catch (Exception e) {
      e.printStackTrace();
      return new LejiaResult(500, "修改商品失败", null);
    }
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

  @RequestMapping(value = "/product/pictureManage", method = RequestMethod.GET)
  public ModelAndView goPictureManagePage(@RequestParam Long id, Model model) {
    Product product = productService.findOneProduct(id);
    model.addAttribute("product", product);
    model.addAttribute("productDetails", productService.findAllProductDetailsByProduct(product));
    model.addAttribute("scrollPictures", scrollPictureService.findAllScorllPicture(product));
    model.addAttribute("productSpecs",productSpecService.findProductSpecsByProduct(product));
    return MvUtil.go("/product/pictureManage");
  }

  @RequestMapping(value = "/product/qrCode", method = RequestMethod.GET)
  public @ResponseBody LejiaResult productQrCode(@RequestParam Long id){
      return LejiaResult.build(200,productService.qrCodeManage(id));
  }


}
