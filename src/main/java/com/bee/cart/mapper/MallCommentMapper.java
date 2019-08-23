package com.bee.cart.mapper;

import com.bee.cart.pojo.MallComment;
import java.util.List;

public interface MallCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MallComment record);

    MallComment selectByPrimaryKey(Integer id);

    List<MallComment> selectAll();

    int updateByPrimaryKey(MallComment record);
}