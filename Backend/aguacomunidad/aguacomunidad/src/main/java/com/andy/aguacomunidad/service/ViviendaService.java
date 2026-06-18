package com.andy.aguacomunidad.service;

import com.andy.aguacomunidad.dto.ViviendaRequest;
import com.andy.aguacomunidad.entity.Usuario;
import com.andy.aguacomunidad.entity.Vivienda;
import com.andy.aguacomunidad.repository.UsuarioRepository;
import com.andy.aguacomunidad.repository.ViviendaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ViviendaService {

    private final ViviendaRepository viviendaRepository;
    private final UsuarioRepository usuarioRepository;

    public ViviendaService(ViviendaRepository viviendaRepository, UsuarioRepository usuarioRepository) {
        this.viviendaRepository = viviendaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Vivienda> guardarVivienda(ViviendaRequest viviendaRequest) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(viviendaRequest.getUsuarioId());

        if (usuarioExistente.isPresent()) {

            if (viviendaRepository.existsByUsuarioId(viviendaRequest.getUsuarioId())) {
                return Optional.empty();
            }

            Vivienda vivienda = new Vivienda();
            vivienda.setUsuario(usuarioExistente.get());
            vivienda.setBarrio(viviendaRequest.getBarrio());
            vivienda.setReferencia(viviendaRequest.getReferencia());

            Vivienda viviendaGuardada = viviendaRepository.save(vivienda);
            return Optional.of(viviendaGuardada);
        }

        return Optional.empty();
    }



    public List<Vivienda> listarViviendas(){
        return viviendaRepository.findAll();
    }

    public Optional<Vivienda> buscarViviendaPorId(Long id){
        return viviendaRepository.findById(id);
    }

    public void eliminarVivienda(Long id){
        viviendaRepository.deleteById(id);
    }

    public Optional<Vivienda> actualizarVivienda(Long id, ViviendaRequest viviendaRequest) {
        Optional<Vivienda> viviendaExistente = viviendaRepository.findById(id);
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(viviendaRequest.getUsuarioId());

        if (viviendaExistente.isPresent() && usuarioExistente.isPresent()) {
            Optional<Vivienda> viviendaDelUsuario = viviendaRepository.findByUsuarioId(viviendaRequest.getUsuarioId());

            if (viviendaDelUsuario.isPresent() && !viviendaDelUsuario.get().getId().equals(id)) {
                return Optional.empty();
            }

            Vivienda vivienda = viviendaExistente.get();
            vivienda.setBarrio(viviendaRequest.getBarrio());
            vivienda.setReferencia(viviendaRequest.getReferencia());
            vivienda.setUsuario(usuarioExistente.get());

            Vivienda viviendaGuardada = viviendaRepository.save(vivienda);
            return Optional.of(viviendaGuardada);
        }

        return Optional.empty();
    }
}





