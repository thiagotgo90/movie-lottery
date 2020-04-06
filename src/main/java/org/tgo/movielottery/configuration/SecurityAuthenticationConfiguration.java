package org.tgo.movielottery.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

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
			.inMemoryAuthentication()
			.withUser("thiago@gmail.com").password(encoder.encode("123456")).roles("ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/h2/console/**").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin().and()
		.httpBasic()
		.and().csrf().ignoringAntMatchers("/h2/console/**")
        .and().headers().frameOptions().sameOrigin();
	}
	
	
}
