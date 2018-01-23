package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Override
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        //分页查询 pagehelper
        //1.设置分页
        PageHelper.startPage(page,rows);//仅跟着的第一个查询才会被分页
        //查询所有
        List<TbItem> tbItems = tbItemMapper.selectByExample(null);

        //构建分页的对象 里面包括了总记录数
        PageInfo<TbItem> info = new PageInfo<TbItem>(tbItems);
        long total = info.getTotal();
        //创建EasyUIDataGridResult 对象  封装属性（total rows）
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(total);
        result.setRows(info.getList());
        return result;
    }
}
