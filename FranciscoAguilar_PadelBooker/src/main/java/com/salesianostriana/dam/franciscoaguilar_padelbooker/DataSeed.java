package com.salesianostriana.dam.franciscoaguilar_padelbooker;

import org.springframework.stereotype.Component;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Pista;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.PistaRepository;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.ReservaRepository;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.repository.UsuarioRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeed {

	private final PistaRepository pistaRepository;
	private final ReservaRepository reservaRepository;
	private final UsuarioRepository usuarioRepository;
	
	@PostConstruct
	public void crearPistas() {
		
		Pista p1 = Pista.builder()
					.tipo("Indoor")
					.suelo("Tierra")
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
						.nombre("Paco")
						.email("aguilar.cifra25@triana.salesianos.edu")
						.telefono("123 45 67 89")
						.build();
		
		Usuario u2 = Usuario.builder()
				.nombre("Francis")
				.email("francispadel@gmail.com")
				.telefono("321 54 76 98")
				.build();
		
		Usuario u3 = Usuario.builder()
				.nombre("Curro")
				.email("currillopillo@gmail.com")
				.telefono("987 65 43 21")
				.build();
		
		usuarioRepository.save(u1);
		usuarioRepository.save(u2);
		usuarioRepository.save(u3);
		
	}
	
}
