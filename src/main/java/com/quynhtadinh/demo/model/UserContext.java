package com.quynhtadinh.demo.model;


import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserContext implements UserDetails, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String username;
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;
	private final Date lastPasswordResetDate;
	
	private UserContext(String username, String password, Collection<? extends GrantedAuthority> authorities,
			Date lastPasswordResetDate){
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.lastPasswordResetDate = lastPasswordResetDate;
	}
	
	public static UserContext create(String username, String password, Collection<? extends GrantedAuthority> authorities,
			Date lastPasswordResetDate){
		 if (StringUtils.isBlank(username)) throw new IllegalArgumentException("Tên người dùng trốngs: " + username);
		 return new UserContext(username, password, authorities, lastPasswordResetDate);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	

}
