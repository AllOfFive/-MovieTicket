package edu.nju.ar.mapper;

import edu.nju.ar.model.User;

public interface UserMapper {
	//查询用户名是否存在
	public User selectUserByName(String name);
	
	//写入注册信息
	public void addUser(User user);
}
