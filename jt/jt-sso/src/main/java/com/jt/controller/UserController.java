package com.jt.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.util.CookieUtil;
import com.jt.util.IPUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user/")
public class UserController {
				@Autowired
				UserService  userService;
				@RequestMapping("/findAll")
				public List<User> findAll(){
					
					List<User> list=userService.findALL();
					return list;
				
				}
				@RequestMapping("/check/{param}//{type}")
				public JSONPObject checUser(@PathVariable String param,@PathVariable Integer type,String callback) {
					
					
					boolean flag=userService.findUserByType(param,type);
					return new JSONPObject(callback, SysResult.success(flag));
				}
				
				
				@Autowired
				private  JedisCluster jedisCluster;
				
				@RequestMapping("/query/{ticket}/{username}")
				public JSONPObject doquery(@PathVariable String ticket,@PathVariable  String username,HttpServletRequest request,String callback,HttpServletResponse response) {
					String ip=IPUtil.getIpAddr(request);
					
					Map<String, String> ti = jedisCluster.hgetAll(ticket);
					JSONPObject ob=null;
					String s = jedisCluster.get("JT_USER_"+username);
					if (StringUtils.isEmpty(s)) {
						ob=	new JSONPObject(callback, SysResult.fail());
						CookieUtil.deleteCookie("JT_TICKET", "/", "jt.com", response);
						CookieUtil.deleteCookie("JT_USER", "/", "jt.com", response);
						return ob;
					}
					if (!ip.equals(ti.get("JT_USER_IP"))) {
					ob=	new JSONPObject(callback, SysResult.fail());
					CookieUtil.deleteCookie("JT_TICKET", "/", "jt.com", response);
					CookieUtil.deleteCookie("JT_USER", "/", "jt.com", response);
					return ob;
					}
					if (!s.equals(ticket)) {
						ob=	new JSONPObject(callback, SysResult.fail());
						CookieUtil.deleteCookie("JT_TICKET", "/", "jt.com", response);
						CookieUtil.deleteCookie("JT_USER", "/", "jt.com", response);
						return ob;
					}
					if (!jedisCluster.exists(ticket)) {
						ob=	new JSONPObject(callback, SysResult.fail());
						CookieUtil.deleteCookie("JT_TICKET", "/", "jt.com", response);
						CookieUtil.deleteCookie("JT_USER", "/", "jt.com", response);
						return ob;
					}
					String userJson=ti.get("JT_USER");
					if (StringUtils.isEmpty(userJson)) {
					ob=	new JSONPObject(callback, SysResult.fail());
					CookieUtil.deleteCookie("JT_TICKET", "/", "jt.com", response);
					CookieUtil.deleteCookie("JT_USER", "/", "jt.com", response);
						return ob;
					}
					
					ob=	new JSONPObject(callback, SysResult.success(userJson));
					return ob;				
				}
				
				
				
}
