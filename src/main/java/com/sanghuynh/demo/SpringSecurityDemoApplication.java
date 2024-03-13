package com.sanghuynh.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringSecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityDemoApplication.class, args);
		 String rawPassword = "123";
	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        
	        // Mã hóa mật khẩu với salt ngẫu nhiên
	        String encodedPassword = passwordEncoder.encode(rawPassword);
	        
	        System.out.println("Mật khẩu gốc: " + rawPassword);
	        System.out.println("Mật khẩu đã mã hóa: " + encodedPassword);
	    }
	
}
