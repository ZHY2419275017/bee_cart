package com.bee.cart.mapper;

import com.bee.cart.pojo.MallUser;
import java.util.List;

public interface MallUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MallUser record);

    MallUser selectByPrimaryKey(Integer id);

    List<MallUser> selectAll();

    int updateByPrimaryKey(MallUser record);
}