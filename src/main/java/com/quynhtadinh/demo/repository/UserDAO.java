package com.quynhtadinh.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.quynhtadinh.demo.entity.Users;



@Transactional
public interface UserDAO extends JpaRepository<Users, Integer>{

	Users findByUsername(String username);
	
}
