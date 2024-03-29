package com.bee.cart.mapper;

import com.bee.cart.pojo.MallOrder;
import java.util.List;

public interface MallOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MallOrder record);

    MallOrder selectByPrimaryKey(Integer id);

    List<MallOrder> selectAll();

    int updateByPrimaryKey(MallOrder record);
}