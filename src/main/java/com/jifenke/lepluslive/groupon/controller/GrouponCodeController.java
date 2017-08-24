package com.jifenke.lepluslive.groupon.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.global.util.MvUtil;
import com.jifenke.lepluslive.groupon.controller.view.GrouponCodeExcel;
import com.jifenke.lepluslive.groupon.domain.criteria.GrouponCodeCriteria;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponCode;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponOrder;
import com.jifenke.lepluslive.groupon.service.GrouponCodeService;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;


/**
 *  团购券码 Controller
 * @author XF
 * @date 2017/6/19
 */

@Controller
@RequestMapping("/manage")
public class GrouponCodeController {

    @Inject
    private GrouponCodeService grouponCodeService;
    @Inject
    private GrouponCodeExcel grouponCodeExcel;

    /**
     *  跳转到列表页面
     *  Created by xf on 2017-06-21.
     */
    @RequestMapping("/grouponCode/list")
    public ModelAndView toListPage() {
        return MvUtil.go("/groupon/codeList");
    }

    /**
     * 分页展示
     * Created by xf on 2017-06-19.
     */
    @RequestMapping("/grouponCode/findByCriteria")
    @ResponseBody
    public LejiaResult findByCriteria(@RequestBody GrouponCodeCriteria codeCriteria) {
        if(codeCriteria.getOffset()==null) {
            codeCriteria.setOffset(1);
        }
        Page<GrouponCode> page = grouponCodeService.findByCriteria(codeCriteria,10);
        List<GrouponCode> content = page.getContent();
        List<GrouponOrder> orders = new ArrayList<>();
        for (GrouponCode code : content) {
            orders.add(code.getGrouponOrder());
        }
        Map data = new HashedMap();
        data.put("page",page);
        data.put("orders",orders);
        return LejiaResult.ok(data);
    }

    /**
     *  导出 Excel
     *  Created by xf on 2017-06-19.
     */
    @RequestMapping(value = "/grouponCode/export",method = RequestMethod.POST)
    public ModelAndView export(GrouponCodeCriteria codeCriteria) {
        if(codeCriteria.getOffset()==null) {
            codeCriteria.setOffset(1);
        }
        Page<GrouponCode> page = grouponCodeService.findByCriteria(codeCriteria,10000);
        Map map = new HashMap();
        map.put("codeList", page.getContent());
        return new ModelAndView(grouponCodeExcel, map);
    }
}
