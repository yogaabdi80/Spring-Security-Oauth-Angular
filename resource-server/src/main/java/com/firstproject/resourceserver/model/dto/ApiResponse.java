package com.firstproject.resourceserver.model.dto;

import lombok.Data;

@Data
public class ApiResponse {
	
	private String message;
	private Integer status;
	
	public ApiResponse(String message, Integer status) {
		this.message = message;
		this.status = status;
	}
}
