import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FavoritoService {
    private baseApiUrl = 'http://localhost:8080';
    private apiUrl = `${this.baseApiUrl}/api/favoritos`;

    constructor(private http: HttpClient) {}

    // Adiciona um favorito
    adicionarFavorito(clienteId: number, prestadorId: number): Observable<any> {
        const body = {
            cliente: { id: clienteId },
            prestador: { id: prestadorId }
        };
        return this.http.post(`${this.apiUrl}`, body);
    }

    // Lista os favoritos de um cliente
    listarFavoritos(clienteId: number): Observable<any[]> {
        return this.http.get<any[]>(`${this.apiUrl}/cliente/${clienteId}`);
    }
}
