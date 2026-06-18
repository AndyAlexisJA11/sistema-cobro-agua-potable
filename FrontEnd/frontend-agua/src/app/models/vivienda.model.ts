export interface Vivienda {
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
}
