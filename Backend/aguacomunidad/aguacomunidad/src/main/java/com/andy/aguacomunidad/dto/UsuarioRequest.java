package com.andy.aguacomunidad.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener mas de 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede tener mas de 100 caracteres")
    private String apellido;

    @NotBlank(message = "El DPI es obligatorio")
    @Size(min = 13, max = 13, message = "El DPI debe tener exactamente 13 caracteres")
    private String dpi;

    @NotBlank(message = "El telefono es obligatorio")
    @Size(min = 8, max = 8, message = "El telefono debe tener exactamente 8 caracteres")
    private String telefono;
}
