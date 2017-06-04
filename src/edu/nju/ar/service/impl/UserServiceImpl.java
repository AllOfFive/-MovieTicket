package edu.nju.ar.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.nju.ar.mapper.UserMapper;
import edu.nju.ar.model.User;
import edu.nju.ar.service.UserService;
@Service
@Transactional
public class UserServiceImpl implements UserService{
	@Resource
	public UserMapper userMapper;
	
	
	
	@Override
	public boolean register(String name, String password) {
		if(userMapper.selectUserByName(name)==null){
			//Íê³É×¢²á
			User user=new User();
			user.setName(name);
			user.setPassword(password);
			userMapper.addUser(user);
			return true;
		}
		return false;
	}

}
