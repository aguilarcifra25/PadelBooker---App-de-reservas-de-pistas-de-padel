package com.salesianostriana.dam.franciscoaguilar_padelbooker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ConfigSeguridad {

	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) {

		http.authorizeHttpRequests(
				(authz) -> authz
					
					.requestMatchers("/", "/favicon.ico", "/h2-console/**", "/home", "/login", "/pistas", "/crearUsuario", "/crearUsuario/submit", "/css/**", "/js/**", "/img/**").permitAll()
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
			csrf.ignoringRequestMatchers("/h2-console/**");
		});
		http.headers((headers) -> headers.frameOptions((opts) -> opts.disable()));

		return http.build();
	}
		
}
