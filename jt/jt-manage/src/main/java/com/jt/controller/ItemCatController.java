package com.jt.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;
import com.jt.vo.ItemCat;

@RestController
@RequestMapping("/item/cat/")
public class ItemCatController {

	@Autowired
	ItemCatService itemCatService;
	@RequestMapping("queryItemName")
	public String    selectitemCatById(Long itemCatId) {
	
	ItemCat itemCat=	itemCatService.findCatById(itemCatId);
		return itemCat.getName();
	
	}
	
	/*
	 * @RequestMapping("list") public List<EasyUITree> findItemCat(Long id){
	 * 
	 * Long parentId=id; if (id==null) {
	 * 
	 * parentId =0L; } System.out.println(111); return
	 * itemCatService.findItemCat(parentId); }
	 */
	@RequestMapping("list")
	public List<EasyUITree> findItemCat1(@RequestParam(name="id",defaultValue = "0")Long parentId){
		
		
		return itemCatService.findItemCat(parentId);
		//return itemCatService.findItemCatCache(parentId);
	}
	
}
