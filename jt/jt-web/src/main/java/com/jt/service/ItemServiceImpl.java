package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.annotation.CacheAs;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.util.HttpClientService;
@Service
public class ItemServiceImpl implements ItemService{
	@Autowired(required = false)
	HttpClientService  cli;
	@Override
	@CacheAs
	public Item finItemById(Long itemId) {
		System.out.println("111");
		String url="http://manage.jt.com/web/item/findItemById?itemId="+itemId;
		Item item = cli.doGet(url,Item.class);
		return item;
	}
	@Override
	@CacheAs
	public ItemDesc finItemDescById(Long itemId) {
		String url="http://manage.jt.com/web/item/findItemDescById?itemId="+itemId;
		ItemDesc itemDesc= cli.doGet(url,ItemDesc.class);
		return itemDesc;
	}

}
