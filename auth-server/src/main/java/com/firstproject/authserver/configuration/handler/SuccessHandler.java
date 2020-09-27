package com.firstproject.authserver.configuration.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class SuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws IOException {
		response.sendRedirect("/auth/server/oauth/authorize?client_id=clientauthcode&response_type=code&redirect_uri=http://localhost:8081/auth/server/user/getToken");
	}

}
