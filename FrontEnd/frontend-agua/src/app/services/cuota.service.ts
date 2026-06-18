import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cuota } from '../models/cuota.model';
import { CuotaRequest } from '../models/cuota-request.model';
import { GenerarCuotasRequest } from '../models/generar-cuotas-request.model';
import { EstadoCuentaViviendaResponse } from '../models/cuenta-vivienda-response.model';

@Injectable({
  providedIn: 'root'
})
export class CuotaService {

  private apiUrl = 'http://localhost:8080/cuotas';

  constructor(private http: HttpClient) {}

  listarCuotas(): Observable<Cuota[]> {
    return this.http.get<Cuota[]>(this.apiUrl);
  }

  guardarCuota(cuotaRequest: CuotaRequest): Observable<Cuota> {
    return this.http.post<Cuota>(this.apiUrl, cuotaRequest);
  }

  actualizarCuota(id: number, cuotaRequest: CuotaRequest): Observable<Cuota> {
    return this.http.put<Cuota>(`${this.apiUrl}/${id}`, cuotaRequest);
  }

  eliminarCuota(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  generarCuotasDelMes(request: GenerarCuotasRequest): Observable<Cuota[]> {
    return this.http.post<Cuota[]>(`${this.apiUrl}/generar`, request);
  }

  obtenerEstadoDeCuentaPorVivienda(viviendaId: number): Observable<EstadoCuentaViviendaResponse> {
  return this.http.get<EstadoCuentaViviendaResponse>(`${this.apiUrl}/estado-cuenta/vivienda/${viviendaId}`);
}
}
