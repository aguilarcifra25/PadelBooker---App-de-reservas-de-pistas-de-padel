package com.salesianostriana.dam.franciscoaguilar_padelbooker.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
	
	@OneToMany(mappedBy = "pista")
    @Builder.Default
    @ToString.Exclude
    private List<Asignacion> asignaciones = new ArrayList<>();
	
}
