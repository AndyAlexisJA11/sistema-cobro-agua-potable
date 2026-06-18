package com.andy.aguacomunidad.service;

import com.andy.aguacomunidad.dto.PagoRequest;
import com.andy.aguacomunidad.entity.Cuota;
import com.andy.aguacomunidad.entity.Pago;
import com.andy.aguacomunidad.enums.EstadoCuota;
import com.andy.aguacomunidad.repository.CuotaRepository;
import com.andy.aguacomunidad.repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final CuotaRepository cuotaRepository;

    public PagoService(PagoRepository pagoRepository, CuotaRepository cuotaRepository) {
        this.pagoRepository = pagoRepository;
        this.cuotaRepository = cuotaRepository;
    }

    public Optional<Pago> guardarPago(PagoRequest pagoRequest) {
        Optional<Cuota> cuotaExistente = cuotaRepository.findById(pagoRequest.getCuotaId());

        if (cuotaExistente.isPresent()) {
            Cuota cuota = cuotaExistente.get();

            if (cuota.getEstado() == EstadoCuota.PAGADA) {
                return Optional.empty();
            }

            if (pagoRequest.getMontoPagado().compareTo(cuota.getMonto()) != 0) {
                return Optional.empty();
            }

            cuota.setEstado(EstadoCuota.PAGADA);
            cuotaRepository.save(cuota);

            Pago pago = new Pago();
            pago.setFechaPago(pagoRequest.getFechaPago());
            pago.setMontoPagado(pagoRequest.getMontoPagado());
            pago.setMetodoPago(pagoRequest.getMetodoPago());
            pago.setObservacion(pagoRequest.getObservacion());
            pago.setCuota(cuota);

            Pago pagoGuardado = pagoRepository.save(pago);
            return Optional.of(pagoGuardado);
        }

        return Optional.empty();
    }

    public List<Pago> listarPagos(){
        return pagoRepository.findAll();
    }

    public Optional<Pago> buscarPagoPorId(Long id){
        return pagoRepository.findById(id);
    }

    public void eliminarPago(Long id) {
        Optional<Pago> pagoExistente = pagoRepository.findById(id);

        if (pagoExistente.isPresent()) {
            Pago pago = pagoExistente.get();
            Cuota cuota = pago.getCuota();

            cuota.setEstado(EstadoCuota.PENDIENTE);
            cuotaRepository.save(cuota);

            pagoRepository.deleteById(id);
        }
    }

    public Optional<Pago> actualizarPago(Long id, PagoRequest pagoRequest) {
        Optional<Cuota> cuotaExistente = cuotaRepository.findById(pagoRequest.getCuotaId());
        Optional<Pago> pagoExistente = pagoRepository.findById(id);

        if (pagoExistente.isPresent() && cuotaExistente.isPresent()) {
            Cuota cuota = cuotaExistente.get();

            if (pagoRequest.getMontoPagado().compareTo(cuota.getMonto()) != 0) {
                return Optional.empty();
            }

            Pago pago = pagoExistente.get();
            pago.setFechaPago(pagoRequest.getFechaPago());
            pago.setMontoPagado(pagoRequest.getMontoPagado());
            pago.setMetodoPago(pagoRequest.getMetodoPago());
            pago.setObservacion(pagoRequest.getObservacion());
            pago.setCuota(cuota);

            Pago pagoActualizado = pagoRepository.save(pago);
            return Optional.of(pagoActualizado);
        }

        return Optional.empty();
    }
}
