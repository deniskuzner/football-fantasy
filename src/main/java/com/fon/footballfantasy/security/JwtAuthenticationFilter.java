package com.fon.footballfantasy.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fon.footballfantasy.domain.user.User;
import com.fon.footballfantasy.service.dto.AuthData;
import com.fon.footballfantasy.service.dto.LoginCredentials;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			LoginCredentials credentials = new ObjectMapper().readValue(request.getInputStream(),
					LoginCredentials.class);

			Authentication authenticationToken = new UsernamePasswordAuthenticationToken(credentials.getUsername(),
					credentials.getPassword());

			Authentication auth = authenticationManager.authenticate(authenticationToken);
			return auth;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();
		
		// Create JWT
		String token = JWT.create()
				.withSubject(principal.getUsername())
				.withExpiresAt(Date.valueOf(LocalDate.now().plusDays(JwtProperties.EXPIRATION_TIME)))
				.sign(HMAC512(JwtProperties.SECRET.getBytes()));

		// Add token in response header
		response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);
		
		// Add auth data to response body
		User user = principal.getUser();
		String roles = user.getRoles().stream().map(ur -> ur.getRole().getName()).collect(Collectors.joining(","));
		Long teamId = user.getTeam() == null ? null : user.getTeam().getId();
		AuthData authData = AuthData.builder().userId(user.getId()).teamId(teamId).roles(roles).build();
		response.getWriter().write(new ObjectMapper().writeValueAsString(authData));
	}

}
