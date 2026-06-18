export interface Pago {
  id: number;
  fechaPago: string;
  montoPagado: number;
  metodoPago: string;
  observacion: string;
  cuota: {
    id: number;
    mes: number;
    anio: number;
    monto: number;
    fechaVencimiento: string;
    estado: string;
    vivienda: {
      id: number;
      barrio: string;
      referencia: string;
      usuario: {
        id: number;
        nombre: string;
        apellido: string;
        dpi: string;
        telefono: string;
      };
    };
  };
}
