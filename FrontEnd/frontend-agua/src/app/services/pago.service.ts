import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pago } from '../models/pago.model';
import { PagoRequest } from '../models/pago-request.model';

@Injectable({
  providedIn: 'root'
})
export class PagoService {

  private apiUrl = 'http://localhost:8080/pagos';

  constructor(private http: HttpClient) {}

  listarPagos(): Observable<Pago[]> {
    return this.http.get<Pago[]>(this.apiUrl);
  }

  guardarPago(pagoRequest: PagoRequest): Observable<Pago> {
    return this.http.post<Pago>(this.apiUrl, pagoRequest);
  }

  actualizarPago(id: number, pagoRequest: PagoRequest): Observable<Pago> {
    return this.http.put<Pago>(`${this.apiUrl}/${id}`, pagoRequest);
  }

  eliminarPago(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
