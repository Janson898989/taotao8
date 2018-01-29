package com.taotao.content.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.pojo.TreeNode;
import com.taotao.pojo.TbContentCategory;

import java.util.List;

public interface ContentCategoryService {

    //根据父节点找其子节点列表

    public List<TreeNode> getContentCategoryListByParentId(Long parentId);
    //添加内容分类数据
    public TaotaoResult saveContentCategory(TbContentCategory contentCategory);
    //删除
    public TaotaoResult deleteContentCateogy(Long id);
}
