package com.andy.aguacomunidad.service;

import com.andy.aguacomunidad.dto.UsuarioRequest;
import com.andy.aguacomunidad.entity.Usuario;
import com.andy.aguacomunidad.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario guardarUsuario(UsuarioRequest usuarioRequest){
        Usuario usuario = new Usuario();

        usuario.setNombre(usuarioRequest.getNombre());
        usuario.setApellido(usuarioRequest.getApellido());
        usuario.setDpi(usuarioRequest.getDpi());
        usuario.setTelefono(usuarioRequest.getTelefono());

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id){
        return usuarioRepository.findById(id);
    }

    public void eliminarUsuario(Long id){
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> actualizarUsuario(Long id, UsuarioRequest usuarioRequest){
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isPresent()){
            Usuario usuario = usuarioExistente.get();

            usuario.setNombre(usuarioRequest.getNombre());
            usuario.setApellido(usuarioRequest.getApellido());
            usuario.setTelefono(usuarioRequest.getTelefono());
            usuario.setDpi(usuarioRequest.getDpi());

            Usuario usuarioGuardado = usuarioRepository.save(usuario);
            return Optional.of(usuarioGuardado);
        }

        return Optional.empty();
    }
}
