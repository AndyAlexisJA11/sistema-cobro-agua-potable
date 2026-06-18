package com.andy.aguacomunidad.controller;
import com.andy.aguacomunidad.dto.UsuarioRequest;
import com.andy.aguacomunidad.entity.Usuario;
import com.andy.aguacomunidad.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listarUsuarios(){
        return usuarioService.listarUsuarios();
    }

    @PostMapping
    public Usuario guardarUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest){
        return usuarioService.guardarUsuario(usuarioRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioService.buscarUsuarioPorId(id);

        if (usuario.isPresent()){
            return ResponseEntity.ok(usuario.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioService.buscarUsuarioPorId(id);

        if (usuario.isPresent()){
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id,@Valid @RequestBody UsuarioRequest usuarioRequest){
        Optional<Usuario> usuarioActualizado = usuarioService.actualizarUsuario(id, usuarioRequest);

        if (usuarioActualizado.isPresent()){
            return ResponseEntity.ok(usuarioActualizado.get());
        }

        return ResponseEntity.notFound().build();
    }
}
