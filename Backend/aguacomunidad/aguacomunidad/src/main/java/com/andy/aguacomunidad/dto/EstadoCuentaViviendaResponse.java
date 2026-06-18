package com.andy.aguacomunidad.dto;
import com.andy.aguacomunidad.entity.Cuota;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoCuentaViviendaResponse {

    private Long viviendaId;
    private String barrio;
    private String referencia;
    private String nombreUsuario;
    private String apellidoUsuario;
    private List<Cuota> cuotas;
    private BigDecimal totalPagado;
    private BigDecimal totalPendiente;
}
