package com.bee.cart.mapper;

import com.bee.cart.pojo.MallProduct;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface MallProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MallProduct record);

    MallProduct selectByPrimaryKey(Integer id);

    List<MallProduct> selectAll();

    int updateByPrimaryKey(MallProduct record);
}