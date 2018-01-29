package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;

public interface SearchService {
    //从数据库中查询所有的商品数据 然后使用solrj 将数据导入到索引库中
    public TaotaoResult importAllToIndex() throws Exception;

    //根据表现层传递过来的查询的条件查询  分页的查询
    public SearchResult search(String queryString,Integer page,Integer rows) throws Exception;

}
