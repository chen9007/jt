package com.jt.service;

import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Queues;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Override
	@Transactional
	public String saveorder(Order order) {
		String  orderId=order.getUserId()+""+System.currentTimeMillis();
		Date date = new Date();
		order.setOrderId(orderId).setStatus(1).setCreated(date).setUpdated(date);
		orderMapper.insert(order);
		System.out.println("1.商品入库  ok！");
		OrderShipping ship = order.getOrderShipping();
		ship.setOrderId(orderId).setCreated(date).setUpdated(date);
		orderShippingMapper.insert(ship);
		System.out.println("2.物流入  ok！");
		List<OrderItem> it = order.getOrderItems();
		for (OrderItem i : it) {
			
			i.setOrderId(orderId).setCreated(date).setUpdated(date);
			orderItemMapper.insert(i);
		}
		System.out.println("3.订单item  ok!");
		return orderId;
	}
	@Override
	public Order findOrderById(String id) {
		Order order = orderMapper.selectById(id);
		OrderShipping ship = orderShippingMapper.selectById(id);
		QueryWrapper<OrderItem> wp = new QueryWrapper<>();
		wp.eq("order_id", id);
		
		orderItemMapper.selectList(wp);
		Stack<Object> s = new Stack<>();
	
		return order;
	}
	
	
}
