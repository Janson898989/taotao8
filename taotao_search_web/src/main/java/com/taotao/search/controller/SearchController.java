package com.taotao.search.controller;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {
    private static final Integer ROWS=60;


    @Autowired
    private SearchService searchService;
    //常量类  Contants
    //url：http://localhost:8085/search.html
    //参数：就是q  和page
    //返回值：String 页面 传递搜索到的数据到页面中

    @RequestMapping("/search")
    public String search(@RequestParam(value="q") String queryString, Integer page, Model model) throws Exception{
        //1.引入服务 注入服务  调用

       if(StringUtils.isNotBlank(queryString)){
           queryString = new String(queryString.getBytes("iso-8859-1"),"utf-8");
       }
        SearchResult result = searchService.search(queryString, page, ROWS);
        //2.将数据传递到页面中
        model.addAttribute("query",queryString);
        model.addAttribute("totalPages",result.getPageCount());
        model.addAttribute("itemList",result.getItemList());
       // model.addAttribute("page",result.getPage());//刚才的问题就是需要添加page页的回显。
        return "search";
    }
}
