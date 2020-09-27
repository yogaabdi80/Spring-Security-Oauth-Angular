package com.firstproject.authserver.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.firstproject.authserver.configuration.handler.FailurHandler;
import com.firstproject.authserver.configuration.handler.LogoutHandler;
import com.firstproject.authserver.configuration.handler.SuccessHandler;
import com.firstproject.authserver.service.CustomUserDetailService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	@Qualifier("oauth2authSuccessHandler")
	private AuthenticationSuccessHandler oauth2authSuccessHandler;
	
	@Autowired
	private CustomUserDetailService customService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http	
				.addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers("/user/register","/user/verify/email","/user/forgot-password","/user/change-password").permitAll()
				.anyRequest().authenticated()
				.and().oauth2Login().successHandler(oauth2authSuccessHandler)
				.and().logout().logoutSuccessHandler(new LogoutHandler())
				.and().csrf().disable().httpBasic();
	}

	@Bean
	public AuthenticationProvider authProvider() {
		LoginCustomProvider login = new LoginCustomProvider(userDetailsService());
		return login;
	}
	
	@Bean
	public CustomAuthenticationFilter authenticationFilter() throws Exception {
		CustomAuthenticationFilter filter = new CustomAuthenticationFilter("/rest-login");
		filter.setAuthenticationManager(authenticationManagerBean());
		filter.setAuthenticationSuccessHandler(new SuccessHandler());
		filter.setAuthenticationFailureHandler(new FailurHandler());
		return filter;
	}
	
	@Override
	@Bean
	@Primary
	public UserDetailsService userDetailsService() {
		return customService;
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
}
