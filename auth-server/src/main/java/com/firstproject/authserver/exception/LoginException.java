package com.firstproject.authserver.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
