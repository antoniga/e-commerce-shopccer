package com.shopccer.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.shopccer.admin.service.impl.ShopccerUserDetailsServiceImpl;

@Configuration
public class WebSecurityConfig{
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new ShopccerUserDetailsServiceImpl();
	}
	
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    	authenticationProvider.setUserDetailsService(userDetailsService());
    	authenticationProvider.setPasswordEncoder(passwordEncoder());
    	
    	return authenticationProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// tokenValiditySeconds(7 * 24 * 60 * 60) mantenemos sesion durante una semana, expresado en segundos
		//http.authorizeHttpRequests().anyRequest().permitAll();
		http.authorizeHttpRequests()
			.requestMatchers("/usuarios/**","/ajustes/**").hasAuthority("Super-Admin")
			.requestMatchers("/marcas/**").hasAnyAuthority("Super-Admin","Admin")
			.requestMatchers("/superficies/**").hasAnyAuthority("Super-Admin","Admin")
			.requestMatchers("/productos/**").hasAnyAuthority("Super-Admin","Admin")
			.anyRequest().authenticated().and()
			.formLogin().loginPage("/login").usernameParameter("email").permitAll().and()
			.logout().permitAll().and()
			.rememberMe().key("klfsSDFWEfoskdg3294825lksdf").tokenValiditySeconds(7 * 24 * 60 * 60);
		
		return http.build();
	}
	
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/css/**", "/webjars/**","/fontawesome/**","/webfonts/**");
    }

}
