package com.jt.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.vo.SysResult;

@Service

public class UserServerImpl implements UserService{
	@Autowired
	public UserMapper userMapper;

	@Override
	public List<User> findALL() {
		List<User> list = userMapper.selectList(null);
		return list;
	}

	@Override
	public boolean findUserByType(String param, Integer type) {
		HashMap<Integer,String> map = new HashMap<>();
		map.put(1, "username");
		map.put(2, "phone");
		map.put(3, "email");
		String column=(String) map.get(type);
		 QueryWrapper<User> wp = new QueryWrapper<>();
		 wp.eq(column, param);
		 User res = userMapper.selectOne(wp);	
		return res==null?false:true;
	}
	
	


}
