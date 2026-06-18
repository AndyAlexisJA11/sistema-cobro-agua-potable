package com.andy.aguacomunidad.entity;

import com.andy.aguacomunidad.enums.Barrio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "viviendas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vivienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Barrio barrio;

    @Column(length = 100)
    private String referencia;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;
}
