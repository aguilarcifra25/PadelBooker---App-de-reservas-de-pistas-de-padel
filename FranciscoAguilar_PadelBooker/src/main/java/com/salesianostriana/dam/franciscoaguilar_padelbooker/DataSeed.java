package com.salesianostriana.dam.franciscoaguilar_padelbooker;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Pista;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.PistaRepository;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.ReservaRepository;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.UsuarioRepository;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.security.RolUsuario;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeed {

	private final PistaRepository pistaRepository;
	private final ReservaRepository reservaRepository;
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder encoder;
	
	@PostConstruct
	public void crearPistas() {
		
		Pista p1 = Pista.builder()
					.tipo("Indoor")
					.suelo("Césped artificial")
					.precioHora(12)
					.build(); 		
		
		Pista p2 = Pista.builder()
					.tipo("Outdoor")
					.suelo("Cemento poroso")
					.precioHora(12)
					.build(); 
		
		Pista p3 = Pista.builder()
					.tipo("Frontón")
					.suelo("Cemento poroso")
					.precioHora(6)
					.build(); 
		
		pistaRepository.save(p1);
		pistaRepository.save(p2);
		pistaRepository.save(p3);
		
	}
	
	@PostConstruct
	public void crearUsers() {
		
		Usuario u1 = Usuario.builder()
						.username("Paco")
						.password(encoder.encode("123"))
						.email("aguilar.cifra25@triana.salesianos.edu")
						.telefono("123 45 67 89")
						.rolUsuario(RolUsuario.USER)
						.build();
		
		Usuario u2 = Usuario.builder()
					.username("Francis")
					.password(encoder.encode("123"))
					.email("francispadel@gmail.com")
					.telefono("321 54 76 98")
					.rolUsuario(RolUsuario.USER)
					.build();
		
		Usuario u3 = Usuario.builder()
					.username("Curro")
					.password(encoder.encode("123"))
					.email("currillopillo@gmail.com")
					.telefono("987 65 43 21")
					.rolUsuario(RolUsuario.USER)
					.build();
		
		Usuario user = Usuario.builder()
					.email("user@user.com")
					.username("user")
					.password(encoder.encode("user"))
					.rolUsuario(RolUsuario.USER)
					.build();
		
		Usuario admin = Usuario.builder()
					.email("admin@admin.com")
					.username("admin")
					.password(encoder.encode("admin"))
					.rolUsuario(RolUsuario.ADMIN)
					.build();
		
		usuarioRepository.save(user);
		usuarioRepository.save(admin);
		
		usuarioRepository.save(u1);
		usuarioRepository.save(u2);
		usuarioRepository.save(u3);
		
	}
	
}
