package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页相关的controller
 */
@Controller//是@controller+@responseBody
public class IndexController {
    //展示门户首页
    @RequestMapping("/index")
    public String showIndex(){
        return "index";
    }
}
