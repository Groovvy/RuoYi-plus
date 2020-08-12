package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wanghuaan
 * @date 2020/6/5
 **/
@Controller
@RequestMapping("/onlinePaint")
public class OnlinePaintController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(OnlinePaintController.class);

    @GetMapping("/index")
    public String index(){
        return "onlinePaint/index";
    }

//    @GetMapping("/test")
//    @ResponseBody
//    public TemplateVo test(){
//        return onlinePaintService.selectTemplateByCode("MB000042");
//    }
}
