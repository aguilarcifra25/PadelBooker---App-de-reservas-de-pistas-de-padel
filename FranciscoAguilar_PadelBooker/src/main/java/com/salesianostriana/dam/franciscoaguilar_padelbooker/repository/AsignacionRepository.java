package com.salesianostriana.dam.franciscoaguilar_padelbooker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Asignacion;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.AsignacionPK;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Pista;

public interface AsignacionRepository extends JpaRepository<Asignacion, AsignacionPK>{

	@Query("SELECT a.pista FROM Asignacion a GROUP BY a.pista ORDER BY COUNT(a) DESC")
	Optional<Pista> findMostBookedPista ();
	
}
