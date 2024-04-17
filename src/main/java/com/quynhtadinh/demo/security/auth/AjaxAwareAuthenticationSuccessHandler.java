package com.quynhtadinh.demo.security.auth;

import java.io.IOException;

import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.quynhtadinh.demo.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quynhtadinh.demo.entity.RoleName;
import com.quynhtadinh.demo.model.token.JwtToken;
import com.quynhtadinh.demo.model.token.JwtTokenFactory;

@Component
public class AjaxAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	//private final ObjectMapper mapper;
    private final JwtTokenFactory tokenFactory;

    @Autowired
    public AjaxAwareAuthenticationSuccessHandler(final ObjectMapper mapper, final JwtTokenFactory tokenFactory) {
        //this.mapper = mapper;
        this.tokenFactory = tokenFactory;
    }
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		UserContext userContext = (UserContext) authentication.getPrincipal();
		JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);
		
		/*Cookie ck = new Cookie("token", accessToken.getToken());
		response.addCookie(ck);*/
		
		clearAuthenticationAttributes(request);
		
		HttpSession session = request.getSession();
        session.setAttribute("token", accessToken);
        
		/*response to client if i make a webservice
		 * Map<String, String> tokenMap = new HashMap<String, String>();
        tokenMap.put("token", accessToken.getToken());

        response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
        mapper.writeValue(response.getWriter(), tokenMap);*/

        response.sendRedirect(getMainURLByRole(userContext));
        
	}
	


	protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        session.removeAttribute("token");
    }
	
	private String getMainURLByRole(UserContext userContext){
		String url;
		for(GrantedAuthority elem: userContext.getAuthorities()){
			url = elem.getAuthority();
			if(url == RoleName.ROLE_ADMIN.name()) return "/admin";
			if(url == RoleName.ROLE_USER.name()) return "/user";
			if(url == RoleName.ROLE_STAFF.name()) return "/staff";
		};
		
		return "/login";
	}
	
	

}
