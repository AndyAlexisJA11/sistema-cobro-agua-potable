package com.andy.aguacomunidad.dto;

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
public class GenerarCuotasRequest {
    private Integer mes;
    private Integer anio;
    private BigDecimal monto;
    private LocalDate fechaVencimiento;
}
