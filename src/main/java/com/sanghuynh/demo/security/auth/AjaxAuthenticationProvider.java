package com.sanghuynh.demo.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.sanghuynh.demo.model.UserContext;
import com.sanghuynh.demo.service.UserServiceDetailsImpl;



@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider{
	private final BCryptPasswordEncoder encoder;
    private final UserServiceDetailsImpl userServiceDetailsImpl;
	
    @Autowired
    public AjaxAuthenticationProvider(final UserServiceDetailsImpl userServiceDetailsImpl, final BCryptPasswordEncoder encoder) {
        this.userServiceDetailsImpl = userServiceDetailsImpl;
        this.encoder = encoder;
    }
    
    
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		Assert.notNull(authentication, "No authentication data provided");
		
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		
		UserContext userContext = userServiceDetailsImpl.loadUserByUsername(username);
		
		if(!encoder.matches(password, userContext.getPassword())) throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
		
		return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
