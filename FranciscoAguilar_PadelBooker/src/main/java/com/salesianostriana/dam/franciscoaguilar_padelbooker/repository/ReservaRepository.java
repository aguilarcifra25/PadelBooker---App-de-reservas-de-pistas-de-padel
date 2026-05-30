package com.salesianostriana.dam.franciscoaguilar_padelbooker.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Reserva;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;

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
	
	@Query("SELECT r FROM Reserva r WHERE " +
		       "(:usuario IS NULL OR LOWER(r.usuario.username) LIKE LOWER(CONCAT('%', :usuario, '%'))) AND " +
		       "(:fecha IS NULL OR r.fecha = :fecha) AND " +
		       "(:horaEntrada IS NULL OR r.horaEntrada = :horaEntrada)")
		List<Reserva> buscarConFiltros(
		        @Param("usuario") String usuario,
		        @Param("fecha") LocalDate fecha,
		        @Param("horaEntrada") LocalTime horaEntrada
		);
	
	List<Reserva> findByUsuario(Usuario usuario);
	
}
