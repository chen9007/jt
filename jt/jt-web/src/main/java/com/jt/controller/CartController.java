package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;



@Controller
@RequestMapping("/cart")
public class CartController {
	Long c;
	@Reference
	private DubboCartService  cartservice;
	@RequestMapping("/show")
	public  String show(Model model,HttpServletRequest request) {
		//方法1
		User user=(User) request.getAttribute("JT_USER");
		Long userId = user.getId();
		//方法2
	   // Long userId = UserThreadLocal.get().getId();
		List<Cart> li=cartservice.findCartListByUserId(userId);
		model.addAttribute("cartList",li);
		
		if (StringUtils.isEmpty(c)) {
			
		 c=1L;
		}
		System.out.println(c++);
		
		return "cart";	
	}
	@RequestMapping("/add/{itemId}")
	public String addCart(Cart cart) {
		Long userId=UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cartservice.addCart(cart);
		return "redirect:/cart/show.html";
	
	}
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(Cart cart) {
		
		Long userId=UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cartservice.deleteCart(cart);
		return "redirect:/cart/show.html";
		
	}
	@RequestMapping("/update/num/{itemId}/{num}")
	public SysResult updateCart(Cart cart) {
		System.out.println(666);
		Long userId=UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cartservice.updateCartNum(cart);
		return SysResult.success();
		
	}
}
