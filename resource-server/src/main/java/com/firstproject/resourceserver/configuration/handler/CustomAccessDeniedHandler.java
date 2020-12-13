//package com.firstproject.resourceserver.configuration.handler;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.Serializable;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.access.AccessDeniedHandler;
//
//import com.firstproject.resourceserver.model.dto.ApiResponse;
//import com.google.gson.Gson;
//
//public class CustomAccessDeniedHandler implements AuthenticationEntryPoint, Serializable {
//
//	private Gson gson = new Gson();
//
//	@Override
//	public void commence(HttpServletRequest request, HttpServletResponse response,
//			AuthenticationException authException) throws IOException, ServletException {		
//		PrintWriter writer = response.getWriter();
//		response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        String stringJson = this.gson.toJson(new ApiResponse<Object>(authException.getMessage(), 99, null));
//        writer.print(stringJson);
//        writer.flush();
//	}
//	
//	
//
//}
