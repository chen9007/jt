package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jt.pojo.User;
import com.jt.util.CookieUtil;
import com.jt.util.IPUtil;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

@Component
public class UserInterceptor implements HandlerInterceptor{

		@Autowired
		private JedisCluster jedis;
		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			
			Cookie userc = CookieUtil.getCookie(request, "JT_USER");
			Cookie tikc = CookieUtil.getCookie(request, "JT_TICKET");
			if (StringUtils.isEmpty(userc)||StringUtils.isEmpty(tikc)) {
				response.sendRedirect("/user/login.html");
				return false;
				
			}
			String username = userc.getValue();//admin123
			String ti= tikc.getValue();//5f1635e8-af02-4ffa-b024-f7d8e384832d
			if (StringUtils.isEmpty(ti)||StringUtils.isEmpty(username)) {
				response.sendRedirect("/user/login.html");
				return false;
				
			}
			String ip = IPUtil.getIpAddr(request);//127.0.0.1
			String ip1 = jedis.hget(ti, "JT_USER_IP");
			if (!ip.equals(ip1)) {
				CookieUtil.deleteCookie("JT_USER", "/", "jt.com", response);
				CookieUtil.deleteCookie("JT_TICKET", "/", "jt.com", response);
				response.sendRedirect("/user/login.html");
				return false;
			}
			String usern = jedis.get("JT_USER_"+username);
			if (!usern.equals(ti)) {
				
				CookieUtil.deleteCookie("JT_USER", "/", "jt.com", response);
				CookieUtil.deleteCookie("JT_TICKET", "/", "jt.com", response);
				response.sendRedirect("/user/login.html");
				return false;
			}
			String user = jedis.hget(ti, "JT_USER");
			System.out.println(user);
			//User userobj = ObjectMapperUtil.toObj(user, User.class);
			User userobj = ObjectMapperUtil.toObj(user, User.class);
		    request.setAttribute("JT_USER",userobj);
			UserThreadLocal.set(userobj);
			
			return true;
		}
		@Override
		public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserThreadLocal.remove();
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
		}
}
