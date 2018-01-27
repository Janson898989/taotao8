package com.taotao.portal.controller;

import com.taotao.common.util.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.Ad1Node;
import org.apache.http.ContentTooLongException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页相关的controller
 */
@Controller//是@controller+@responseBody
public class IndexController {

    @Autowired
    private ContentService contentService;
    //展示门户首页
    @RequestMapping("/index")
    public String showIndex(Model model){

        //url:/index
        //参数：categoryId----》硬编码
        //返回值：json ----》传递到页面---->
        //1.引入服务 注入   调用
        List<TbContent> contents = contentService.getContentListByCategoryId(89l);



        //转成需要在页面显示POJO的列表对象
        List<Ad1Node> nodes = new ArrayList<>();

        for (TbContent content : contents) {
            Ad1Node node = new Ad1Node();
            node.setAlt(content.getSubTitle());
            node.setHeight("240");
            node.setHeightB("240");
            node.setHref(content.getUrl());
            node.setSrc(content.getPic());
            node.setSrcB(content.getPic2());
            node.setWidth("670");
            node.setWidthB("550");
            nodes.add(node);
        }

        //转成JSON  传递给页面

        model.addAttribute("nodespics",JsonUtils.objectToJson(nodes));
        return "index";
    }
}
