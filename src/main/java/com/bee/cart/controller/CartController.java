package com.bee.cart.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bee.cart.pojo.MallUser;
import com.bee.cart.service.ICartService;
import com.bee.cart.vo.CartVo;

@Controller
@RestController
@RequestMapping(value="/cart")
public class CartController {
	
	@Autowired
	private ICartService cartService;
	
	private Logger logger = LoggerFactory.getLogger(CartController.class);
	
	@GetMapping("/hello")
    public String hello(){
    	return "hello";
    }
	/**
	 * 加入购物车
	 * @param session
	 * @param count
	 * @param productId
	 * @return
	 */
	@GetMapping(value="/add")
	public CartVo add(HttpSession session,Integer count,Integer productId){
		logger.info("购物车add方法被访问");
		MallUser user = (MallUser)session.getAttribute("user");
		if(user==null){
			logger.error("add方法中用户未登录");
			return null;
		}
		logger.info("add方法中sessionId是："+session.getId());
		//添加购物车
		return cartService.add(user.getId(), count, productId);
	}
	/**
	 * 更新购物车的产品数量
	 * @param session
	 * @param count
	 * @param productId
	 * @return
	 */
	@GetMapping(value="/update")
	public CartVo update(HttpSession session,Integer count,Integer productId){
		logger.info("购物车update方法被访问");
		MallUser user = (MallUser)session.getAttribute("user");
		if(user==null){
			logger.error("update方法中用户未登录");
			return null;
		}
		logger.info("update方法中sessionId是"+session.getId());
		return cartService.update(user.getId(), count, productId);
	}
	/**
	 * 删除购物车中指定的商品
	 * @param session
	 * @param productIds
	 * @return
	 */
	@GetMapping(value="/delete")
	public CartVo delete (HttpSession session,String productIds){
		logger.info("购物车delete方法被访问");
		MallUser user = (MallUser)session.getAttribute("user");
		if(user==null){
			logger.error("delete方法中用户未登录");
			return null;
		}		
		logger.info("delete方法中sessionId是："+session.getId());	
		return cartService.delete(user.getId(), productIds);
	}
	
	@GetMapping(value="/list")
	public CartVo list(Integer userId){	
		logger.info("购物车list方法被访问");
		return cartService.list(userId);
	}
	/**
	 * 全部选择
	 * @param session
	 * @return
	 */
	@GetMapping(value="/selectAll")
	public CartVo selectAll(HttpSession session){
		logger.info("购物车selectAll方法被访问");
		MallUser user = (MallUser)session.getAttribute("user");
		if(user==null){
			logger.error("selectAll方法中用户未登录");
			return null;
		}
		logger.info("购物车selectAll方法中sessionId是："+session.getId());
		cartService.selectOrUnSelect(user.getId(),null, 1);//1表示已勾选		
		return this.list(user.getId());
		
	}
	
	/**
	 * 全部反选
	 * @param session
	 * @return
	 */
	@GetMapping(value="/unSelectAll")
	public CartVo unSelectAll(HttpSession session){
		logger.info("购物车unSelectAll方法被访问");
		MallUser user = (MallUser)session.getAttribute("user");
		if(user==null){
			logger.error("unSelectAll方法中用户未登录");
			return null;
		}
		logger.info("购物车unSelectAll方法中sessionId是："+session.getId());
		cartService.selectOrUnSelect(user.getId(),null, 0);//0表示不勾选		
		return this.list(user.getId());
		
	}
	
	/**
	 * 选择商品（单个选择）
	 * @param session
	 * @param productId
	 * @return
	 */
	@GetMapping(value="/selected")
	public CartVo selected(HttpSession session,Integer productId){
		logger.info("购物车selected方法被访问");
		MallUser user = (MallUser)session.getAttribute("user");
		if(user==null){
			logger.error("selected方法中用户未登录");
			return null;
		}
		logger.info("购物车selected方法中sessionId是："+session.getId());
		cartService.selectOrUnSelect(user.getId(),productId, 1);//1表示勾选		
		return this.list(user.getId());
		
	}
	/**
	 * 取消选择商品（单个取消）
	 * @param session
	 * @param productId
	 * @return
	 */
	@GetMapping(value="/unSelected")
	public CartVo unSelected(HttpSession session,Integer productId){
		logger.info("购物车unSelected方法被访问");
		MallUser user = (MallUser)session.getAttribute("user");
		if(user==null){
			logger.error("unSelected方法中用户未登录");
			return null;
		}
		logger.info("购物车unSelected方法中sessionId是："+session.getId());
		cartService.selectOrUnSelect(user.getId(),productId, 0);//0表示不勾选		
		return this.list(user.getId());
		
	}
	
	/**
	 * 获取用户购物车内的商品数量
	 * @param session
	 * @return
	 */
	@GetMapping(value="/getCartProductCount")
	public Integer getCartProductCount(HttpSession session){
		logger.info("购物车getCartProductCount方法被访问");
		MallUser user = (MallUser)session.getAttribute("user");
		if(user==null){
			logger.error("getCartProductCount方法中用户未登录,返回结果是0");
			return 0;
		}
		logger.info("购物车getCartProductCount方法中sessionId是："+session.getId());	
		return cartService.getCartProductCount(user.getId());		
	}
	
	
	
	
}
