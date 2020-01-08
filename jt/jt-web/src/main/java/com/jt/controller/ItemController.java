package com.jt.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

@Controller
@RequestMapping("")
public class ItemController {
	@Autowired
	ItemService  is;
	@RequestMapping("/items/{itemId}")
	public String item(@PathVariable Long itemId,Model model) {
		
		Item item=is.finItemById(itemId);
		
		ItemDesc itemDesc=is.finItemDescById(itemId);
		
		model.addAttribute("item",item);
		model.addAttribute("itemDesc",itemDesc);
		return "item";
	}

}
