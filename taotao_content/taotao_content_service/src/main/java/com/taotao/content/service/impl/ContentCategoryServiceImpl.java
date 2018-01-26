package com.taotao.content.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.pojo.TreeNode;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper mapper;

    @Override
    public List<TreeNode> getContentCategoryListByParentId(Long parentId) {
        //1.创建example 设置查询的条件
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        //2.执行查询   list<tbcontentcategoy>
        List<TbContentCategory> categories = mapper.selectByExample(example);
        List<TreeNode> nodes = new ArrayList<>();
        for (TbContentCategory category : categories) {
            TreeNode node = new TreeNode();
            node.setId(category.getId());//节点的id
            node.setText(category.getName());
            node.setState(category.getIsParent()?"closed":"open");
            nodes.add(node);
        }
        //3.转成List<treenode
        return nodes;
    }

    @Override
    public TaotaoResult saveContentCategory(TbContentCategory contentCategory) {
        //1.补全属性
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(contentCategory.getCreated());
        contentCategory.setIsParent(false);//叶子
        contentCategory.setSortOrder(1);
        contentCategory.setStatus(1);


        //如果添加的节点其父节点原来就是一个叶子节点就需要更新成父节点
        TbContentCategory newNodeParentcategory = mapper.selectByPrimaryKey(contentCategory.getParentId());

        if(!newNodeParentcategory.getIsParent()){
            newNodeParentcategory.setIsParent(true);
            mapper.updateByPrimaryKey(newNodeParentcategory);
        }


        //2.插入数据
        mapper.insertSelective(contentCategory);
        //3.返回taotaoresult ---》date属性指定 contentCategory对象
        //contentCategory 该对象就有id的值
        return TaotaoResult.ok(contentCategory);
    }
}
