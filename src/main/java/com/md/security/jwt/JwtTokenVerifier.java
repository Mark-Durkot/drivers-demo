package com.md.security.jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtTokenVerifier {

	public static Authentication verifyToken(String access_token) throws Exception {
		
		access_token = access_token.substring("Bearer ".length());
		
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJwt = verifier.verify(access_token);
		
		String username = decodedJwt.getSubject();
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		List<String> roles = decodedJwt.getClaim("roles").asList(String.class);
		roles.stream().map(role -> authorities.add(new SimpleGrantedAuthority(role))).collect(Collectors.toList());
		
		return new UsernamePasswordAuthenticationToken(username, null, authorities);
	}
	
}
