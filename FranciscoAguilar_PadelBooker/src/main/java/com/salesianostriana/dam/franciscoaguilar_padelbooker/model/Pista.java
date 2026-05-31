package com.salesianostriana.dam.franciscoaguilar_padelbooker.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor@AllArgsConstructor
@Builder
@Data
public class Pista {

	@Id @GeneratedValue
	private Long numero;
	
	@NotBlank(message = "El tipo de pista no puede estar vacío.")
	@Pattern(regexp = "^(Indoor|Outdoor|Frontón|Mini 1vs1)$", message = "El tipo de pista debe ser Indoor, Outdoor, Frontón o Mini 1vs1.")
	private String tipo;
	
	@NotBlank(message = "El tipo de suelo no puede estar vacío.")
	@Pattern(regexp = "^(Césped artificial|Cemento poroso)$", message = "El tipo de suelo debe ser Césped artificial o Cemento poroso.")
	private String suelo;
	
	@NotNull(message = "El precio por hora no puede estar vacío")
	@Min(value = 5, message = "El precio por hora mínimo debe ser de 5.")
	private double precioHora;
	
	@URL(message = "El formato de la imagen debe ser una URL válida")
	private String imagen;
	
	// -- Asociaciones --
	
	@OneToMany(mappedBy = "pista")
    @Builder.Default
    @EqualsAndHashCode.Exclude
	@ToString.Exclude
    private List<Asignacion> asignaciones = new ArrayList<>();
	
}
