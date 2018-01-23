package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String showIndex(){
        return "index";
    }
    //展示商品列表的tab页面
    //url:/item-list--->页面就是item-list.jsp
    //url:item-add ---->页面就是item-add.jsp
    @RequestMapping("/{page}")
    public String showItemLisPage(@PathVariable(value="page")  String page){
        return page;
    }

}
