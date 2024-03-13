package com.sanghuynh.demo.model.token;

import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sanghuynh.demo.config.JwtSettings;
import com.sanghuynh.demo.model.UserContext;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenFactory {

	private final JwtSettings settings;
	
	@Autowired
	public JwtTokenFactory(JwtSettings settings) {
        this.settings = settings;
    }
	
	
	public AccessJwtToken createAccessJwtToken(UserContext userContext){
		if (StringUtils.isBlank(userContext.getUsername())) 
            throw new IllegalArgumentException("Cannot create JWT Token without username");
		if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty()) 
            throw new IllegalArgumentException("User doesn't have any privileges");
		
		Claims claims = Jwts.claims().setSubject(userContext.getUsername());
		claims.put("scopes", userContext.getAuthorities().stream().map(s->s.toString())
				.collect(Collectors.toList()));
		DateTime currentTime = new DateTime();
		
		String token = Jwts.builder()
				.setClaims(claims)
				.setIssuer(settings.getTokenIssuer())
				.setIssuedAt(currentTime.toDate())
				.setExpiration(currentTime.plusMinutes(settings.getTokenExpirationTime()).toDate())
				.signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
			.compact();
		
		return new AccessJwtToken(token, claims);
	}	
	
}
