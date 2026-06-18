package com.andy.aguacomunidad.controller;
import com.andy.aguacomunidad.dto.PagoRequest;
import com.andy.aguacomunidad.entity.Pago;
import com.andy.aguacomunidad.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pagos")
@CrossOrigin(origins = "http://localhost:4200")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping
    public ResponseEntity<Pago> guardarPago (@Valid @RequestBody PagoRequest pagoRequest){
        Optional<Pago> pagoGuardado = pagoService.guardarPago(pagoRequest);

        if (pagoGuardado.isPresent()){
            return ResponseEntity.ok(pagoGuardado.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Pago> listarPagos(){
        return pagoService.listarPagos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> buscarPagoPorId(@PathVariable Long id){
        Optional<Pago> pagoExistente = pagoService.buscarPagoPorId(id);
        if (pagoExistente.isPresent()){
            return ResponseEntity.ok(pagoExistente.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPagoPorId(@PathVariable Long id){
        Optional<Pago> pagoExistente = pagoService.buscarPagoPorId(id);

        if (pagoExistente.isPresent()){
            pagoService.eliminarPago(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizarPago(@PathVariable Long id, @Valid @RequestBody PagoRequest pagoRequest){
       Optional<Pago> pagoActualizado = pagoService.actualizarPago(id, pagoRequest);

       if (pagoActualizado.isPresent()){
           return ResponseEntity.ok(pagoActualizado.get());
       }

       return ResponseEntity.notFound().build();
    }
}
