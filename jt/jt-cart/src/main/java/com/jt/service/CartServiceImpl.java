package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
@Service
public class CartServiceImpl implements DubboCartService {
	
	@Autowired
	private CartMapper cartMapper;
	
	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		return cartMapper.selectList(queryWrapper);
	}

	@Override
	public void addCart(Cart cart) {
		//cart.setItemId(10087L);
		QueryWrapper<Cart> wp = new QueryWrapper<>();
		wp.eq("user_id", cart.getUserId());
		wp.eq("item_id", cart.getItemId());
		Cart ca = cartMapper.selectOne(wp);
		if (ca==null) {
			cart.setCreated(new Date()).setUpdated(new Date());
			cartMapper.insert(cart);
		}else {
			Integer num = ca.getNum()+cart.getNum();
			ca.setCreated(new Date()).setUpdated(new Date());
			ca.setNum(num);
			cartMapper.updateById(ca);
		}
		
		
		
	/*	Long userId = cart.getUserId();
		List<Cart> li= findCartListByUserId(userId);
		for (Cart c : li) {
			Long itemId = c.getItemId();
			if (itemId) {
				
			}
		}
		*/
	}

	@Override
	public void updateCartNum(Cart cart) {
		Integer num = cart.getNum();
		List<Cart> li = findCartListByUserId(cart.getUserId());
		for (Cart c : li) {
			if (c.getItemId()-cart.getItemId()==0L) {
				System.out.println(1111);
				c.setUpdated(new Date());
				c.setNum(num);
				cartMapper.updateById(c);
			}
		}
	}

	@Override
	public void deleteCart(Cart cart) {
		 QueryWrapper<Cart> wp = new QueryWrapper<>(cart);
		 cartMapper.delete(wp);
		 
		
	}

}
