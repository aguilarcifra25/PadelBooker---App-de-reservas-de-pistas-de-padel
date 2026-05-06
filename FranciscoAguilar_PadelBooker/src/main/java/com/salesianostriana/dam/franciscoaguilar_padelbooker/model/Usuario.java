package com.salesianostriana.dam.franciscoaguilar_padelbooker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Builder
@Data
public class Usuario {

	@Id @GeneratedValue
	private Long id;
	
	private String nombre;
	private String email;
	private String telefono;
	
}
