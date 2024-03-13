package com.sanghuynh.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sanghuynh.demo.entity.Authority;



@Transactional
public interface AuthorityDAO extends JpaRepository<Authority, Integer>{
}
