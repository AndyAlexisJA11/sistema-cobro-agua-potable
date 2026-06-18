package com.andy.aguacomunidad.repository;

import com.andy.aguacomunidad.entity.Vivienda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ViviendaRepository extends JpaRepository<Vivienda,Long> {
    boolean existsByUsuarioId(Long usuarioId);
    Optional<Vivienda> findByUsuarioId(Long usuarioId);
}
