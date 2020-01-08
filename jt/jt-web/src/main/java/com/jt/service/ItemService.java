package com.jt.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
public interface ItemService {

	public Item finItemById(Long itemId);
	public ItemDesc finItemDescById(Long itemId);
			
}
