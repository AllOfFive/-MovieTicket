package edu.nju.ar.service;

public interface UserService {
	//用户注册,如果成功注册返回true，如果不能注册如用户名已存在则返回FALSE
	public boolean register(String name,String password);
}
