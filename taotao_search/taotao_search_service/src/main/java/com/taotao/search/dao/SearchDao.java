package com.taotao.search.dao;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import org.apache.ibatis.annotations.ResultType;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDao {

    //根据查询的条件执行查询 返回结果集（包括分页的数据，和商品的列表）


    @Autowired
    private SolrServer solrServer;

    public SearchResult search(SolrQuery query) throws Exception{
        //1.创建solrserver对象

        //2.根据query的对象的条件 执行查询
        QueryResponse response = solrServer.query(query);
        //3.获取到结果集  solrdocument ----》转成SearchItem--->封装到searchresult中
        SolrDocumentList results = response.getResults();
        long numFound = results.getNumFound();
        List<SearchItem> products = new ArrayList<>();
        //获取高亮
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        for (SolrDocument result : results) {
            SearchItem searchItem = new SearchItem();
            searchItem.setCategory_name(result.get("item_category_name").toString());//商品类别
            searchItem.setId(Long.valueOf((String)result.get("id")));//域中的值是String  pojo中的是long 需要转换
            searchItem.setImage(result.get("item_image").toString());
//            searchItem.setItem_desc(result.get("item_desc").toString()); 空的数据
            searchItem.setPrice((Long) result.get("item_price"));
            searchItem.setSell_point(result.get("item_sell_point").toString());

            List<String> list = highlighting.get(result.get("id")).get("item_title");
            String item_title = "";
            if(list!=null && list.size()>0){
                //有高亮
                item_title=list.get(0);
            }else{
                item_title=result.get("item_title").toString();
            }
            searchItem.setTitle(item_title);
            products.add(searchItem);
        }
        //4.设置总记录数  设置 商品的列表

        SearchResult searchResult = new SearchResult();
        searchResult.setRecordCount(numFound);
        searchResult.setItemList(products);
        return searchResult;
    }


}
