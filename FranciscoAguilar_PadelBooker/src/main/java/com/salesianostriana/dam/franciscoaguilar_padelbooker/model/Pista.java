package com.salesianostriana.dam.franciscoaguilar_padelbooker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor@AllArgsConstructor
@Builder
@Data
public class Pista {

	@Id @GeneratedValue
	private Long numero;
	
	private String tipo;
	private double precioHora;
	private String imagen;
	
}
