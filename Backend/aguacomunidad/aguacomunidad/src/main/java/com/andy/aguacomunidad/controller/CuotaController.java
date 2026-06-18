package com.andy.aguacomunidad.controller;

import com.andy.aguacomunidad.dto.CuotaRequest;
import com.andy.aguacomunidad.dto.EstadoCuentaViviendaResponse;
import com.andy.aguacomunidad.dto.GenerarCuotasRequest;
import com.andy.aguacomunidad.entity.Cuota;
import com.andy.aguacomunidad.service.CuotaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuotas")
@CrossOrigin(origins = "http://localhost:4200")
public class CuotaController {

    private final CuotaService cuotaService;

    public CuotaController(CuotaService cuotaService) {
        this.cuotaService = cuotaService;
    }

    @PostMapping
    public ResponseEntity<Cuota> guardarCuota(@Valid @RequestBody CuotaRequest cuotaRequest){
        Optional<Cuota> cuotaGuardada = cuotaService.guardarCuota(cuotaRequest);

        if (cuotaGuardada.isPresent()) {
            return ResponseEntity.ok(cuotaGuardada.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Cuota> listarCuotas(){
        return cuotaService.listarCuotas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuota> buscarCuotaPorId(@PathVariable Long id){
        Optional<Cuota> cuota = cuotaService.buscarCuotaPorId(id);
        if (cuota.isPresent()) {
            return ResponseEntity.ok(cuota.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuotaPorId(@PathVariable Long id){
        Optional<Cuota> cuotaExistente = cuotaService.buscarCuotaPorId(id);

        if (cuotaExistente.isPresent()) {
            cuotaService.eliminarCuota(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuota> actualizarCuota(@PathVariable Long id, @Valid @RequestBody CuotaRequest cuotaRequest){
        Optional<Cuota> cuotaActualizada = cuotaService.actualizarCuota(id, cuotaRequest);

        if (cuotaActualizada.isPresent()) {
            return ResponseEntity.ok(cuotaActualizada.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/pendientes")
    public List<Cuota> listarCuotasPendientes(){
        return cuotaService.listarCuotasPendientes();
    }

    @GetMapping("vivienda/{viviendaId}")
    public List<Cuota> listarCuotasPorVivienda(@PathVariable Long viviendaId){
        return cuotaService.listarCuotasPorVivienda(viviendaId);
    }

    @GetMapping("/estado-cuenta/vivienda/{viviendaId}")
    public ResponseEntity<EstadoCuentaViviendaResponse> obtenerEstadoDeCuentaPorVivienda(@PathVariable Long viviendaId){
        Optional<EstadoCuentaViviendaResponse> estadoCuenta = cuotaService.obtenerEstadoDeCuentaPorVivienda(viviendaId);

        if (estadoCuenta.isPresent()) {
            return ResponseEntity.ok(estadoCuenta.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/generar")
    public ResponseEntity<List<Cuota>> generarCuotasDelMes(@RequestBody GenerarCuotasRequest request) {
        List<Cuota> cuotasGeneradas = cuotaService.generarCuotasDelMes(request);
        return ResponseEntity.ok(cuotasGeneradas);
    }
}
