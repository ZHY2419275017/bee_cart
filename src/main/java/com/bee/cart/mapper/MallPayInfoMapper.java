package com.bee.cart.mapper;

import com.bee.cart.pojo.MallPayInfo;
import java.util.List;

public interface MallPayInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MallPayInfo record);

    MallPayInfo selectByPrimaryKey(Integer id);

    List<MallPayInfo> selectAll();

    int updateByPrimaryKey(MallPayInfo record);
}