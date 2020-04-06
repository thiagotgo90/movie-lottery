package org.tgo.movielottery.authentication;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tgo.movielottery.properties.ApiProperties;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.methods.TmdbAccount;
import com.omertron.themoviedbapi.methods.TmdbAuthentication;
import com.omertron.themoviedbapi.model.account.Account;
import com.omertron.themoviedbapi.model.authentication.TokenAuthorisation;
import com.omertron.themoviedbapi.model.authentication.TokenSession;
import com.omertron.themoviedbapi.tools.HttpTools;

@RestController
public class AuthenticationController {
	

	@Autowired
	public AuthenticationController( UserAuthenticationRepository repository, ApiProperties apiProperties) {
		this.repository = repository;
		tmdbaccount = new TmdbAccount(apiProperties.getTmdb().getKey(), new HttpTools(HttpClientBuilder.create().build()));
		tmdbAuthentication = new TmdbAuthentication(apiProperties.getTmdb().getKey(), new HttpTools(HttpClientBuilder.create().build()));
	}
	
	private UserAuthenticationRepository repository;
	private TmdbAuthentication tmdbAuthentication;
	private TmdbAccount tmdbaccount;

	@GetMapping("authenticate")
	public ResponseEntity<?> authenticate(@AuthenticationPrincipal Authentication authentication) throws MovieDbException {
		
		String username = ((User)authentication.getPrincipal()).getUsername();
		
		UserAuthentication userLogin = repository.findByUserLogin(username);
		
		if(userLogin != null && userLogin.getUserLogin() != null) {
			
			Map<String, String> message = new HashMap<String, String>();
			message.put("message", "User already logged in");
			
			return ResponseEntity.ok(message);
		}
		
		
		TokenAuthorisation authorisationToken = tmdbAuthentication.getAuthorisationToken();
		
		String address = String.format("https://www.themoviedb.org/authenticate/%s", authorisationToken.getRequestToken());
		HttpHeaders location = new HttpHeaders();
		location.setLocation(URI.create(address).normalize());
		
		UserAuthentication userAuthentication = new UserAuthentication();
		userAuthentication.setRequestToken(authorisationToken.getRequestToken());
		userAuthentication.setUserLogin(((User)authentication.getPrincipal()).getUsername());
		
		repository.save(userAuthentication);
		
		return ResponseEntity
				.status(HttpStatus.TEMPORARY_REDIRECT)
				.headers(location)
				.build();
	}
	
	@GetMapping("authenticate/approved")
	public ResponseEntity<?> authenticationApproved(@AuthenticationPrincipal Authentication authentication) throws MovieDbException {
		
		UserAuthentication userAuthentication = repository.findByUserLogin(((User)authentication.getPrincipal()).getUsername());
		TokenAuthorisation tokenAuthorisation = new TokenAuthorisation();
		tokenAuthorisation.setSuccess(true);
		tokenAuthorisation.setRequestToken(userAuthentication.getRequestToken());
		
		TokenSession sessionToken = tmdbAuthentication.getSessionToken(tokenAuthorisation);
		Account account = tmdbaccount.getAccount(sessionToken.getSessionId());
		
		userAuthentication.setSessionid(sessionToken.getSessionId());
		userAuthentication.setAccountId(account.getId());
		
		repository.save(userAuthentication);
		
		Map<String, String> message = new HashMap<String, String>();
		message.put("message", "User login success");
		
		return ResponseEntity.ok(message);
	}

}
