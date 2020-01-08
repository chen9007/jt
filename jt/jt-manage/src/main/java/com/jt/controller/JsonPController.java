package com.jt.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.SysResult;

@RestController
@RequestMapping("/user")
public class JsonPController {
			//@RequestMapping("/web/testJSONP")
			public String   jsonp(String callback) {
				ItemDesc des = new  ItemDesc();
				des.setItemId(1001L).setItemDesc("详情描述").setCreated(new Date()).setUpdated(new Date());
				String jsonp = ObjectMapperUtil.toJson(des);
				
				
				return callback+"("+jsonp+")";
			} 
			@RequestMapping("web/testJSONP")
			public JSONPObject   jsonp2(String callback) {
				ItemDesc des = new  ItemDesc();
				des.setItemId(1001L).setItemDesc("详情描述").setCreated(new Date()).setUpdated(new Date());
				//String jsonp = ObjectMapperUtil.toJson(des);
				
				JSONPObject jo = new JSONPObject(callback,des);
				return jo;
			} 
}
