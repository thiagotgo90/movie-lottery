package org.tgo.movielottery.movie;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.tgo.movielottery.authentication.UserAuthentication;
import org.tgo.movielottery.authentication.UserAuthenticationRepository;
import org.tgo.movielottery.properties.ApiProperties;

import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.methods.TmdbAccount;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.results.ResultList;
import com.omertron.themoviedbapi.tools.HttpTools;

@RestController
@RequestMapping("movie")
public class MovieController {


	@Autowired
	public MovieController(ImdbService imdbService, UserAuthenticationRepository userAuthenticationRepository, ApiProperties apiProperties) {
		this.imdbService = imdbService;
		this.userAuthenticationRepository = userAuthenticationRepository;
		tmdbAccount = new TmdbAccount(apiProperties.getTmdb().getKey(), new HttpTools(HttpClientBuilder.create().build()));
	}

	private TmdbAccount tmdbAccount;
	private ImdbService imdbService;
	private UserAuthenticationRepository userAuthenticationRepository;

	@GetMapping("random")
	public ResponseEntity<?> randomMovieFromMyList(@AuthenticationPrincipal Authentication authentication) throws MovieDbException, RestClientException, URISyntaxException, UnsupportedEncodingException {

		String username = ((User) authentication.getPrincipal()).getUsername();
		UserAuthentication userAuthentication = userAuthenticationRepository.findByUserLogin(username);
		
		ResultList<MovieBasic> watchListMovie = tmdbAccount.getWatchListMovie(userAuthentication.getSessionid(), userAuthentication.getAccountId(), 1, null, null);
		
		
		ImdbMovie movie = imdbService.getMovie(watchListMovie.getResults().get(0).getTitle());
		
		return ResponseEntity.ok(movie);
	}

}
