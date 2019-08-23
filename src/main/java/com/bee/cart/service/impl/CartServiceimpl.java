package com.bee.cart.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.bee.cart.mapper.MallCartMapper;
import com.bee.cart.mapper.MallProductMapper;
import com.bee.cart.pojo.MallCart;
import com.bee.cart.pojo.MallProduct;
import com.bee.cart.service.ICartService;
import com.bee.cart.util.BigDecimalUtil;
import com.bee.cart.vo.CartProductVo;
import com.bee.cart.vo.CartVo;
@Service
public class CartServiceimpl implements ICartService {
	
	@Autowired
	private MallCartMapper cartMapper;
	@Autowired
	private  MallProductMapper productMapper;
	
	private Logger logger = LoggerFactory.getLogger(CartServiceimpl.class);


	@Override
	public CartVo add(Integer userId, Integer count, Integer productId) {
		if(count==null || productId==null){
			return null;
		}
		MallCart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
		if(cart==null){
			//购物车内没有该商品，需要添加该商品
			MallCart cartItem = new MallCart();
			cartItem.setChecked(1);//购物车选中状态
			cartItem.setProductId(productId);
			cartItem.setUserId(userId);
			cartItem.setQuantity(count);
			cartItem.setCreateTime(new Date());
			cartItem.setUpdateTime(new Date());
			cartMapper.insert(cartItem);
		}else{
			//购物车内已经有该商品了，
			count = cart.getQuantity()+count;
			cart.setQuantity(count);
			cartMapper.updateByPrimaryKeySelective(cart);
		}
		return this.list(userId);
	}
	
	
	//组建cartVo对象
	private CartVo getCartVoLimit(Integer userId){
		CartVo cartVo = new CartVo();	
		List<CartProductVo> cartProductVoList = Lists.newArrayList();
		List<MallCart> cartList = cartMapper.selectCartByUserId(userId);
		BigDecimal cartTotalPrice = new BigDecimal("0");
		if(!org.springframework.util.CollectionUtils.isEmpty(cartList)){
			//遍历这个购物车
			for(MallCart cartItem : cartList){
				CartProductVo cartProductVo = new CartProductVo();
				cartProductVo.setId(cartItem.getId());
				cartProductVo.setUserId(cartItem.getUserId());
				cartProductVo.setProductId(cartItem.getProductId());				
				//根据商品id查找商品信息
				MallProduct product = productMapper.selectByPrimaryKey(cartItem.getProductId());
				if(!StringUtils.isEmpty(product)){
					cartProductVo.setProductMainImage(product.getMainImage());
					cartProductVo.setProductName(product.getName());
					cartProductVo.setProductSubtitle(product.getSubtitle());
					cartProductVo.setProductStatus(product.getStatus());
					cartProductVo.setProductStock(product.getStock());
					cartProductVo.setProductPrice(product.getPrice());
					//限制库存
					int buyLimitCount = 0;
					if(product.getStock()>=cartItem.getQuantity()){
						cartProductVo.setLimitQuantity("success");
						//库存充足，不用修改购买数量
						buyLimitCount=cartItem.getQuantity();
					}else{
						buyLimitCount=product.getStock();
						cartProductVo.setLimitQuantity("error");
						//此时购物车的数量 大于库存的数量，把购物车内该商品的数量强制转换为库存的数量
						MallCart cart = new MallCart();
						cart.setId(cartItem.getId());
						cart.setQuantity(buyLimitCount);
						cartMapper.updateByPrimaryKeySelective(cart);
					}
					cartProductVo.setQuantity(buyLimitCount); 
					//计算总价
					cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVo.getQuantity().doubleValue()));
					cartProductVo.setProductChecked(cartItem.getChecked());
				}
				
				if(cartItem.getChecked()==1){
					//加入到购物车总价中
					cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
				}
				cartProductVoList.add(cartProductVo);
				
			}
			//构造cartVo对象
			cartVo.setCartTotalPrice(cartTotalPrice);
			cartVo.setCartProductVoList(cartProductVoList);
			cartVo.setAllChecked(getAllCheckedStatus(userId));			
		}
		return cartVo;
	}
	
   //判断是否全选
   private boolean getAllCheckedStatus(Integer userId){
        if(userId == null){
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }


@Override
public CartVo update(Integer userId, Integer count, Integer productId) {
	if(count==null || productId==null){
		logger.error("购物车update方法中参数错误");
		return null;
	}
	//从购物车查找该商品是否已经存在
	MallCart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
	if(cart!=null){
		//更新该条购物车的数量
		cart.setQuantity(count);
	}
	cartMapper.updateByPrimaryKeySelective(cart);
	return this.getCartVoLimit(userId);
}


	@Override
	public CartVo delete(Integer userId, String productIds) {	
		List<String> productIdList =Arrays.asList(productIds.split(","));
		if(CollectionUtils.isEmpty(productIdList)){
			logger.error("delete方法中productIds参数错误");
			return null;
		}
		int rowcount = cartMapper.deleteByUserIdProductIds(userId, productIdList);
		if(rowcount>0){
			logger.info("删除购物车成功！！");
			return this.getCartVoLimit(userId);
		}else{
			logger.error("MallCartMapper层删除购物车失败！");
		}
		return this.list(userId);
	}


	@Override
	public CartVo list(Integer userId) {
		return this.getCartVoLimit(userId);
	}


	@Override
	public CartVo selectOrUnSelect(Integer userId,Integer productId,Integer checked) {
		cartMapper.checkedOrUnchecked(userId,productId,checked);
		logger.info("全选或全不选成功");
		return this.list(userId);
	}


	@Override
	public Integer getCartProductCount(Integer userId) {
	    return cartMapper.selectCartProductCount(userId);
	}

}
