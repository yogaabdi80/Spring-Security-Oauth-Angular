package com.firstproject.authserver.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.firstproject.authserver.model.dto.Response;
import com.firstproject.authserver.model.dto.TokenResp;
import com.firstproject.authserver.model.dto.UserDto;
import com.firstproject.authserver.proxy.AuthProxy;
import com.firstproject.authserver.service.EmailSenderService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class ApiController {

	@Autowired
	private AuthProxy proxy;
	
	@Autowired
	private EmailSenderService service;
	
	private Gson gson = new Gson();

	@PostMapping()
	public Response<?> login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) throws IOException {
		
		ResponseEntity<String> authenticationResponse = proxy.login(username, password);
		String obj = authenticationResponse.getBody();
		
		Response<?> res = gson.fromJson(obj, Response.class);
		if(res.getStatus()==200) {
			response.sendRedirect("/auth/server/oauth/authorize?client_id=clientauthcode&response_type=code&redirect_uri=http://localhost:8081/auth/server/user/getToken");
		}
		return res;
	}

	@PostMapping("/getToken")
	public Response<TokenResp> getToken(@RequestParam String code) {
		TokenResp authenticationResponse = null;
		try {
			authenticationResponse = proxy.getToken("Basic Y2xpZW50YXV0aGNvZGU6YWJjZA==",
					"authorization_code", code, "http://localhost:4200/dashboard");
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<TokenResp>("Token Gagal Diterima!",99,authenticationResponse);
		}
		return new Response<TokenResp>("Token Berhasil Diterima!",200,authenticationResponse);
	}
	
	@PostMapping("/register")
	public Response<Object> register(@RequestBody UserDto userDto) {
		return service.register(userDto);
	}
	
	@PostMapping("/verify/email")
	public Response<Object> verifyEmail(@RequestParam String id) {
		return service.verifyEmail(Long.parseLong(id));
	}
	
	@PostMapping("/forgot-password")
	public Response<Object> forgotPassword(@RequestParam String email) {
		return service.forgotPassword(email);
	}
	
	@PostMapping("/change-password")
	public Response<Object> changePassword(@RequestParam String id,@RequestParam String password) {
		return service.resetPassword(Long.parseLong(id), password);
	}

}
