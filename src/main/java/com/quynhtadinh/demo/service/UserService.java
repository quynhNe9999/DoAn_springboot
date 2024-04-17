package com.quynhtadinh.demo.service;

import com.quynhtadinh.demo.entity.Users;

public interface UserService {
	Users getUserById(Long id);

	Users updateUser(Users users);

	void deleteUserById(Long id);

	Users saveUser(Users users);
}
