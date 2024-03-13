package com.sanghuynh.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sanghuynh.demo.entity.Users;



@Transactional
public interface UserDAO extends JpaRepository<Users, Integer>{

	Users findByUsername(String username);
	
}
