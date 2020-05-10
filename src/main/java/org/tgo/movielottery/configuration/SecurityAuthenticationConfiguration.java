package org.tgo.movielottery.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

@Configuration
public class SecurityAuthenticationConfiguration extends WebSecurityConfigurerAdapter {
	
	private PasswordEncoder encoder;

	@Autowired
	public SecurityAuthenticationConfiguration(PasswordEncoder encoder) {
		this.encoder = encoder;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.authenticationProvider(new PreAuthenticatedAuthenticationProvider())
			.inMemoryAuthentication()
			.withUser("thiago@gmail.com").password(encoder.encode("123456")).roles("ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable().authorizeRequests()
        	.antMatchers(HttpMethod.GET,"/index*", "/static/**", "/*.js", "/*.json", "/*.ico").permitAll()
			.antMatchers("/h2/console/**").permitAll()
			.antMatchers("/built/**", "/main.css").permitAll()
			.anyRequest().authenticated().and()
		.formLogin()
			// .loginPage("/index.html")
			// .loginProcessingUrl("/perform_login")
			.defaultSuccessUrl("/",true).and()
			// .failureUrl("/index.html?error=true").and()
		.httpBasic().and()
		.csrf()
			.ignoringAntMatchers("/h2/console/**").and()
		.headers()
			.frameOptions().sameOrigin();
	}
	
	
}
