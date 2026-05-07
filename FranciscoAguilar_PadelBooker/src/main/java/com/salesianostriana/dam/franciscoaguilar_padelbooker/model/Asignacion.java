package com.salesianostriana.dam.franciscoaguilar_padelbooker.model;

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
public class Asignacion {

	@Id @GeneratedValue
	private Long id;
	
	private boolean usaLuz;
    private double precio;
    private String observaciones;
	
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_asignacion_reserva"))
    private Reserva reserva;
    
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_asignacion_pista"))
    private Pista pista;
    
}
