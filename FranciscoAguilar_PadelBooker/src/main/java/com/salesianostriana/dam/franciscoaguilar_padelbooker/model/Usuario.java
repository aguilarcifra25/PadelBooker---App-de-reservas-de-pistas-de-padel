package com.salesianostriana.dam.franciscoaguilar_padelbooker.model;

import java.util.List;
import java.util.ArrayList;

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
@NoArgsConstructor @AllArgsConstructor
@Builder
@Data
public class Usuario {

	@Id @GeneratedValue
	private Long id;
	
	private String nombre;
	private String email;
	private String telefono;
	
	@OneToMany(mappedBy = "usuario")
    @Builder.Default
    @ToString.Exclude
    private List<Reserva> reservas = new ArrayList<>(); 
	
}
