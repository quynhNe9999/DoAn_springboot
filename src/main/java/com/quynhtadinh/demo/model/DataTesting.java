package com.quynhtadinh.demo.model;

/*import java.text.DateFormat;
import java.text.SimpleDateFormat;*/
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.quynhtadinh.demo.entity.Authority;
import com.quynhtadinh.demo.entity.Users;
import com.quynhtadinh.demo.repository.AuthorityDAO;
import com.quynhtadinh.demo.repository.UserDAO;



@Component
public class DataTesting implements ApplicationListener<ContextRefreshedEvent>{
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private AuthorityDAO authDAO;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		
		//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		
		// Admin account
	    if (userDAO.findByUsername("admin") == null) {
	    	Date date = new Date();
	        Users admin = new Users("admin", passwordEncoder.encode("123"), date);
	        List<Authority> authorities = new ArrayList<Authority>();
	        authorities.add(authDAO.findOne(1));
	        admin.setAuthorities(authorities);
	        userDAO.save(admin);
	    }
	    
	    // User account
	    if (userDAO.findByUsername("users") == null) {
	    	Date date = new Date();
	        Users user = new Users("users", passwordEncoder.encode("123"), date);
	        List<Authority> authorities = new ArrayList<Authority>();
	        authorities.add(authDAO.findOne(2));
	        user.setAuthorities(authorities);
	        userDAO.save(user);
	    }
	    
	    // Staff account
	    if (userDAO.findByUsername("staff") == null) {
	    	Date date = new Date();
	        Users staff = new Users("staff", passwordEncoder.encode("123"), date);
	        List<Authority> authorities = new ArrayList<Authority>();
	        authorities.add(authDAO.findOne(3));
	        staff.setAuthorities(authorities);
	        userDAO.save(staff);
	    }
	}
}
