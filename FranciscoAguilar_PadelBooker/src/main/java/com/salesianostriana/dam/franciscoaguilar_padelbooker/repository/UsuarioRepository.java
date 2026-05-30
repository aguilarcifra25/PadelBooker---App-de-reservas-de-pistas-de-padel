package com.salesianostriana.dam.franciscoaguilar_padelbooker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.model.Usuario;
import com.salesianostriana.dam.franciscoaguilar_padelbooker.security.RolUsuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByUsername(String nombreUsuario);
	
	List<Usuario> findByRolUsuario(RolUsuario rolUsuario);
	
	@Query("SELECT u FROM Usuario u WHERE u.username != :username AND u.username != 'admin'")
	List<Usuario> findAllExceptYouAndAdmin(@Param("username") String username);

	@Query("SELECT u FROM Usuario u WHERE " +
		       "u.username != 'admin' AND u.username != :logueado AND " +
		       "(:nombre IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
		       "(:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')))")
		List<Usuario> buscarConFiltrosSinRol(@Param("logueado") String logueado,@Param("nombre") String nombre, @Param("email") String email
		);

	@Query("SELECT u FROM Usuario u WHERE " +
	       "u.username != 'admin' AND u.username != :logueado AND " +
	       "(:nombre IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
	       "(:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
	       "u.rolUsuario = :rol")
	List<Usuario> buscarConFiltrosConRol(@Param("logueado") String logueado, @Param("nombre") String nombre,
											@Param("email") String email, @Param("rol") RolUsuario rol
	);
	
	
	Integer countByRolUsuario (RolUsuario rolUsuario);
	
}
