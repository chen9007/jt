package com.jt.exception;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
@Aspect
public class ExceptionAOP1 {
			@AfterThrowing(pointcut ="execution(* com.jt..*.*(..))",throwing ="throwable" )
			public void aftethrow(JoinPoint jp,Throwable throwable) {
				String n1 = jp.getSignature().getDeclaringTypeName();
				Class<? extends Object> n2 = jp.getTarget().getClass();
				Class<? extends Throwable> c = throwable.getClass();
				String m = throwable.getMessage();
				System.err.println("异常位置 :"+n1);
				
				System.err.println("异常类型:"+c);
				System.out.println("异常详情"+m);
				
				throwable.printStackTrace();
				
			}
}
