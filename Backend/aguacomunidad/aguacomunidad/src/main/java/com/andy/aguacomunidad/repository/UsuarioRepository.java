package com.andy.aguacomunidad.repository;

import com.andy.aguacomunidad.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
