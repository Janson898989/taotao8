package com.taotao.content.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

public interface ContentService {
    /**
     * 根据内容分类的id分页查询其下的所有的内容列表
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    public EasyUIDataGridResult getContentList(Long categoryId,Integer page,Integer rows);

    public TaotaoResult saveContent(TbContent tbContent);


    //根据分类的id 查询分类下的所有的内容列表
    public List<TbContent> getContentListByCategoryId(Long categoryId);
}
