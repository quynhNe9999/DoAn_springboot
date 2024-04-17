package com.quynhtadinh.demo.service.impl;

import com.quynhtadinh.demo.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quynhtadinh.demo.entity.Users;
import com.quynhtadinh.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Override
	public Users getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Users updateUser(Users users) {
		// TODO Auto-generated method stub
		return userDAO.save(users);
	}

	@Override
	public void deleteUserById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Users saveUser(Users users) {
		// TODO Auto-generated method stub
		return userDAO.save(users);
	}

}
