package com.jt.aop;


import java.util.ArrayList;
import java.util.List;



import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jt.annotation.CacheAs;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

@org.aspectj.lang.annotation.Aspect
@Component
public class CatAspect {
	@Autowired(required = false)
	//ShardedJedis jedis;
	//Jedis jedis;
	JedisCluster jedis;
	
	 // @Pointcut("@annotation(com.jt.config.CacheAs)")   public void cut() {}
	@Around("@annotation(cacheAs)")
	public Object  around(ProceedingJoinPoint jp,CacheAs cacheAs){
	//jedis.flushAll();
	
		
		String key = check(jp,cacheAs);
		Object p;
		String value = jedis.get(key);
		String json=null;
		if (StringUtils.isEmpty(value)) {
			
			try {
				 p = jp.proceed();
				 json = ObjectMapperUtil.toJson(p);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException();
			}
			if (cacheAs.seconds()==0) {
				System.out.println(key);
				System.out.println(json);
				jedis.set(key, json);
			}else {		
				jedis.setex(key, cacheAs.seconds(), json);
			}}else {
			MethodSignature si = (MethodSignature) jp.getSignature();	
			String w = jp.getArgs()[0].getClass().getTypeName();
			System.out.println(w+"-------------------------");
			Class type= si.getReturnType();			
			p=ObjectMapperUtil.toObj(value, type);			
			
		}	
		return p;	
}	
	private String check( ProceedingJoinPoint jp,CacheAs  cacheAs) {
	//int s=8/0;
		String n1 = jp.getSignature().getDeclaringTypeName();
		String n2 = jp.getSignature().getName();
		System.out.println(n1);
		System.out.println(n2);
		 Object arg = jp.getArgs()[0];
		
		String key = cacheAs.key();
		if (!StringUtils.isEmpty(key)) {
			key=key;
			
		}else {
			key=n1+"."+n2+"::"+arg;
			
		}
		
		return  key;
	}
	
	
	
}

