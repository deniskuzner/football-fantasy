package com.fon.footballfantasy.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private UserPrincipalService userPrincipalService;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
			UserPrincipalService userPrincipalService) {
		super(authenticationManager);
		this.userPrincipalService = userPrincipalService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(JwtProperties.HEADER_STRING);

		if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		Authentication authentication = getUsernamePasswordAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		chain.doFilter(request, response);
	}

	private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
		String token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

		DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes())).build().verify(token);
		String username = decodedJWT.getSubject();

		if (username != null) {
			UserPrincipal principal = (UserPrincipal) userPrincipalService.loadUserByUsername(username);
			Authentication auth = new UsernamePasswordAuthenticationToken(username, null, principal.getAuthorities());
			return auth;
		}
		return null;
	}

}
