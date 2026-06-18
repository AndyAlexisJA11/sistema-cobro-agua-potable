export interface CuotaRequest {
  mes: number;
  anio: number;
  monto: number;
  fechaVencimiento: string;
  estadoCuota: string;
  viviendaId: number;
}
