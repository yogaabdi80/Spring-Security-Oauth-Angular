package com.firstproject.resourceserver.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "belajar";
	
	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;
	
	@Value("${security.oauth2.authorization.check-token-access}")
	private String checkToken;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		RemoteTokenServices tokenService = new RemoteTokenServices();
		tokenService.setClientId(clientId);
		tokenService.setClientSecret(clientSecret);
		tokenService.setCheckTokenEndpointUrl(checkToken);

		resources.tokenStore(new InMemoryTokenStore()).resourceId(RESOURCE_ID).tokenServices(tokenService);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().disable().authorizeRequests()
        .antMatchers("/api/produk/getAll","/api/produk/getGambarProduk/**","/api/produk/getProduk/**").permitAll()
        .anyRequest().authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//				.and().exceptionHandling().authenticationEntryPoint(new CustomAccessDeniedHandler());
	}
	
}
