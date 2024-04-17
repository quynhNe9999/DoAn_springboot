package com.quynhtadinh.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quynhtadinh.demo.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.quynhtadinh.demo.model.token.JwtToken;
import com.quynhtadinh.demo.service.UserServiceDetailsImpl;
import com.quynhtadinh.demo.utils.JwtTokenUtils;



public class JwtAuthenticationTokenFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	@Autowired
	private UserServiceDetailsImpl userServiceDetailsImpl;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String token = getTokenFromCookie(request);
		if(token == null){
			token = getTokenFromSession(request);
		}
		String username = jwtTokenUtils.getUsernameFromToken(token);

		if(username != null){
			UserContext userContext = userServiceDetailsImpl.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			System.out.println(authentication);
		}
		
		chain.doFilter(request, response);
	}
	
	private String getTokenFromSession(HttpServletRequest request){
		try{
			if(request.getSession().getAttribute("token") != null){
				JwtToken accessToken = (JwtToken) request.getSession().getAttribute("token");
				return accessToken.getToken();
			}
		}catch(Exception e){
		}
		return null;
	}
	
	private String getTokenFromCookie(HttpServletRequest request){
		String token = null;
		try{
			Cookie[] cookies = request.getCookies();
			for(Cookie cookie: cookies){
				if(cookie.getName().equals("token")){
					token = cookie.getValue();
				}
			}
		} catch(Exception e){
		}
		return token;
		
	}

}
