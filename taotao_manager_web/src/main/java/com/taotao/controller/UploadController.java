package com.taotao.controller;

import com.taotao.util.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UploadController {

    @Value("${TAOTAO_IMAGE_URL}")
    private String TAOTAO_IMAGE_URL;


    //当返回的数据的content-type如果是application/json的时候，google是可以的，但是火狐不支持，它支持的是content-type=text/plain;
    //url:/pic/upload
    //参数：multipartfile
    @RequestMapping(value="/pic/upload",produces = MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
    @ResponseBody
    public Map<String, Object> upload(MultipartFile uploadFile) {
        //1上传文件处理 1，加入file-upload.jar 2.配置文件解析器

        // 2调用fastdfs提供的客户端 把流写入到对应的storage中
        //CTR + ALT + T
        try {
            //3.
            FastDFSClient client = new FastDFSClient("classpath:resource/fastdfs.conf");

            //4.上传图片
            //第一个参数：就是字节数组
            //第二个参数：图片的扩展名 不带点

            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);//不带的点
            //group1/M00/00/01/wKgZhVpn-3GARhORAAvqH_kipG8413.jpg
            String imagePath = client.uploadFile(uploadFile.getBytes(), extName);
            //拼接URL 将完整的URL 存入到数据里面
            String complPath = TAOTAO_IMAGE_URL + imagePath;
            //返回一个JSON给页面
//            {
//                "error" : 0,
//                    "url" : "http://www.example.com/path/to/file.ext"
//            }
            Map<String, Object> map = new HashMap<>();
            map.put("error", 0);
            map.put("url", complPath);
            return map;

        } catch (Exception e) {
            //失败时
//            {
//                "error" : 1,
//                    "message" : "错误信息"
//            }

            e.printStackTrace();
            Map<String, Object> map = new HashMap<>();
            map.put("error", 1);
            map.put("message", "上传失败");
            return map;
        }
    }
}
