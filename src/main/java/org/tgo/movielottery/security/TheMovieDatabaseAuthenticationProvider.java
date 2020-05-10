package org.tgo.movielottery.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class TheMovieDatabaseAuthenticationProvider implements AuthenticationProvider {

	
	private TheMovieDatabaseUserDetailService userDetailsService;
	
	public TheMovieDatabaseAuthenticationProvider(TheMovieDatabaseUserDetailService userDetailsService) {
		super();
		this.userDetailsService = userDetailsService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
		
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
