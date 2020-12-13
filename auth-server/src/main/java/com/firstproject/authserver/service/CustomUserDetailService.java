package com.firstproject.authserver.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.firstproject.authserver.exception.LoginException;
import com.firstproject.authserver.model.dto.MyUserPrincipal;
import com.firstproject.authserver.model.entity.User;
import com.firstproject.authserver.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userDetailRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = new User();
		List<String> authoritiesDb = new ArrayList<String>();
		user = userDetailRepo.findByUsername(username);
		if (user != null) {
			authoritiesDb = userDetailRepo.getAuthorities(user.getUsername());
		} else {
			throw new UsernameNotFoundException("Username atau Email tidak terdaftar!");
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : authoritiesDb) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		MyUserPrincipal userDetails = new MyUserPrincipal(user.getUsername(), user.getPassword(), user.getEnabled(),
				true, true, true, authorities);
		return userDetails;
	}

}
