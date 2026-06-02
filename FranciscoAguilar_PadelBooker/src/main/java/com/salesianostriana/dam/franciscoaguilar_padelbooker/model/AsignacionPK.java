package com.salesianostriana.dam.franciscoaguilar_padelbooker.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Embeddable
public class AsignacionPK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long pista_id;
	private long reserva_id;
}
