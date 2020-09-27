package com.firstproject.authserver.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.core.GrantedAuthority;

public class LoginCustomProvider extends DaoAuthenticationProvider {

	@Autowired
	private UserDetailsService userDetailService;

	public LoginCustomProvider(UserDetailsService userDetailService) {
		this.userDetailService = userDetailService;
		super.setUserDetailsService(this.userDetailService);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		logger.info("authenticate");
		final String name = authentication.getName();
		final String password = authentication.getCredentials().toString();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		UserDetails user = null;
		user = this.userDetailService.loadUserByUsername(name);
		if (name.equals(user.getUsername()) && encoder.matches(password, user.getPassword())) {
			final List<GrantedAuthority> grantedAuths = new ArrayList<>();
			for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
				grantedAuths.add(grantedAuthority);
			}
			final UserDetails principal = new User(name, password, grantedAuths);
			final Authentication auth = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
			return auth;
		} else {
			return null;
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		logger.info("supports");
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
