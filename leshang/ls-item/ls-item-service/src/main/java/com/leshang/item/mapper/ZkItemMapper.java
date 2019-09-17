package com.leshang.item.mapper;

import com.leshang.item.pojo.ZkItem;
import com.leshang.common.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import com.leshang.common.mapper.BaseMapper;

public interface ZkItemMapper  extends BaseMapper<ZkItem> {

    @Select("SELECT * FROM zk_item ORDER BY price ASC")
     List<ZkItem> queryItemsByPriceASC();

    @Select("SELECT * FROM zk_item ORDER BY price DESC")
    List<ZkItem> queryItemsByPriceDESC();
}