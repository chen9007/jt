package com.jt.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

@Service
public class DubboUserServiceImpl implements DubboUserService {
	@Autowired
	private  UserMapper usermapper;
	@Autowired
	private JedisCluster  jediscluster;
	@Override
	public void insertUserService(User user) {
		String md5pass = org.springframework.util.DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5pass).setEmail(user.getPhone()).setUpdated(new Date()).setCreated(new Date());
		usermapper.insert(user);
	}

	@Override
	public String findUserByUP(User user,String userIP){
		
		String ticket;
		String pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(pwd);
		QueryWrapper<User> wp = new QueryWrapper<>(user);
		
		User userDB = usermapper.selectOne(wp);
		if (userDB==null) {
			return null;
		}
		userDB.setPassword("*******");
		if (!org.springframework.util.StringUtils.isEmpty(userDB)) {
			ticket=UUID.randomUUID().toString();
			if (jediscluster.exists("JT_USER"+user.getUsername())) {
				String oldtik=jediscluster.get("JT_USER"+user.getUsername());
				jediscluster.del(oldtik);                                   
			}
			String json = ObjectMapperUtil.toJson(userDB);
			/*jediscluster.hset("JT_USER"+userDB.getUsername(), "JT_USER",json );


					 jediscluster.hset("JT_USER"+userDB.getUsername(), "JT_USER_IP",userIP );
					 jediscluster.expire("JT_USER"+userDB.getUsername(), 86400*7);*/

			jediscluster.hset(ticket, "JT_USER",json );
			jediscluster.hset(ticket, "JT_USER_IP",userIP ); 
			jediscluster.expire(ticket,
					86400*7);

			jediscluster.setex("JT_USER_"+user.getUsername(),
					86400*7,ticket);

		}else {
			return  null;
		}
		
		return ticket;
	}

}
