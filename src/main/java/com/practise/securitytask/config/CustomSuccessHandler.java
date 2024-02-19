package com.practise.securitytask.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.practise.securitytask.constants.RoleConstant;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_"+RoleConstant.ADMIN))) {
			response.sendRedirect("/security/api/v1/admin/dashboard");
		}else if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_"+RoleConstant.USER))) {
			response.sendRedirect("/security/api/v1/user/dashboard");
		}else {
			response.sendError(420);
		}
	}

}
