package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;


   /* url:/item/list
    参数：page:当前的页码 rows:每页显示多少行
    get方法
    返回值：json    {total：，rows:[]}*/
    @RequestMapping(value="/item/list",method = RequestMethod.GET)
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page,Integer rows){
        //1.引入服务

        //2.注入服务
        //3.调用服务
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

    //插入商品数据包括（商品的基本信息和描述信息）
    //url:/item/save
    //参数：表单
    //返回值：json  taotaoresult
    @RequestMapping(value="/item/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult saveItem(String desc, TbItem tbItem){
        //引入服务
        //注入服务
        //调用服务
        return itemService.saveItem(tbItem,desc);
    }
}
