package com.salesianostriana.dam.franciscoaguilar_padelbooker.model;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.security.RolUsuario;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@SuppressWarnings("serial")
@Entity
@NoArgsConstructor @AllArgsConstructor
@Builder
@Data
@Table(name="usuarios")
public class Usuario implements UserDetails {

	@Id @GeneratedValue
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	private String email;
	private String telefono;
	private String password;
	
	private RolUsuario rolUsuario;
	
	
	@OneToMany(mappedBy = "usuario")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Reserva> reservas = new ArrayList<>(); 
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rolUsuario.name()));
    }
		
}
