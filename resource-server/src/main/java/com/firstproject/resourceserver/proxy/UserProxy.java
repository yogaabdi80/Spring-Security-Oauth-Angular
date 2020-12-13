package com.firstproject.resourceserver.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.firstproject.resourceserver.model.dto.ApiResponse;
import com.firstproject.resourceserver.model.dto.UserDto;

import feign.Headers;

@FeignClient(name = "authProxy", url = "http://localhost:8081/auth/server/user")
@Headers("Accept: application/json")
public interface UserProxy {

	@PostMapping("/register")
	public ApiResponse<Object> register(@RequestBody UserDto userDto);
	
	@PostMapping("/verify/email")
	public ApiResponse<Object> verifyEmail(@RequestParam String id);
	
	@PostMapping("/forgot-password")
	public ApiResponse<Object> forgotPassword(@RequestParam String email);
	
	@PostMapping("/change-password")
	public ApiResponse<Object> changePassword(@RequestParam String id,@RequestParam String password);
	
}
