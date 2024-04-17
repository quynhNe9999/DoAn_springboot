package com.quynhtadinh.demo.security.auth;

import com.quynhtadinh.demo.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.quynhtadinh.demo.service.UserServiceDetailsImpl;



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

		Assert.notNull(authentication, "Không có dữ liệu xác thực được cung cấp");
		
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		
		UserContext userContext = userServiceDetailsImpl.loadUserByUsername(username);
		
		if(!encoder.matches(password, userContext.getPassword())) throw new BadCredentialsException("Quá trình xác thực đã thất bại. Tên người dùng hoặc mật khẩu không hợp lệ.");
		
		return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
