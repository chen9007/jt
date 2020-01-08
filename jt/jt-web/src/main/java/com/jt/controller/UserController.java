package com.jt.controller;

import java.util.HashSet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.util.CookieUtil;
import com.jt.util.IPUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
				@RequestMapping("/{moduleName}")
				public String user(@PathVariable String moduleName) {
					return moduleName;	
				}
				@Reference(check = false)
				private  DubboUserService   userService;
				@RequestMapping("/doRegister")
				@ResponseBody
				public SysResult  doRegister(User user) {
					userService.insertUserService(user);
					
					return SysResult.success();
				}
				@Autowired
				JedisCluster jedisCluster;
				@RequestMapping("/logout")
				
				public String  logout(HttpServletRequest request,HttpServletResponse response) {
					Cookie[] cookies = request.getCookies();
					CookieUtil.deleteCookie( "JT_TICKET", "/","jt.com",response);
					CookieUtil.deleteCookie( "JT_USER", "/","jt.com",response);
					if(cookies == null) {
						//重定向到系统首页
						
						return "redirect:/";
					}
						
					for (Cookie cookie : cookies) {
						
						String name = cookie.getName();
						String ticket = cookie.getValue();
						
						if (name=="JT_USER") {
							
							jedisCluster.del("JT_USER"+ticket);
						}else {
							jedisCluster.del(ticket);
						}
						

					}
					
					return "redirect:/";
				}

	/*cookie 设置时间为0 删除cookie，为-1，窗口关闭时删除cookie*/
				@RequestMapping("/doLogin")
				@ResponseBody
				public SysResult  doLogin(User user,HttpServletResponse response,HttpServletRequest request){
				
				String ip =IPUtil.getIpAddr(request);
				
				
				String ticket=userService.findUserByUP(user,ip);
				if (StringUtils.isEmpty(ticket)) {
					return SysResult.fail();
				}

				Cookie cookie = new Cookie("JT_TICKET", ticket);
				cookie.setMaxAge(60*60*24*7);
				//cookie.setMaxAge(0);
				cookie.setPath("/");
				cookie.setDomain("jt.com");
				response.addCookie(cookie);
				Cookie cookie1 = new Cookie("JT_USER", user.getUsername());
				cookie1.setMaxAge(60*60*24*7);
				//cookie.setMaxAge(0);
				cookie1.setPath("/");
				cookie1.setDomain("jt.com");
				response.addCookie(cookie1);
					return SysResult.success();
				}
				
				
				
}
