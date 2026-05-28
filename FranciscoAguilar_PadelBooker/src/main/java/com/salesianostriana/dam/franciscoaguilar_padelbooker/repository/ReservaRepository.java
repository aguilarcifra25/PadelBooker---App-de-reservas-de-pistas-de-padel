package com.salesianostriana.dam.franciscoaguilar_padelbooker.repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long>{

	@Query("SELECT COUNT(a) > 0 FROM Asignacion a WHERE a.pista.numero = :numeroPista " +
		       "AND a.reserva.fecha = :fecha " +
		       "AND a.reserva.codigo != :codigoReservaActual " + 
		       "AND (:horaEntrada < a.reserva.horaSalida AND :horaSalida > a.reserva.horaEntrada)")
		boolean tieneConflictoEdicionHorario(
		    @Param("numeroPista") Long numeroPista, 
		    @Param("fecha") LocalDate fecha, 
		    @Param("horaEntrada") LocalTime horaEntrada, 
		    @Param("horaSalida") LocalTime horaSalida, 
		    @Param("codigoReservaActual") Long codigoReservaActual
		);
	
	
}
