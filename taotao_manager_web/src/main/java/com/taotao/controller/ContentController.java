package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentList(Long categoryId,Integer page,Integer rows){
        //1.引入服务  注入  调用
        return  contentService.getContentList(categoryId,page,rows);
    }

    //如果是4.3.10以上的就可以使用一个注解 @postMapping()  @getMapping
    @RequestMapping(value="/content/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult saveConent(TbContent content){
        return contentService.saveContent(content);
    }

}
