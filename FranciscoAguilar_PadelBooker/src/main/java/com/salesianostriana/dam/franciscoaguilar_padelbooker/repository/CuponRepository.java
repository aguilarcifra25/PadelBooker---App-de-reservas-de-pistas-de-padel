package com.salesianostriana.dam.franciscoaguilar_padelbooker.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Cupon;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;

public interface CuponRepository extends JpaRepository<Cupon, Long>{

	Optional<Cupon> findByCodigo(String codigo);
	
	boolean existsByCodigo(String codigo);
	
	List<Cupon> findByUsuario(Usuario usuario);

    List<Cupon> findByUsuarioAndUsadoFalse(Usuario usuario);

    List<Cupon> findByUsuarioIsNull();

    List<Cupon> findByFechaExpiracionBefore(LocalDate fecha);
}
