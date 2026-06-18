package com.andy.aguacomunidad.repository;
import com.andy.aguacomunidad.entity.Cuota;
import com.andy.aguacomunidad.enums.EstadoCuota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuotaRepository extends JpaRepository<Cuota, Long> {

    List<Cuota> findByEstado(EstadoCuota estado);

    List<Cuota> findByViviendaId(Long viviendaId);

    boolean existsByViviendaIdAndMesAndAnio(Long viviendaId, Integer mes, Integer anio);
}
