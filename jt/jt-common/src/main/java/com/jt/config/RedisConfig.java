package com.jt.config;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

@Configuration
@PropertySource("classpath:/redis.properties")
public class RedisConfig {
	
	
	@Value("${redis.nodes}")
	private String nodes;
	@Value("${redis.sentinel}")
	private String nodes1;
	@Value("${cluster.nodes}")
	private String nodes2;
	
//	public JedisSentinelPool pool;
	@Bean
	@Scope("prototype")
	public JedisCluster  cluster1(){
		Set<HostAndPort> hset = new HashSet<>();
		String[] node= nodes2.split(",");
		for (String nod : node) {
			String host=nod.split(":")[0];
			int port=Integer.valueOf(nod.split(":")[1]);
			hset.add(new HostAndPort(host, port));
		}
		
	
		JedisCluster jedis = new JedisCluster(hset);
		return jedis;
	}
	
	
	/*
	private ShardedJedis  jedis;
	
	@Scope("prototype")
	@Bean
	//redis分片
     public ShardedJedis  sharedjedis() {
		ArrayList<JedisShardInfo> ay = new  ArrayList<>();
    	 String[] arrayNode=nodes.split(",");
    	 for (String node : arrayNode) {
			String host=node.split(":")[0];
			int port=Integer.parseInt(node.split(":")[1]);
			ay.add(new JedisShardInfo(host,port));
    	 }
    	 jedis=new ShardedJedis(ay);
    	
		return jedis;
    	 
    	 
     }
    
	@Scope("prototype")
	@Bean
	public Jedis jedis(JedisSentinelPool pool) {
		
		 //pool = pool();
		Jedis jedis = pool.getResource();
		return jedis;
		
	}
	@Bean
	public JedisSentinelPool pool() {
		HashSet<String> sentinels = new HashSet<>();
		sentinels.add(nodes1);		
		JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels);
		return pool;
	}
	
	 
 */

/*
	@Value("${redis.host}")
	private String host;
	@Value("${redis.port}")
	private int port;
	@Bean
	@Scope("prototype")
	public Jedis jedis() {

		return new Jedis(host, port);

	}*/
	
}
