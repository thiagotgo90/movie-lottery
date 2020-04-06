package org.tgo.movielottery.movie;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.tgo.movielottery.properties.ApiProperties;

@Service
public class ImdbService {

	private ApiProperties apiProperties;
	private RestTemplate restTemplate;

	@Autowired
	public ImdbService(ApiProperties apiProperties, RestTemplate restTemplate) {
		this.apiProperties = apiProperties;
		this.restTemplate = restTemplate;
	}

	public ImdbMovie getMovie(String title) throws RestClientException, URISyntaxException, UnsupportedEncodingException {
		
		title = URLEncoder.encode(title, StandardCharsets.UTF_8.toString());
		String url = String.format("http://www.omdbapi.com/?t=%s&apikey=%s&type=movie", title, apiProperties.getOmdb().getKey());

		ResponseEntity<ImdbMovie> entity = restTemplate.getForEntity(new URI(url), ImdbMovie.class);
		return entity.getBody();
	}

}
