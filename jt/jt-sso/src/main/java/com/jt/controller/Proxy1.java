package com.jt.controller;


/**list动态代理*/


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Proxy;


public class Proxy1{
public static void main(String[] args) {
ArrayList<String> li = new  ArrayList<>();
@SuppressWarnings("unchecked")
List<String> px = (List<String>) Proxy.newProxyInstance(li.getClass().getClassLoader(), li.getClass().getInterfaces(), new InvocationHandler() {
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		return method.invoke(li, args);
	}
});
px.add("哈哈");
System.out.println(li);


}

}
