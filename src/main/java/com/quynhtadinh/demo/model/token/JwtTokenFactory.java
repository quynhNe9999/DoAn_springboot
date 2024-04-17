package com.quynhtadinh.demo.model.token;

import java.util.stream.Collectors;

import com.quynhtadinh.demo.config.JwtSettings;
import com.quynhtadinh.demo.model.UserContext;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
            throw new IllegalArgumentException("Không thể tạo mã thông báo JWT mà không có tên người dùng");
		if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty()) 
            throw new IllegalArgumentException("Người dùng không có bất kỳ đặc quyền nào");
		
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
