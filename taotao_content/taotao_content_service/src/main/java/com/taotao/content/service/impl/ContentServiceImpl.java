package com.taotao.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.content.service.jedis.JedisClient;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper tbContentMapper;
    @Override
    public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
        //1.设置分页的条件        仅跟着的第一个查询才会被分页
        PageHelper.startPage(page,rows);
        //2.创建查询对象example 设置查询的条件

        TbContentExample example  = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        //3,执行查询
        List<TbContent> contents = tbContentMapper.selectByExample(example);

        //4.创建分页的信息获取总记录数
        PageInfo<TbContent> info = new PageInfo<TbContent>(contents);
        long total = info.getTotal();
        //5.创建EasyUIDataGridResult  封装
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(total);
        result.setRows(contents);
        return result;
    }

    @Override
    public TaotaoResult saveContent(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContent.setUpdated(tbContent.getCreated());
        tbContentMapper.insertSelective(tbContent);
        return TaotaoResult.ok();
    }


    @Autowired
    private JedisClient jedisClient;

    //获取轮播图的业务 首页需要进行缓存
    @Override
    public List<TbContent> getContentListByCategoryId(Long categoryId) {
        //使用缓存 不能影响正常的业务逻辑
        // key value  使用hash类型


        //1.判断如果redis中有缓存数据  直接返回
        try {
            String stringjson = jedisClient.hget("TB_CONTENT_REDIS_PORTAL", categoryId + "");

            if(StringUtils.isNotBlank(stringjson)){//不为空
                System.out.println("找到缓存啦！！！！！");
               return  JsonUtils.jsonToList(stringjson,TbContent.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //2.如果没有数据在redis中 执行以下的sql 去数据库取出来
        TbContentExample example  = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);

        List<TbContent> contents = tbContentMapper.selectByExample(example);


        //3.取出数据写入redis数据库中 返回
        try {
            jedisClient.hset("TB_CONTENT_REDIS_PORTAL",categoryId+"", JsonUtils.objectToJson(contents));
            System.out.println("第一次从数据库找数据写入redis");
        } catch (Exception e) {
            e.printStackTrace();
        }


        return contents;
    }
}
