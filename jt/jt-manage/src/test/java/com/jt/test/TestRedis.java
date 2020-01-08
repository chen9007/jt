package com.jt.test;



import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestRedis {
		private Jedis jedis;
		@Before
		public void innit() {
			
			
			jedis=new Jedis("192.168.79.129",6379);
		}
		
		
		@Test
		public void test1() throws Exception {
			jedis.set("1908", "go on and on!");
			System.err.println(jedis.get("1908"));
			jedis.append("1908", "         my heart will be go on!");
			System.err.println(jedis.get("1908"));
			jedis.setex("1908", 20, "i see you!!");
			System.err.println(jedis.get("1908"));
			//jedis.psetex("1908", 20, "i see you!!");
			//System.err.println(jedis.get("1908"));
			Thread.sleep(1000);
			System.err.println(jedis.ttl("1908"));
			
			//System.err.println(jedis.asking());
			
		}
		
		@Test
		public  void  testNxEX() {
			String s = jedis.set("abc", "Need just word! Word has word!", "NX", "EX", 20);
			System.err.println(s);
			System.err.println(jedis.get("abc"));
			
			
			
		}
		@Test
		public  void  testhx() {
			
		jedis.hset("person","id","100");
		 jedis.hset("person","name","ys");
		 Map<String, String> al = jedis.hgetAll("person");
			System.err.println(al);
			
			
			
			
		}
		@Test
		//队列和栈
		public  void  tesLis() {
			
			jedis.lpush("list", "1","2","3","4","5","6");
			for (int i = 1; i <= 6; i++) {
				String l= jedis.rpop("list");
				//String m= jedis.lpop("list");
				
				System.out.println(i+"--->    "+l);
			}	
			jedis.flushAll();
		}
		@Test
		//事物
		public  void  tesTX() {
			
			Transaction ml = jedis.multi();
			try {
						ml.set("aaa"," 1");
						ml.set("bbb"," 1");
						ml.exec();//提交
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ml.discard();//回滚
			}
					
		}
}
