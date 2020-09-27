package com.firstproject.authserver.configuration.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.firstproject.authserver.model.dto.Response;
import com.google.gson.Gson;

public class FailurHandler implements AuthenticationFailureHandler {
	
	private Gson gson = new Gson();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		PrintWriter writer = response.getWriter();
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String stringJson = this.gson.toJson(new Response<Object>(exception.getMessage(), 99, null));
        writer.print(stringJson);
        writer.flush();
	}

}
