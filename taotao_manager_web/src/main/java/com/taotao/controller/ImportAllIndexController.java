package com.taotao.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImportAllIndexController {


    @Autowired
    private SearchService searchService;

    @RequestMapping("/importAll")
    @ResponseBody
    public TaotaoResult importAll() throws Exception{
        //1.引入服务
        //2.注入服务
        //3.调用
        return searchService.importAllToIndex();
    }
}
