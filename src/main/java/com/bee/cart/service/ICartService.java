package com.bee.cart.service;

import com.bee.cart.vo.CartVo;

public interface ICartService {
	
	CartVo add(Integer userId,Integer count,Integer productId);
	
	CartVo update(Integer userId,Integer count,Integer productId);
    
	CartVo delete(Integer userId,String productIds);
	
	CartVo list(Integer userId);
	
	CartVo selectOrUnSelect(Integer userId,Integer productId,Integer checked);
    
	Integer getCartProductCount(Integer userId);
}
