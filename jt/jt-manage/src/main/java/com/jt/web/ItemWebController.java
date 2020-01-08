package com.jt.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;

@RestController
@RequestMapping("/web/item/")
public class ItemWebController {
	@Autowired
	ItemService itemService;
	@RequestMapping("findItemById")
	public Item  findItemById(Long itemId) {

		Item item = itemService.findItemById(itemId);
		return item;

	}
	@RequestMapping("findItemDescById")
	public ItemDesc  findItemDescById(Long itemId) {	
		ItemDesc item = itemService.findItemDescById(itemId);
		return item;
	}
}
