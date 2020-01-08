package com.jt.test;


import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

//@SpringBootTest
//@RunWith(SpringRunner.class)
public class TestRedis1 {
	/*
	 * @Autowired
	 * 
	 * private Jedis jedis;
	 * 
	 * 
	 * @Test public void dad() {
	 * 
	 * jedis.set("11", "222");
	 * 
	 * 
	 * }
	 */
	@Test
	 public void testShards() {
		
		 ArrayList<JedisShardInfo> ay = new  ArrayList<>();
		 ay.add(new JedisShardInfo("192.168.79.129",6379));
		 ay.add(new JedisShardInfo("192.168.79.129",6380));
		 ay.add(new JedisShardInfo("192.168.79.129",6381));
		 ShardedJedis jedis = new ShardedJedis(ay);
		 jedis.set("1908", "分片测试");
		 System.err.println(jedis.get("1908"));
		 
	 }
	
	@Test
	public void testsentinel() {
		HashSet<String> sentinels = new HashSet<>();
		sentinels.add("192.168.79.129:26379");		
		JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels);
		Jedis jedis = pool.getResource();
		jedis.set("name", "ys");
		System.out.println(jedis.get("name"));
	}
	
	
	 /*实现redis集群的测试*/
	@Test
	public void testCluster() {
	HashSet<HostAndPort> hset = new HashSet<>();
	hset.add(new HostAndPort("192.168.79.129", 7000));
	hset.add(new HostAndPort("192.168.79.129", 7001));
	hset.add(new HostAndPort("192.168.79.129", 7002));
	hset.add(new HostAndPort("192.168.79.129", 7003));
	hset.add(new HostAndPort("192.168.79.129", 7004));
	hset.add(new HostAndPort("192.168.79.129", 7005));
	JedisCluster cluster = new 	JedisCluster(hset);
	cluster.set("no.1", "你大爷还是你大爷");
	System.out.println(cluster.get("no.1"));
	}
	
}
