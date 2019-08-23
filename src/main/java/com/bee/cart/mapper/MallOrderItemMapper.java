package com.bee.cart.mapper;

import com.bee.cart.pojo.MallOrderItem;
import java.util.List;

public interface MallOrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MallOrderItem record);

    MallOrderItem selectByPrimaryKey(Integer id);

    List<MallOrderItem> selectAll();

    int updateByPrimaryKey(MallOrderItem record);
}