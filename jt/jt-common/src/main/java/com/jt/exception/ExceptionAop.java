package com.jt.exception;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.vo.SysResult;

@RestControllerAdvice
public class ExceptionAop {

	/*
	 * @Pointcut("execution(* com.jt.Controller..*.(..))",) public SysResult
	 * sysThrow(RuntimeException re) {
	 * 
	 * }
	 */
	/*
	 * @ExceptionHandler(RuntimeException.class) public SysResult
	 * fail(RuntimeException e) {
	 * 
	 * e.printStackTrace(); return SysResult.fail();
	 * 
	 * 
	 * }
	 */
	@ExceptionHandler(RuntimeException.class)
	public Object  error(Exception e,HttpServletRequest request) {
		String callback = request.getParameter("callback");
		if (StringUtils.isEmpty(callback)) {
			return SysResult.fail();
		}else {
			e.printStackTrace();
			return new JSONPObject(callback, SysResult.fail());
		}
		
		
		
	}
}
