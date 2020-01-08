package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	/*
	 * @RequestMapping("/page/{moduleName}") public String module(@PathVariable
	 * String moduleName) {
	 * 
	 * return moduleName; }
	 */
	@RequestMapping("/page/{moduleName}")
	public String mode(@PathVariable String moduleName) {
		
		return moduleName;		
	}
	
	@GetMapping(value = "/user")//查询
	public void userselect() {
		
		
	}
	@GetMapping(value = "/game")//查询
	public String  game() {
		return "game";
		
		
	}
	//@DeleteMapping(value = "/user")删除
	//@PutMapping(value = "/user")更改
	
	@PostMapping(value = "/user")//追加,新增
	public void userinsert() {
		
		
	}
	
}
