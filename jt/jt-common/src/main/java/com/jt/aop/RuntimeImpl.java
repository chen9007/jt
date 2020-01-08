package com.jt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Aspect
@Component

public class RuntimeImpl {
			
		@Around("execution(* com.jt.service..*.*(..))")
		public Object around(ProceedingJoinPoint jp) {
			Object obj=null;
			long s = System.currentTimeMillis();
			try {
				 obj = jp.proceed();
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				throw new RuntimeException(e1);
			}
			long e = System.currentTimeMillis();
			long time=e-s;
			Class<? extends Object> n1 = jp.getTarget().getClass();
			String n2 = jp.getTarget().getClass().getName();
			String n3 = jp.getSignature().getName();
		/*
		 * System.err.println(n1); System.err.println(n2);
		 */
			System.err.println(n2+"."+n3+"::"+time);
			return obj;
			
			
		}
}
