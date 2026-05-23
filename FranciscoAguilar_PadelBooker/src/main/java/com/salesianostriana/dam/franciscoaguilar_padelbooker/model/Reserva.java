package com.salesianostriana.dam.franciscoaguilar_padelbooker.model;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Builder
@Data
public class Reserva {

	@Id @GeneratedValue
	private Long codigo;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(nullable = false)
    @NotNull(message = "La fecha de la reserva es obligatoria.")
    @FutureOrPresent(message = "La fecha de la reserva no puede ser anterior a hoy.")
	private Date fecha;
	
	@Column(nullable = false)
    @NotNull(message = "La hora de entrada es obligatoria.")
    private LocalTime horaEntrada;
    
    @Column(nullable = false)
    @NotNull(message = "La hora de salida es obligatoria.")
    private LocalTime horaSalida;
    
    @Column(nullable = false)
    @PositiveOrZero(message = "El precio total no puede ser negativo")
    private double precioTotal;
	
	@OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
	@Builder.Default
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Asignacion> asignaciones = new ArrayList<>();

	@ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_reserva_usuario"))
    private Usuario usuario;
	
}