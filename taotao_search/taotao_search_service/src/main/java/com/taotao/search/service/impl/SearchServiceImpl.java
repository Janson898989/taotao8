package com.taotao.search.service.impl;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchItemMapper mapper;

    @Autowired
    private SearchDao dao;

    @Autowired
    private SolrServer solrServer;
    @Override
    public TaotaoResult importAllToIndex()  throws Exception{
        //1.调用mapper查询所有的数据
        List<SearchItem> searchItemList = mapper.getSearchItemList();

        //2.将这些数据放入到索引库中  使用solrj
        //2.1 创建连接对象solrserver 对象  由spring管理 注入进来即可
        //SolrServer solrServer = new HttpSolrServer("http://192.168.25.154:8080/solr/collection1");

        //2.2 创建一个文档---->一条记录

        List<SolrInputDocument> documents = new ArrayList<>();
        for (SearchItem searchItem : searchItemList) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id",searchItem.getId().toString());
            document.addField("item_title",searchItem.getTitle());
            document.addField("item_sell_point",searchItem.getSell_point());
            document.addField("item_price",searchItem.getPrice());
            document.addField("item_image",searchItem.getImage());
            document.addField("item_category_name",searchItem.getCategory_name());
            document.addField("item_desc",searchItem.getItem_desc());
            //2.3 向文档中添加域
            documents.add(document);
        }
        //2.4 添加文档到索引中
        solrServer.add(documents);
        //2.5 commit
        solrServer.commit();
        return TaotaoResult.ok();
    }

    @Override
    public SearchResult search(String queryString, Integer page, Integer rows)  throws Exception{
        //1.创建查询对象设置各种查询的条件
        SolrQuery query = new SolrQuery();
        if(StringUtils.isNotBlank(queryString)) {
            query.setQuery(queryString);
        }else{
            query.setQuery("*:*");
        }
        //设置默认的搜索域
        query.set("df","item_keywords");

        //分页条件的设置
        if(page==null)page=1;
        if(rows==null)rows=60;
        query.setStart((page-1)*rows);
        query.setRows(rows);

        //高亮 开启高亮 设置高亮显示的域  设置前缀和后缀

        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");


        //3注入dao 调用方法  得到就只是 总记录数 和商品的列表
        SearchResult search = dao.search(query);

        //设置分页的数据

        search.setPage(page);
        search.setRows(rows);
        long pagecount = search.getRecordCount()/rows;
        if(search.getRecordCount()%rows>0){
            pagecount++;
        }
        search.setPageCount(pagecount);
        return search;
    }
}
