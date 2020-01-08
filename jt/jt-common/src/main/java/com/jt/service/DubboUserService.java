package com.jt.service;

import com.jt.pojo.User;

public interface DubboUserService {

	void insertUserService(User user);

	String findUserByUP(User user,String ip);

}
