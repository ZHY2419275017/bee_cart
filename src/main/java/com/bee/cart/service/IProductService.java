package com.bee.cart.service;

import com.bee.cart.pojo.MallProduct;

public interface IProductService {
	
	MallProduct selectProductByProductId(Integer productId);

}
