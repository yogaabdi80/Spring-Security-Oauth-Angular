package com.firstproject.authserver.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.firstproject.authserver.model.entity.User;
import com.firstproject.authserver.repository.UserRepository;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Autowired
	private UserRepository repo;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> additionalInfo = new HashMap<>();
		User user = repo.findByUsername(authentication.getName());
		additionalInfo.put("idUser", user.getId());
		additionalInfo.put("fullname", user.getFullname());
		additionalInfo.put("cart", user.getCart().getId());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		return accessToken;
	}
}
