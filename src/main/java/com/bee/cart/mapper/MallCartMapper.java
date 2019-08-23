package com.bee.cart.mapper;

import com.bee.cart.pojo.MallCart;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface MallCartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MallCart record);

    MallCart selectByPrimaryKey(Integer id);

    List<MallCart> selectAll();

    int updateByPrimaryKey(MallCart record);
    
    int updateByPrimaryKeySelective(MallCart record);
    
    MallCart selectCartByUserIdProductId(@Param("userId") Integer userId,@Param("productId") Integer productId);
    
    List<MallCart> selectCartByUserId(Integer userId);
    
    int selectCartProductCheckedStatusByUserId(Integer userId);
    
    List<MallCart> selectCartByProductId(Integer productId);
    
    int deleteByUserIdProductIds(@Param("userId") Integer userId,@Param("productIds") List<String> productIdList);
    
    int checkedOrUnchecked(@Param("userId") Integer userId,@Param("productId") Integer productId,@Param("checked") Integer checked);
   
    int selectCartProductCount(Integer userId);
    
}