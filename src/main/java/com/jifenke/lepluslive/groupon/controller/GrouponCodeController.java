package com.jifenke.lepluslive.groupon.controller;

import com.jifenke.lepluslive.global.util.LejiaResult;
import com.jifenke.lepluslive.groupon.domain.criteria.GrouponCodeCriteria;
import com.jifenke.lepluslive.groupon.domain.criteria.GrouponOrderCriteria;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponCode;
import com.jifenke.lepluslive.groupon.domain.entities.GrouponOrder;
import com.jifenke.lepluslive.groupon.service.GrouponCodeService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    /**
     * 分页展示
     * Created by xf on 2017-06-19.
     */
    @RequestMapping("/grouponCode/findByCriteria")
    public LejiaResult findByCriteria(GrouponCodeCriteria codeCriteria) {
        if(codeCriteria.getOffset()==null) {
            codeCriteria.setOffset(1);
        }
        Page<GrouponCode> page = grouponCodeService.findByCriteria(codeCriteria,10);
        return LejiaResult.ok(page);
    }
}
