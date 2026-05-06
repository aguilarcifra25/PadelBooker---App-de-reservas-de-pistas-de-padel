package com.salesianostriana.dam.franciscoaguilar_padelbooker.model;

import java.time.Duration;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Builder
@Data
public class Reserva {

	@Id @GeneratedValue
	private Long codigo;
	
	private Date fecha;
	private Duration duracion; 
	private double precioTotal;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_reserva_pista"))
    private Pista pista;

}