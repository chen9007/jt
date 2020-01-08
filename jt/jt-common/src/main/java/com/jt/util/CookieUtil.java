package com.jt.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	public static void deleteCookie(String cookie,String path,String domain,HttpServletResponse response) {
		Cookie ck = new Cookie(cookie, "");
		ck.setMaxAge(0);
		ck.setPath(path);
		ck.setDomain(domain);
		response.addCookie(ck);	
	}
	public static Cookie  getCookie(HttpServletRequest request,String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies!=null&&cookies.length>0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return cookie;
				}
			}
		}
		return null;
		
	}
}
