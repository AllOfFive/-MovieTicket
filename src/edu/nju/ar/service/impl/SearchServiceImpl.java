package edu.nju.ar.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.nju.ar.mapper.UserMapper;
import edu.nju.ar.model.User;
import edu.nju.ar.service.SearchService;
@Service
@Transactional
public class SearchServiceImpl implements SearchService{
	@Resource
	public UserMapper userMapper;
	
	
	
	@Override
	public boolean register(String name, String password) {
		if(userMapper.selectUserByName(name)==null){
			//���ע��
			User user=new User();
			user.setName(name);
			user.setPassword(password);
			userMapper.addUser(user);
			return true;
		}
		return false;
	}

}
