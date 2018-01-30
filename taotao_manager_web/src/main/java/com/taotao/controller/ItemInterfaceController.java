package com.taotao.controller;
//应该放在另外一个单独的系统做一个接口的开发

import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController//@controller和@responseBody  所有的方法都是返回JSON
public class ItemInterfaceController {

    @Autowired
    private ItemService itemService;

    //增
    //url://http:127.0.0.1/rest/interface/item
    //参数：tbitem 数据
    //method:post
    //返回值：201 created

    @RequestMapping(value = "/rest/interface/item",method = RequestMethod.POST)
    public ResponseEntity<Void> createTbItem(TbItem tbItem,String desc){
        try {
            itemService.saveItem(tbItem,desc);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    //删
    //url:http:127.0.0.1/rest/interface/item/{id}
    //参数：id
    //method:delete
    //返回值：responseeneity  204

    @RequestMapping(value="/rest/interface/item/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteById(@PathVariable  Long id){
        try {
            itemService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //改
    //url:http:127.0.0.1/rest/interface/item
    //参数：tbitem ,desc
    //method:put
    //返回值：responseeneity  204

    @RequestMapping(value="/rest/interface/item",method = RequestMethod.PUT)
    public ResponseEntity<Void> updateTbItem(TbItem tbItem,String desc){
        try {
            itemService.updateById(tbItem,desc);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }




    //查
    //url:http:127.0.0.1/rest/interface/item/{id}
    //参数：id
    //method:get
    //返回值：商品的信息的json
    @RequestMapping(value="/rest/interface/item/{id}",method = RequestMethod.GET)
    public ResponseEntity<TbItem> getTbItemById(@PathVariable Long id){
        TbItem item = null;
        try {
            item = itemService.getItemById(id);
            //成功返回头：200 响应体：body--> item
           //  ResponseEntity.ok(item);
            return ResponseEntity.status(HttpStatus.OK).body(item);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



}
