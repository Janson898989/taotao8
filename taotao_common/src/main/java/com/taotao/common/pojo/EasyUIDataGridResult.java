package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

//如果要在不同的系统之前进行传递对象 必须要实现序列化
public class EasyUIDataGridResult implements Serializable{
    private Long total;//总记录数
    private List rows;//对象的集合 可以使商品列表

    //ALT + INSERT
    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
