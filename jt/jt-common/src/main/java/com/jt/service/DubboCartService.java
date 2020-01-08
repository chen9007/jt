package com.jt.service;

import java.util.List;

import com.alibaba.dubbo.config.annotation.Service;
import com.jt.pojo.Cart;

public interface DubboCartService {
	public List<Cart> findCartListByUserId(Long userId);

	public void addCart(Cart cart);

	public void updateCartNum(Cart cart);

	public void deleteCart(Cart cart);
}
