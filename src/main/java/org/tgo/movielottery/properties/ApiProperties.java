package org.tgo.movielottery.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@PropertySource("classpath:/api.properties")
@Configuration
@ConfigurationProperties("api")
public class ApiProperties {

	private Tmdb tmdb = new Tmdb();
	private Omdb omdb = new Omdb();

	@Data
	public static class Tmdb {
		private String key;
	}
	
	@Data
	public static class Omdb {
		private String key;
	}

}
