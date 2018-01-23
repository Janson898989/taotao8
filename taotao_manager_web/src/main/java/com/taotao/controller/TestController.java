package com.taotao.controller;

import com.taotao.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/getNow")
    @ResponseBody//一定要开启注解驱动并且引入jackson的依赖
    public String getNow(){
        //1.在springmvc.xml中引入服务
        //2.注入服务（会自动创建一个代理对象调用接口的实现类）
        //3.调用服务
        String now = testService.getNow();
        return now;
    }
}
