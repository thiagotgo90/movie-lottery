package org.tgo.movielottery.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tgo.movielottery.authentication.UserAuthentication;
import org.tgo.movielottery.authentication.UserAuthenticationRepository;

@Service
public class TheMovieDatabaseUserDetailService implements UserDetailsService {

	@Autowired
	public TheMovieDatabaseUserDetailService(UserAuthenticationRepository userAuthenticationRepository) {
		this.userAuthenticationRepository = userAuthenticationRepository;
	}

	private UserAuthenticationRepository userAuthenticationRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserAuthentication userAuthentication = userAuthenticationRepository.findByUserLogin(username);
		
		if(userAuthentication == null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(" ", " ", AuthorityUtils.NO_AUTHORITIES);
	}
}
