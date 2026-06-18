import { Cuota } from './cuota.model';

export interface EstadoCuentaViviendaResponse {
  viviendaId: number;
  barrio: string;
  referencia: string;
  nombreUsuario: string;
  apellidoUsuario: string;
  cuotas: Cuota[];
  totalPagado: number;
  totalPendiente: number;
}
