package com.taotao.common.pojo;

import java.io.Serializable;

/**
 *
 * 树节点的POJO
 */
public class TreeNode  implements Serializable{
    private Long id;//树控件的节点的id的值
    private String text;//显示节点文本
    private String state;//状态  closed  open

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
