package com.firstproject.authserver.model.dto;

import lombok.Data;

@Data
public class Response<T> {
	private String msg;
	private int status;
	private T objek;
	
	public Response(String msg, int status, T objek) {
		this.msg = msg;
		this.status = status;
		this.objek = objek;
	}
}
