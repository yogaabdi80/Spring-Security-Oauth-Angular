package com.firstproject.authserver.configuration.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.firstproject.authserver.model.entity.Authorities;
import com.firstproject.authserver.model.entity.Cart;
import com.firstproject.authserver.model.entity.User;
import com.firstproject.authserver.model.entity.UserDetail;
import com.firstproject.authserver.repository.AuthoritiesRepository;
import com.firstproject.authserver.repository.UserDetailRepo;
import com.firstproject.authserver.repository.UserRepository;

@Component("oauth2authSuccessHandler")

public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private UserDetailRepo detailRepo;

	@Autowired
	private AuthoritiesRepository authoritiesRepo;

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		OAuth2AuthenticationToken oToken = (OAuth2AuthenticationToken) authentication;
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String name = oToken.getPrincipal().getAttributes().get("name").toString();
		String email = oToken.getPrincipal().getAttributes().get("email").toString();
		System.out.println(email);
		User user =  repo.findByEmail(email);
		if(user==null) {
			System.out.println(oToken.getAuthorizedClientRegistrationId());
			user = new User(oToken.getName(), encoder.encode(UUID.randomUUID().toString()), true, email,name);
			user = repo.save(user);
			List<Authorities> listAuthorities = new ArrayList<Authorities>();
			listAuthorities.add(authoritiesRepo.findById("r002").get());
			user.setAuthorities(listAuthorities);
			Cart cart = new Cart();
			user=repo.save(user);
			cart.setUserCart(user);
			user.setCart(cart);
			repo.save(user);
			UserDetail detail = new UserDetail();
			detail.setUserDetail(user);
			detailRepo.save(detail);
		}

//		System.out.println("/oauth2/authorization/clientauthcode");

		this.redirectStrategy.sendRedirect(request, response, "/oauth2/authorization/clientauthcode");

	}
}
