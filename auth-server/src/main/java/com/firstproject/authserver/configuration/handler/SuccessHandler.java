package com.firstproject.authserver.configuration.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.firstproject.authserver.model.dto.Response;
import com.google.gson.Gson;

public class SuccessHandler implements AuthenticationSuccessHandler {

	private Gson gson = new Gson();

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws IOException {
		PrintWriter writer = response.getWriter();
		String stringJson = this.gson
				.toJson(new Response<Object>("Login Berhasil!", 200, authentication.getPrincipal()));
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
		writer.print(stringJson);
		writer.flush();
//		System.out.println("/oauth2/authorization/clientauthcode");
	}

}
