package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUITable;
import com.jt.vo.SysResult;

@RestController
@RequestMapping("/item/")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	@RequestMapping("query")
	public EasyUITable findgoods(int page,int rows) {
		
		EasyUITable aa = itemService.findItemByPage(page,rows);
		System.out.println(aa);
		return aa;
	}
	@RequestMapping("save")
	public SysResult saveItem(Item item,ItemDesc itemDesc) {
		
		 itemService.saveItem(item ,itemDesc);
		 
		 
		
		return SysResult.success();
	}
	@RequestMapping("query/item/desc/{itemId}")
	public SysResult  desc(@PathVariable Long itemId) {
		
		ItemDesc itemDesc1=itemService.getDesc(itemId);
		
		String itemDesc = itemDesc1.getItemDesc();
		
		return SysResult.success(itemDesc);
	}
	@RequestMapping("update")
	public SysResult updateItem(Item item,ItemDesc itemdesc) {
		
		itemService.updateItem(item,itemdesc);
		System.out.println(11111);
		
		return SysResult.success();
	}
	@RequestMapping("delete")
	public SysResult deleteItem(Long[] ids) {
		
		itemService.deleteItem(ids);
		System.out.println(11111);
		
		
		return SysResult.success();
		
	}
	@RequestMapping({"instock","reshelf"})
	public SysResult dawd(Long[] ids,Integer status ) {
		
		itemService.instock(ids,status);

		return SysResult.success();
	}
	
}
