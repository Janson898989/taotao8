package com.taotao.search.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class TestSolrj {

    @Test
    public void solrjquery() throws Exception{
        //1.创建连接对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.154:8080/solr");
        //.2创建查询的对象
        SolrQuery query = new SolrQuery("*:*");
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");




        //3.执行查询
        QueryResponse response = solrServer.query(query);



        //4.获取结果集
        SolrDocumentList results = response.getResults();


        long numFound = results.getNumFound();
        System.out.println(numFound);

        //获取高亮
        //第一个key:就是文档的id的值
        //第二个key;就是高亮显示的域的名称
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        for (SolrDocument result : results) {
            System.out.println(result.get("id"));
            List<String> list = highlighting.get(result.get("id")).get("item_title");
            if(list!=null && list.size()>0){
                //有高亮
                System.out.println(list.get(0));
            }else{
                System.out.println(result.get("item_title"));
            }
            System.out.println(result.get("item_price"));
        }

    }



    //测试集群

    @Test
    public void testSolrCloud() throws Exception{
        //创建cloudsolrserver连接对象
        CloudSolrServer solrServer = new CloudSolrServer("192.168.25.154:2181,192.168.25.154:2182,192.168.25.154:2183");
        //第二个：设置默认的collection集合名字
        solrServer.setDefaultCollection("collection2");

        //3.添加文档
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id","test01");
        document.addField("item_title","solr集群的测试");

        //4.添加到索引库
        solrServer.add(document);

        solrServer.commit();



    }
}
