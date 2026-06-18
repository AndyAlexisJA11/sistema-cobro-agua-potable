package com.andy.aguacomunidad.service;
import com.andy.aguacomunidad.dto.CuotaRequest;
import com.andy.aguacomunidad.dto.EstadoCuentaViviendaResponse;
import com.andy.aguacomunidad.dto.GenerarCuotasRequest;
import com.andy.aguacomunidad.entity.Cuota;
import com.andy.aguacomunidad.entity.Vivienda;
import com.andy.aguacomunidad.enums.EstadoCuota;
import com.andy.aguacomunidad.repository.CuotaRepository;
import com.andy.aguacomunidad.repository.ViviendaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CuotaService {

    private final CuotaRepository cuotaRepository;
    private final ViviendaRepository viviendaRepository;

    public CuotaService(CuotaRepository cuotaRepository, ViviendaRepository viviendaRepository) {
        this.cuotaRepository = cuotaRepository;
        this.viviendaRepository = viviendaRepository;
    }

    public Optional<Cuota> guardarCuota(CuotaRequest cuotaRequest) {
        Optional<Vivienda> viviendaExistente = viviendaRepository.findById(cuotaRequest.getViviendaId());

        if (viviendaExistente.isPresent()) {
            Cuota cuota = new Cuota();

            cuota.setMes(cuotaRequest.getMes());
            cuota.setAnio(cuotaRequest.getAnio());
            cuota.setMonto(cuotaRequest.getMonto());
            cuota.setFechaVencimiento(cuotaRequest.getFechaVencimiento());
            cuota.setEstado(EstadoCuota.PENDIENTE);
            cuota.setVivienda(viviendaExistente.get());

            Cuota cuotaGuardada = cuotaRepository.save(cuota);
            return Optional.of(cuotaGuardada);
        }

        return Optional.empty();
    }

    public List<Cuota> listarCuotas() {
        return cuotaRepository.findAll();
    }

    public Optional<Cuota> buscarCuotaPorId(Long id) {
        return cuotaRepository.findById(id);
    }

    public void eliminarCuota(Long id) {
        cuotaRepository.deleteById(id);
    }

    public Optional<Cuota> actualizarCuota(Long id, CuotaRequest cuotaRequest) {
        Optional<Cuota> cuotaExistente = cuotaRepository.findById(id);
        Optional<Vivienda> viviendaExistente = viviendaRepository.findById(cuotaRequest.getViviendaId());

        if (cuotaExistente.isPresent() && viviendaExistente.isPresent()) {
            Cuota cuota = cuotaExistente.get();

            cuota.setMes(cuotaRequest.getMes());
            cuota.setAnio(cuotaRequest.getAnio());
            cuota.setMonto(cuotaRequest.getMonto());
            cuota.setFechaVencimiento(cuotaRequest.getFechaVencimiento());
            cuota.setVivienda(viviendaExistente.get());

            Cuota cuotaGuardada = cuotaRepository.save(cuota);
            return Optional.of(cuotaGuardada);
        }

        return Optional.empty();
    }

    public List<Cuota> listarCuotasPendientes(){
        return cuotaRepository.findByEstado(EstadoCuota.PENDIENTE);
    }

    public List<Cuota> listarCuotasPorVivienda(Long viviendaId){
        return cuotaRepository.findByViviendaId(viviendaId);
    }

    public Optional<EstadoCuentaViviendaResponse> obtenerEstadoDeCuentaPorVivienda(Long viviendaId){
        Optional<Vivienda> viviendaExistente = viviendaRepository.findById(viviendaId);
        BigDecimal totalPagado = BigDecimal.ZERO;
        BigDecimal totalPendiente = BigDecimal.ZERO;

        if (viviendaExistente.isPresent()) {
            Vivienda vivienda = viviendaExistente.get();
            List<Cuota> cuotas = cuotaRepository.findByViviendaId(viviendaId);

            for (Cuota cuota : cuotas){
                if (cuota.getEstado() == EstadoCuota.PAGADA){
                    totalPagado = totalPagado.add(cuota.getMonto());
                }else {
                    totalPendiente = totalPendiente.add(cuota.getMonto());
                }
            }

            EstadoCuentaViviendaResponse response = new EstadoCuentaViviendaResponse(
                    vivienda.getId(),
                    vivienda.getBarrio().name(),
                    vivienda.getReferencia(),
                    vivienda.getUsuario().getNombre(),
                    vivienda.getUsuario().getApellido(),
                    cuotas,
                    totalPagado,
                    totalPendiente);

            return Optional.of(response);
        }

        return Optional.empty();
    }

    public List<Cuota> generarCuotasDelMes(GenerarCuotasRequest request) {
        List<Vivienda> viviendas = viviendaRepository.findAll();
        List<Cuota> cuotasGeneradas = new ArrayList<>();

        for (Vivienda vivienda : viviendas) {
            boolean yaExiste = cuotaRepository.existsByViviendaIdAndMesAndAnio(
                    vivienda.getId(),
                    request.getMes(),
                    request.getAnio()
            );

            if (!yaExiste) {
                Cuota cuota = new Cuota();
                cuota.setMes(request.getMes());
                cuota.setAnio(request.getAnio());
                cuota.setMonto(request.getMonto());
                cuota.setFechaVencimiento(YearMonth.of(request.getAnio(), request.getMes()).atEndOfMonth());
                cuota.setEstado(EstadoCuota.PENDIENTE);
                cuota.setVivienda(vivienda);

                Cuota cuotaGuardada = cuotaRepository.save(cuota);
                cuotasGeneradas.add(cuotaGuardada);
            }
        }

        return cuotasGeneradas;
    }
}


