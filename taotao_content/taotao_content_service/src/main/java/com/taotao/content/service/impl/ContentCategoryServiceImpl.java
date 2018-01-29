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

    public TaotaoResult deleteContentCateogy(Long id){
        //获取父节点
        TbContentCategory category = mapper.selectByPrimaryKey(id);
        Long curentParentId = category.getParentId();
        // 获取所有需要删除的节点id
        List<Long>ids = new ArrayList<>();
        // 把自己放进入
        ids.add(id);
        // 使用递归的方式获取节点的所有子节点
        this.getIds(ids, id);

        // 删除节点
        this.deleteByIds(ids);
        // 判断是否存在兄弟节点，如果没有兄弟节点，修改父节点的isParent为false
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(curentParentId);
        List<TbContentCategory> afterdeleteContentcateogy = mapper.selectByExample(example);

        //如果能够查询到说明 删除了当前的节点及子节点 之后还有节点意味着还有兄弟
        if (afterdeleteContentcateogy!=null && afterdeleteContentcateogy.size()>0) {
            //不用管
        }else{
            //否则 就是删除了唯一的一个节点 需要修改被删除节点的父为叶子节点
            TbContentCategory parentCategory = mapper.selectByPrimaryKey(curentParentId);
            parentCategory.setIsParent(false);
            mapper.updateByPrimaryKey(parentCategory);
        }
        return TaotaoResult.ok();
    }

    // 递归获取id这个节点的所有的子节点
    private void getIds(List<Long> ids, Long parentId) {
        // 根据parentId查询子节点
      TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        // 查询子节点
        List<TbContentCategory> list = mapper.selectByExample(example);

        if(list!=null) {
            // 判断查询结果，其实就是判断是否有子节点
            for (TbContentCategory contentCategory : list) {
                // 如果进入循环，表示有子节点，把子节点id放到ids中
                ids.add(contentCategory.getId());
                // 递归调用，获取子节点的子节点
                this.getIds(ids, contentCategory.getId());
            }
        }
    }

    private void deleteByIds(List<Long> ids){
        for (Long id : ids) {
            mapper.deleteByPrimaryKey(id);
        }
    }
}
