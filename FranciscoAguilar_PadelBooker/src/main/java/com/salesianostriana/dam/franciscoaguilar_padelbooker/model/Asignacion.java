package com.salesianostriana.dam.franciscoaguilar_padelbooker.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Builder
@Data
public class Asignacion {

	@EmbeddedId
	private AsignacionPK asignacionPK;
	
	private boolean usaLuz;
	
	@Min(value = 0, message = "La cantidad de palas no puede ser un número negativo")
    @Max(value = 4, message = "No se pueden alquilar más de 4 raquetas por pista")
    private int cantPalas;
		
	@PositiveOrZero(message = "El precio de la asignación de la pista debe ser cero o positivo")
    private double precio;
		
    private String observaciones;
	
    // -- Asociaciones --
    
    @ManyToOne
    @MapsId("reserva_id")
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_asignacion_reserva"))
    @NotNull(message = "La asignación debe estar vinculada obligatoriamente a una reserva.")
    private Reserva reserva;
    
    @ManyToOne
    @MapsId("pista_id")
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_asignacion_pista"))
    @NotNull(message = "La asignación debe estar vinculada obligatoriamente a una pista.")
    private Pista pista;
    
    // -- Helpers --
    
    public void agregarEnPista(Pista p) {
		p.getAsignaciones().add(this);
		this.pista = p;
	}

	public void eliminarDePista(Pista p) {
		p.getAsignaciones().remove(this);
		this.pista = null;
	}
    
	
	
	public void agregarEnReserva(Reserva r) {
		r.getAsignaciones().add(this);
		this.reserva = r;
	}

	public void eliminarDeReserva(Reserva r) {
		r.getAsignaciones().remove(this);
		this.reserva = null;
	}
    
}
