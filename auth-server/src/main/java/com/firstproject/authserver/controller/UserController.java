package com.firstproject.authserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.firstproject.authserver.model.dto.Response;
import com.firstproject.authserver.model.dto.TokenResp;
import com.firstproject.authserver.model.dto.UserDto;
import com.firstproject.authserver.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService service;

	@DeleteMapping("/revoke-token/{token}")
	public Response<?> revoke(@PathVariable String token) {
		return service.revoke(token);
	}

	@GetMapping("/clientTokens")
	public List<String> getClientTokens() {
		return service.getClientTokens();
	}

	@PostMapping("/getToken")
	public Response<TokenResp> getToken(@RequestParam String code) {
		return service.getToken(code);
	}

	@PostMapping("/register")
	public Response<Object> register(@RequestBody UserDto userDto) {
		return service.register(userDto);
	}

	@PostMapping("/verify-email")
	public Response<Object> verifyEmail(@RequestParam String id) {
		return service.verifyEmail(Long.parseLong(id));
	}

	@PostMapping("/forgot-password")
	public Response<Object> forgotPassword(@RequestParam String email) {
		return service.forgotPassword(email);
	}

	@PostMapping("/change-password")
	public Response<Object> changePassword(@RequestParam String id, @RequestParam String password,
			@RequestParam String newPassword) {
		return service.changePassword(Long.parseLong(id), password, newPassword);
	}

	@PostMapping("/reset-password")
	public Response<Object> resetPassword(@RequestParam String id, @RequestParam String password) {
		return service.resetPassword(Long.parseLong(id), password);
	}

	@GetMapping("/check-validity-reset-password")
	public Response<Object> checkValidityResetPassword(@RequestParam String id) {
		return service.checkValidityResetPassword(Long.parseLong(id));
	}

}
