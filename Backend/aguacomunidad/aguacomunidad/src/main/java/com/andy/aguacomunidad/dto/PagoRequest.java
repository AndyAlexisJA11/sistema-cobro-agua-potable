package com.andy.aguacomunidad.dto;

import com.andy.aguacomunidad.enums.MetodoPago;
import jakarta.validation.constraints.DecimalMin;
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
public class PagoRequest {

    @NotNull(message = "Debe ingresar una fecha")
    private LocalDate fechaPago;

    @NotNull(message = "Debe ingredar un monto")
    @DecimalMin(value = "0.01", message = "El monto pagado debe ser mayor que 0")
    private BigDecimal montoPagado;

    @NotNull(message = "Debe ingresar un metodo de pago")
    private MetodoPago metodoPago;

    private String observacion;

    @NotNull(message = "Debe ingresar un id de cuota")
    private Long cuotaId;

}
