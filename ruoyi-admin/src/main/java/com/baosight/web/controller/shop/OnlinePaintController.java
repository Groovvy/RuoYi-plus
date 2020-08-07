package com.baosight.web.controller.shop;

import com.baosight.shop.domain.TemplateVo;
import com.baosight.shop.service.IOnlinePaintService;
import com.ruoyi.common.core.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wanghuaan
 * @date 2020/6/5
 **/
@Controller
@RequestMapping("/onlinePaint")
public class OnlinePaintController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(OnlinePaintController.class);

    @Autowired
    private IOnlinePaintService onlinePaintService;

    @GetMapping("/index")
    public String index(){
        return "shop/onlinePaint/index";
    }

    @GetMapping("/test")
    @ResponseBody
    public TemplateVo test(){
        return onlinePaintService.selectTemplateByCode("MB000042");
    }
}
