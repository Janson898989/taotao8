package com.taotao.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.pojo.TreeNode;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.pojo.TbContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentCateogyController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<TreeNode> getContentCategoryByParentId(@RequestParam(value="id",defaultValue = "0") Long parentId){
        //1.引入服务
        //2.注入服务
        //3.调用
        return contentCategoryService.getContentCategoryListByParentId(parentId);
    }

    //url;/content/category/create
    //参数：parentId     name
    //返回值：json  里面要求有id的值  data.data.id
    @RequestMapping("/content/category/create")
    @ResponseBody
    public TaotaoResult saveContentCategory(TbContentCategory contentCategory){
       return  contentCategoryService.saveContentCategory(contentCategory);
    }

    //删除
    @RequestMapping("/content/category/delete")
    @ResponseBody
    public TaotaoResult delete(Long id){
        return contentCategoryService.deleteContentCateogy(id);
    }
}
