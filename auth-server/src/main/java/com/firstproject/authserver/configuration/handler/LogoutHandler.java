package com.firstproject.authserver.configuration.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.firstproject.authserver.model.dto.Response;
import com.google.gson.Gson;

public class LogoutHandler implements LogoutSuccessHandler {

	private Gson gson = new Gson();

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		PrintWriter writer = response.getWriter();
        String stringJson = this.gson.toJson(new Response<Object>("Logout Berhasil!", 200, null));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        writer.print(stringJson);
        writer.flush();
	}

}
