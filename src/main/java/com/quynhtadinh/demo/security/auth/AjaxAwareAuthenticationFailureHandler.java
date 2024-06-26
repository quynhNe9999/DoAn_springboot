package com.quynhtadinh.demo.security.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AjaxAwareAuthenticationFailureHandler implements AuthenticationFailureHandler{

	private final ObjectMapper mapper;
    
    @Autowired
    public AjaxAwareAuthenticationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
			throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		mapper.writeValue(response.getWriter(), "Đăng nhập thất bại");
		
		
	}

}
