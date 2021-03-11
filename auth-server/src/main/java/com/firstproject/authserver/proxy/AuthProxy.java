package com.firstproject.authserver.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.firstproject.authserver.model.dto.TokenResp;

import feign.Headers;

@FeignClient(name = "authProxy", url = "http://localhost:8081/auth/server")
@Headers("Accept: application/json")
public interface AuthProxy {

	@Headers("Content-Type: multipart/form-data")
	@PostMapping("/rest-login")
	public ResponseEntity<String> login(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password);

	@Headers("Content-Type: multipart/form-data")
	@PostMapping("/oauth/token")
	public TokenResp getToken(@RequestHeader(value = "Authorization") String auth,
			@RequestParam(value = "grant_type") String grant_type, @RequestParam(value = "code") String code,
			@RequestParam(value = "redirect_uri") String redirect_uri);

//	@Headers("Content-Type: application/x-www-form-urlencoded")
	@GetMapping("/oauth/authorize")
	public ResponseEntity<String> auth(@RequestParam(value = "response_type") String response_type,
			@RequestParam(value = "redirect_uri") String redirect_uri,
			@RequestParam(value = "client_id") String client_id);

}
