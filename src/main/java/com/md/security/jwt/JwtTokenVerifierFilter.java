package com.md.security.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;


public class JwtTokenVerifierFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
									FilterChain filterChain) throws ServletException, IOException {
		
		String access_token = request.getHeader("Authorization");
		
		if (access_token == null || !access_token.startsWith("Bearer ")) {
			response.sendError(403, "Please, provide a valid JWT token");
			return;
		}
		
		try {
			Authentication authentication = JwtTokenVerifier.verifyToken(access_token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception e) {
			response.sendError(403);
			return;
		}
		
		filterChain.doFilter(request, response);
		
	}
	
}
