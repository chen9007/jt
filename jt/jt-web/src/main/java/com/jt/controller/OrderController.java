package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.service.DubboCartService;
import com.jt.service.OrderService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/order/")
public class OrderController {
	@Reference
	private  DubboCartService  ds;
	@Reference
	private OrderService  os;

	@RequestMapping("create")
	public String order(Model  model) {

		Long id = UserThreadLocal.get().getId();
		List<Cart> cali = ds.findCartListByUserId(id);
		model.addAttribute("carts",cali);
		return "order-cart";
	}
	@RequestMapping("myOrder")
	public String myorder(Model  model) {
		
		Long id = UserThreadLocal.get().getId();
		List<Cart> cali = ds.findCartListByUserId(id);
		model.addAttribute("carts",cali);
		return "order-cart";
	}
	@RequestMapping("submit")
	@ResponseBody
	public SysResult saveOrder(Order order) {
		String OrderId =os.saveorder(order);
		
		return SysResult.success(OrderId);
	}
	@RequestMapping("success")
	public String saveOrder(String id,Model model) {
	Order order=os.findOrderById(id);
	model.addAttribute("order", order);
		return "success";
	}
}
