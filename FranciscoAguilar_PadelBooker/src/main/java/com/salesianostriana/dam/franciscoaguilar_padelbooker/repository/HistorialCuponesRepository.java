package com.salesianostriana.dam.franciscoaguilar_padelbooker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.HistorialCupones;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;

@Repository
public interface HistorialCuponesRepository extends JpaRepository<HistorialCupones, Long> {

    boolean existsByUsuarioAndCodigoCupon(Usuario usuario, String codigoCupon);
    
    Optional<HistorialCupones> findByUsuarioAndCodigoCupon(Usuario usuario, String codigoCupon);
    
    boolean existsByCodigoCupon(String codigoCupon);
    
}