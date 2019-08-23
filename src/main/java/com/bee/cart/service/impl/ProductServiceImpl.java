package com.bee.cart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bee.cart.mapper.MallProductMapper;
import com.bee.cart.pojo.MallProduct;
import com.bee.cart.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService{
	
	@Autowired
	private MallProductMapper productMapper;
	
	@Override
	public MallProduct selectProductByProductId(Integer productId) {
	  return productMapper.selectByPrimaryKey(productId);		
	}

}
