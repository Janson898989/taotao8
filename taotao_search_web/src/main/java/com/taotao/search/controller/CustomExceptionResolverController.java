package com.taotao.search.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理器的类
 */
@ControllerAdvice//通知   只有要有@requestmapping修饰的方法  都能被捕获到
public class CustomExceptionResolverController {

    //如果获取到异常需要返回一个友好的页面
    @ExceptionHandler(value=Exception.class)
    public String showError(Model model){
        //打印日志 输日志到文件中
        model.addAttribute("message","您的网络有异常");
        return "error/exception";
    }

}
