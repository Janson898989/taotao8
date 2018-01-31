package com.taotao.item.pojo;

import com.taotao.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

public class Item extends TbItem{

    public Item(TbItem item){

        //tbitem.setId()----->item.setId()
        //注意：只有相同的类型和相同的方法的时候，才会调用
        BeanUtils.copyProperties(item,this);
    }

    public String[] getImages(){
        if(StringUtils.isNotBlank(super.getImage())){
            return super.getImage().split(",");
        }
        return null;
    }
}
