package com.jt.service;

import com.jt.pojo.Order;

public interface OrderService {

	public  String saveorder(Order order);

	public  Order findOrderById(String id) ;

}
