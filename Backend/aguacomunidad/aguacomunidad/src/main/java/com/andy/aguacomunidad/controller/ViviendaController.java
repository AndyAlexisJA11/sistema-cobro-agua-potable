package com.andy.aguacomunidad.controller;
import com.andy.aguacomunidad.dto.ViviendaRequest;
import com.andy.aguacomunidad.entity.Vivienda;
import com.andy.aguacomunidad.service.ViviendaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/viviendas")
@CrossOrigin(origins = "http://localhost:4200")
public class ViviendaController {
    private final ViviendaService viviendaService;

    public ViviendaController(ViviendaService viviendaService) {
        this.viviendaService = viviendaService;
    }

    @PostMapping
    public ResponseEntity<Vivienda> guardarVivienda(@Valid @RequestBody ViviendaRequest viviendaRequest){
        Optional<Vivienda> viviendaGuardada = viviendaService.guardarVivienda(viviendaRequest);

        if (viviendaGuardada.isPresent()){
            return ResponseEntity.ok(viviendaGuardada.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Vivienda> listarViviendas(){
        return viviendaService.listarViviendas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vivienda> buscarViviendaPorId(@PathVariable Long id){
        Optional<Vivienda> vivienda = viviendaService.buscarViviendaPorId(id);

        if (vivienda.isPresent()){
            return ResponseEntity.ok(vivienda.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarViviendaPorId(@PathVariable Long id){
        Optional<Vivienda> viviendaExistente = viviendaService.buscarViviendaPorId(id);

        if (viviendaExistente.isPresent()){
            viviendaService.eliminarVivienda(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vivienda> actualizarVivienda (@PathVariable Long id,@Valid @RequestBody ViviendaRequest viviendaRequest){
        Optional<Vivienda> viviendaActualizada = viviendaService.actualizarVivienda(id, viviendaRequest);

        if (viviendaActualizada.isPresent()){
            return ResponseEntity.ok(viviendaActualizada.get());
        }

        return ResponseEntity.notFound().build();
    }
}
