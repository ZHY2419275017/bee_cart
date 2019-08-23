package com.bee.cart.mapper;

import com.bee.cart.pojo.MallCategory;
import java.util.List;

public interface MallCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MallCategory record);

    MallCategory selectByPrimaryKey(Integer id);

    List<MallCategory> selectAll();

    int updateByPrimaryKey(MallCategory record);
}