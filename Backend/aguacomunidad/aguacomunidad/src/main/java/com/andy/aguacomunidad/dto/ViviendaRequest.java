package com.andy.aguacomunidad.dto;

import com.andy.aguacomunidad.enums.Barrio;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViviendaRequest {

    @NotNull(message = "El barrio es obligatorio")
    private Barrio barrio;

    @Size(max = 100, message = "La referencia no puede tener mas de 100 caracteres")
    private String referencia;

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;
}
