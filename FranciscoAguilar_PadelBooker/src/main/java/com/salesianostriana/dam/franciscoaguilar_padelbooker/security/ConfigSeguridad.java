package com.salesianostriana.dam.franciscoaguilar_padelbooker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
@EnableWebSecurity
public class ConfigSeguridad {

	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) {

		http.authorizeHttpRequests(
				(authz) -> authz
					
					.requestMatchers("/", "/home", "/login", "/pistas", "/css/**", "/js/**", "/img/**").permitAll()
					.anyRequest()
					.authenticated())
					.requestCache(cache -> {
			            HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
			            requestCache.setMatchingRequestParameterName(null);
			            cache.requestCache(requestCache);
			        })
				.formLogin(form -> form
						.loginPage("/login")
						.permitAll()
				);

		// Añadimos esto para poder acceder a la consola de H2
		// con Spring Security habilitado.
		http.csrf((csrf) -> {
			csrf.ignoringRequestMatchers("/h2/**");
		});
		http.headers((headers) -> headers.frameOptions((opts) -> opts.disable()));

		return http.build();
	}
		
}
