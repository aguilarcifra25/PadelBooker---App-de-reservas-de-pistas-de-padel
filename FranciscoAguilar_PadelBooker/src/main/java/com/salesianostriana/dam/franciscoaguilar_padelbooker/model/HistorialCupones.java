package com.salesianostriana.dam.franciscoaguilar_padelbooker.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor@AllArgsConstructor
@Builder
@Data
public class HistorialCupones {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String codigoCupon;

    private LocalDateTime fechaUso;
    
    
    public HistorialCupones(Usuario usuario, String codigoCupon) {
        this.usuario = usuario;
        this.codigoCupon = codigoCupon;
    }
    
}
