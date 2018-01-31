package com.taotao.search.listener;

import com.taotao.common.pojo.SearchItem;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ItemChangeListener  implements MessageListener{

    @Autowired
    private SearchItemMapper mapper;

    @Autowired
    private SearchService searchService;

    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage) {
            try {

                //获取消息  内容 是 商品的id
                String itemIdStr= ((TextMessage) message).getText();
                //转成long
                Long itemId = Long.valueOf(itemIdStr);

                //调用数据库查询商品的方法  获取到商品的数据
                SearchItem item = mapper.getSearchItemById(itemId);
                //将获取到的商品的数据更新到索引库中  定义一个接口定义实现类 定义一个方法 ：作用：更新索引库
                searchService.updateIndex(item);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
