package com.fon.footballfantasy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserPrincipalService userPrincipalService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors()
			.and()
			.csrf().disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilter(new JwtAuthenticationFilter(authenticationManager()))
			.addFilter(new JwtAuthorizationFilter(authenticationManager(), userPrincipalService))
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/login").permitAll()
			.antMatchers(HttpMethod.POST, "/users/register").permitAll()
			.antMatchers(HttpMethod.GET, "/clubs/all").permitAll()
			.antMatchers(HttpMethod.GET, "/clubs/parse-season-clubs").hasAuthority("ROLE_ADMIN")
			.antMatchers(HttpMethod.POST, "/clubs/parse").hasAuthority("ROLE_ADMIN")
			.antMatchers(HttpMethod.DELETE, "/clubs/**").hasAuthority("ROLE_ADMIN")
			.antMatchers(HttpMethod.GET, "/gameweeks/parse-season-gameweeks").hasAuthority("ROLE_ADMIN")
			.antMatchers(HttpMethod.DELETE, "/gameweeks/**").hasAuthority("ROLE_ADMIN")
			.antMatchers("/match-events/parse-match-events/**").hasAuthority("ROLE_ADMIN")
			.antMatchers(HttpMethod.DELETE, "/match-events/**").hasAuthority("ROLE_ADMIN")
			.antMatchers(HttpMethod.DELETE, "/players/**").hasAuthority("ROLE_ADMIN")
			.antMatchers("/performances/calculate/**").hasAuthority("ROLE_ADMIN")
			.antMatchers("/team-performances/calculate/**").hasAuthority("ROLE_ADMIN")
			.anyRequest().authenticated();
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userPrincipalService);
		return daoAuthenticationProvider;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
