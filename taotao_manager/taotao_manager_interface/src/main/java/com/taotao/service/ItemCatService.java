package com.taotao.service;

import com.taotao.common.pojo.TreeNode;

import java.util.List;

public interface ItemCatService {
    /**
     * 根据父节点的值获取子节点的列表
     * @param parentId
     * @return
     */
    public List<TreeNode> getItemCatListByParentId(Long parentId);
}
