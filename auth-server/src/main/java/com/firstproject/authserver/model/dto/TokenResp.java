package com.firstproject.authserver.model.dto;

import lombok.Data;

@Data
public class TokenResp {
	private String access_token;
	private String token_type;
	private String refresh_token;
	private String scope;
	private String jti;
}
