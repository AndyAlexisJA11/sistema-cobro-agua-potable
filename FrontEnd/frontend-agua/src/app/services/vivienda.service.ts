import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Vivienda } from "../models/vivienda.model";
import { ViviendaRequest } from "../models/vivienda-request.model";



@Injectable({
  providedIn: 'root'
})
export class ViviendaService {
  private apiUrl = 'http://localhost:8080/viviendas';

  constructor(private http: HttpClient){}

  listarViviendas(): Observable<Vivienda[]>{
    return this.http.get<Vivienda[]>(this.apiUrl)
  }

  guardarVivienda(viviendaRequest: ViviendaRequest): Observable<Vivienda> {
    return this.http.post<Vivienda>(this.apiUrl, viviendaRequest);
  }

  actualizarVivienda(id: number, viviendaRequest: ViviendaRequest): Observable<Vivienda>{
    return this.http.put<Vivienda>(`${this.apiUrl}/${id}`, viviendaRequest);
    }

    eliminarVivienda(id: number): Observable<void>{
      return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}
