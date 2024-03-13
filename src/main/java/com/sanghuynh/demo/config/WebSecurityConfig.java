package com.sanghuynh.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanghuynh.demo.security.JwtAuthenticationTokenFilter;
import com.sanghuynh.demo.security.RestAuthenticationEntryPoint;
import com.sanghuynh.demo.security.auth.AjaxAuthenticationProvider;
import com.sanghuynh.demo.security.auth.AjaxLoginProcessingFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";
	public static final String FORM_BASED_LOGIN_ENTRY_POINT = "/auth";
	public static final String AUTH_MAIN = "/";
	
	@Autowired private RestAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired private AuthenticationSuccessHandler successHandler;
    @Autowired private AuthenticationFailureHandler failureHandler;
    @Autowired private AjaxAuthenticationProvider ajaxAuthenticationProvider;
   
    
    @Autowired private AuthenticationManager authenticationManager;
    
    @Autowired private ObjectMapper objectMapper;
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
    protected AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter() throws Exception {
        AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(FORM_BASED_LOGIN_ENTRY_POINT, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }
      
    /*protected JwtAuthenticationTokenFilter buildJwtAuthenticationTokenFilter() throws Exception {
    	JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter(AUTH_MAIN, failureHandler, tokenExtractor);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }*/
    
    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
     
    	return new JwtAuthenticationTokenFilter();
    }
     
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(ajaxAuthenticationProvider);
    }
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http
	        .csrf().disable() // We don't need CSRF for JWT based authentication
	        .exceptionHandling()
	        .authenticationEntryPoint(this.authenticationEntryPoint)
	        
	        .and()
            .sessionManagement() // don't create session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            
            .and()
            .authorizeRequests()
        	// allow anonymous resource requests
            .antMatchers(
                    HttpMethod.GET,
                    "/*.html",
                    "/favicon.ico",
                    "/**/*.html",
                    "/**/*.css",
                    "/**/*.js",
                    "/"
            ).permitAll()
            
            .antMatchers(FORM_BASED_LOGIN_ENTRY_POINT).permitAll() // Login end-point
            .antMatchers("/register").permitAll()
            .antMatchers("/login").permitAll()
            .antMatchers("/error").permitAll()
            
            .and()
            .authorizeRequests()
            .antMatchers("/user/**").hasRole("USER")
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/staff/**").hasRole("STAFF")
            .anyRequest().authenticated()
            
            
            .and()
        .exceptionHandling()
            .accessDeniedPage("/403");
		 
		//remember me configuration
		 /*http.rememberMe(). 
		 	key("rem-me-key").
		 	rememberMeParameter("remember-me-param").
		 	rememberMeCookieName("my-remember-me").
		 	tokenValiditySeconds(86400);*/
//		 Custom base security LoginFilter
		 http 
            .addFilterBefore(buildAjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
            
		 	 	
	}
	
}
