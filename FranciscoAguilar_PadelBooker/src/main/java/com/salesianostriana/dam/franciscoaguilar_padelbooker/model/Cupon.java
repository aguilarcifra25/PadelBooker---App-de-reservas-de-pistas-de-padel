package com.salesianostriana.dam.franciscoaguilar_padelbooker.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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
public class Cupon {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "El código del cupón no puede estar vacío.")
    @Size(min = 3, max = 20, message = "El código debe tener entre 3 y 20 caracteres.")
    @Column(unique = true, nullable = false)
    private String codigo;
    
    
    @Min(value = 1, message = "El descuento mínimo debe ser del 1%.")
    @Max(value = 50, message = "El descuento no puede superar el 50%.")
    private int descuento;
        
    private boolean usado;
        
    @NotNull(message = "Debes asignar una fecha de expiración.")
    @FutureOrPresent(message = "La fecha de expiración debe ser de hoy en adelante.")
    private LocalDate fechaExpiracion;

    @Min(value = 1, message = "El uso máximo debe ser al menos de 1.")
    @Column(nullable = true)
    private Integer usoMaximo;

    @NotNull(message = "El contador de uso actual no puede ser nulo.")
    @PositiveOrZero(message = "El uso actual no puede ser un número negativo.")
    @Column(nullable = true)
    private Integer usoActual;

    
    // -- Asociaciones --
    
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_cupon_usuario"), nullable = true)
    private Usuario usuario;
        
    @OneToMany(mappedBy = "cupon")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Reserva> reservas = new ArrayList<>(); 
    
}