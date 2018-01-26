package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;
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

    @Override
    public TaotaoResult saveItem(TbItem tbItem, String desc) {
        //注入mapper
        //1.生成商品的唯一的编码
        long itemId = IDUtils.genItemId();
        //2.补全商品的其他的属性
        tbItem.setId(itemId);
        tbItem.setStatus((byte) 1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(tbItem.getCreated());
        //3.插入商品的基本信息表
        tbItemMapper.insertSelective(tbItem);

        //4.插入商品的描述表 构建一个对象
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemDesc(desc);
        itemDesc.setItemId(itemId);
        itemDesc.setCreated(tbItem.getCreated());
        itemDesc.setUpdated(tbItem.getCreated());
        tbItemDescMapper.insertSelective(itemDesc);
        return TaotaoResult.ok();
    }
}
