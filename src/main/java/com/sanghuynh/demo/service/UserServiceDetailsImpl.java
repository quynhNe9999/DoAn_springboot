package com.sanghuynh.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sanghuynh.demo.entity.Users;
import com.sanghuynh.demo.model.UserContext;
import com.sanghuynh.demo.repository.UserDAO;




@Service
public class UserServiceDetailsImpl implements UserDetailsService{

	private final UserDAO userDAO;
    
    @Autowired
    public UserServiceDetailsImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
	
    public UserDAO getUserDAO(){
    	return userDAO;
    }
    
	@Override
	public UserContext loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userDAO.findByUsername(username);
		if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
        	return UserContext.create(user.getUsername(), user.getPassword(),
        			mapAuthorities(user), user.getLastPasswordResetDate());
        }
	}
	
	private List<GrantedAuthority> mapAuthorities(Users user){
		return user.getAuthorities().stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
				.collect(Collectors.toList());
	}

}
