package edu.nju.ar.mapper;

import edu.nju.ar.model.User;

public interface UserMapper {
	//��ѯ�û����Ƿ����
	public User selectUserByName(String name);
	
	//д��ע����Ϣ
	public void addUser(User user);
}
