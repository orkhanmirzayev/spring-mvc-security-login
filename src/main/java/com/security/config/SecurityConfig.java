package com.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserDetailsService userDetailsService;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	http
		.formLogin()
		.loginPage("/login")
		.successHandler(new AuthenticationSuccessHandler() {

			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication arg2) throws IOException, ServletException {
				redirectStrategy.sendRedirect(request, response, "/home");
				
			}})
		.and()
		.authorizeRequests()
        .antMatchers("/home","/index","/login","/resources/**","/person/**","/test").permitAll()
        .antMatchers("/admin/**").hasRole("ADMIN")
        .anyRequest().authenticated();
	}
}
