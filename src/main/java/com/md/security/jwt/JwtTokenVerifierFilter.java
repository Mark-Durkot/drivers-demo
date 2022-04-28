package com.md.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;


public class JwtTokenVerifierFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
									FilterChain filterChain) throws ServletException, IOException {
		
		if (request.getServletPath().equals("/login")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String access_token = request.getHeader("Authorization");
		
		if (access_token == null || !access_token.startsWith("Bearer ")) {
			response.sendError(403, "Provide correct auth token");
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
