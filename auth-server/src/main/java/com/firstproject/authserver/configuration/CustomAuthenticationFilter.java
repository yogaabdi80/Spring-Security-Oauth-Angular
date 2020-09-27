package com.firstproject.authserver.configuration;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.firstproject.authserver.exception.LoginException;
import com.firstproject.authserver.model.entity.User;
import com.firstproject.authserver.repository.UserRepository;
import com.firstproject.authserver.service.CustomUserDetailService;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private static final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	private Pattern pattern = Pattern.compile(regex);

	@Autowired
	private CustomUserDetailService customService;

	@Autowired
	private UserRepository repo;

	private User user;

	public CustomAuthenticationFilter(String url) {
		super(new AntPathRequestMatcher(url, HttpMethod.POST.name()));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		logger.info("attemptAuthentication");
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request);
		return this.getAuthenticationManager().authenticate(authRequest);

	}

	private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (pattern.matcher(username).matches()) {
			try {
				user = repo.findByEmail(username);
				username = user.getUsername();
			} catch (Exception e) {
				throw new LoginException("Username atau Email tidak terdaftar!");
			}
		}
		UserDetails user = this.customService.loadUserByUsername(username);
		if (!encoder.matches(password, user.getPassword())) {
			throw new LoginException("Password tidak sesuai!");
		}
		username = username.trim();
		if(!user.isEnabled()) {
			throw new LoginException("Akun Belum Terverifikasi!");
		}
		return new UsernamePasswordAuthenticationToken(username, password);
	}

	public void setUserDetailService(CustomUserDetailService userDetailService) {
		this.customService = userDetailService;
	}

}