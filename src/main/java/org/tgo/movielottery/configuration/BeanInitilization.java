package org.tgo.movielottery.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class BeanInitilization {


	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder()
				.additionalMessageConverters(createMappingJacksonHttpMessageConverter())
				.build();
	}
	
	
	private MappingJackson2HttpMessageConverter createMappingJacksonHttpMessageConverter() {
		
		 ObjectMapper objectMapper = new ObjectMapper();
	     objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(objectMapper);
		return converter;
	}

}
