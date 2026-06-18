package com.andy.aguacomunidad.dto;

import com.andy.aguacomunidad.enums.EstadoCuota;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CuotaRequest {

    @NotNull(message = "El mes es obligatorio")
    @Min(value = 1, message = "El mes no puede ser menor que 1")
    @Max(value = 12, message = "El mes no puede ser mayor a 12")
    private Integer mes;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1, message = "El año debe ser mayor que 0")
    private Integer anio;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que 0")
    private BigDecimal monto;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDate fechaVencimiento;

    @NotNull(message = "El estado de cuota es obligatoria")
    private EstadoCuota estadoCuota;

    @NotNull(message = "La vivienda es obligatoria")
    private Long viviendaId;

}
