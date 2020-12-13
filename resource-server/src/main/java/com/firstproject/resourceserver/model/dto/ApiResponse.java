package com.firstproject.resourceserver.model.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
	
	private String message;
	private Integer status;
	private T objek;
	
	public ApiResponse(String message, Integer status, T objek) {
		this.message = message;
		this.status = status;
		this.objek = objek;
	}
}
