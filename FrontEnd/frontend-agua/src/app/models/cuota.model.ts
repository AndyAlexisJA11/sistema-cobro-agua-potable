export interface Cuota {
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
}
