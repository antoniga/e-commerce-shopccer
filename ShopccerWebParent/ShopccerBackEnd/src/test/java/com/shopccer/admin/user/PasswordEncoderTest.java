package com.shopccer.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
	
	@Test
	public void testEnconderPassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String pwd = "pwd1234";
		String encodedPwd = passwordEncoder.encode(pwd);
		
		System.out.println(encodedPwd);
		
		assertThat(passwordEncoder.matches(pwd, encodedPwd)).isTrue();
	}

}
