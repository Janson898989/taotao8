package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
    /**
     * 分页查询商品的列表
     * @param page
     * @param rows
     * @return
     */
    public EasyUIDataGridResult getItemList(Integer page,Integer rows);

    public TaotaoResult saveItem(TbItem tbItem,String desc);

    public TbItem getItemById(Long id);

    public void deleteById(Long id);

    //参数里面更新后的数据必须有id的值
    public void updateById(TbItem item,String desc);
}
