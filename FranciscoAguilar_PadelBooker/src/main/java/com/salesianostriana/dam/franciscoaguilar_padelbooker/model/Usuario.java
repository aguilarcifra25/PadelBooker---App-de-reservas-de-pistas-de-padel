package com.salesianostriana.dam.franciscoaguilar_padelbooker.model;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.salesianostriana.dam.franciscoaguilar_padelbooker.security.RolUsuario;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
	@NotBlank(message = "El nombre de usuario no puede estar vacío")
	private String username;
	
	@Column(nullable = false, unique = true)
    @NotBlank(message = "El email no puede estar vacío.")
    @Email(message = "El formato del email no es válido.")
	private String email;
	
	@Pattern(regexp = "^(\\+?[0-9]{9,15})?$", message = "El teléfono debe ser un número válido entre 9 y 15 dígitos")
	private String telefono;
		
	@Column(nullable = false)
    @NotBlank(message = "La contraseña no puede estar vacía.")
	private String password;
	
	@NotNull(message = "El rol de usuario es obligatorio.")
    @Enumerated(EnumType.STRING)
	private RolUsuario rolUsuario;
	
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Reserva> reservas = new ArrayList<>(); 
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rolUsuario.name()));
    }
		
}
