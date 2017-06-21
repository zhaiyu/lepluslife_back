package com.jifenke.lepluslive.groupon.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.groupon.controller.dto.GrouponProductDto;
import com.jifenke.lepluslive.groupon.domain.criteria.GrouponProductCriteria;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponProduct;
import com.jifenke.lepluslive.groupon.service.GrouponProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  团购产品 Controller
 * Created by xf on 17-6-16.
 */
@Controller
@RequestMapping("/manage")
public class GrouponProductController {

    @Inject
    private GrouponProductService grouponProductService;

    /**
     *  跳转到列表页面
     *  Created by xf on 2017-06-21.
     */
    @RequestMapping("/grouponProduct/list")
    public ModelAndView toListPage() {
        return MvUtil.go("/groupon/productList");
    }
    /**
     *  跳转到添加页面
     *  Created by xf on 2017-06-21.
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
        if(productCriteria.getOffset()==null) {
            productCriteria.setOffset(1);
        }
        Page<GrouponProduct> page = grouponProductService.findByCriteria(productCriteria,10);
        List<GrouponProduct> products = page.getContent();
        Map<String,Object> data = new HashMap<>();
        data.put("data",page);
        if(products!=null&&products.size()>0) {
            List<Long> merchants = grouponProductService.countMerchantByProducts(products);
            data.put("bindMerchants",merchants);
        }
        return LejiaResult.ok(data);
    }

    /**
     * 新建产品
     * Created by xf on 2017-06-20.
     */
    @RequestMapping("/grouponProduct/save")
    @ResponseBody
    public LejiaResult saveProduct(@RequestBody GrouponProductDto grouponProductDto) {
        boolean result = grouponProductService.saveProduct(grouponProductDto);
        if(result) {
            return LejiaResult.ok();
        }else {
            return LejiaResult.build(500,"保存失败，请联系系统管理员！");
        }
    }
}
