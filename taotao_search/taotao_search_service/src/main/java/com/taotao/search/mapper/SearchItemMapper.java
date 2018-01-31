package com.taotao.search.mapper;

import com.taotao.common.pojo.SearchItem;

import java.util.List;

/**
 * 搜索时所对应的查询到的商品的数据的mapper
 */
public interface SearchItemMapper {
        public List<SearchItem> getSearchItemList();

        public SearchItem getSearchItemById(Long id);
}
