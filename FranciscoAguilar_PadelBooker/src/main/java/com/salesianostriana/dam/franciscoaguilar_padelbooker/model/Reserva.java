package com.salesianostriana.dam.franciscoaguilar_padelbooker.model;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Reserva {

	@Id @GeneratedValue
	private Long codigo;
	
	private Date fecha;
	private Duration duracion; 
	private double precioTotal;
	
	@OneToMany(mappedBy = "reserva")
	@Builder.Default
    @ToString.Exclude
    private List<Asignacion> asignaciones = new ArrayList<>();

	@ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_reserva_usuario"))
    private Usuario usuario;
	
}