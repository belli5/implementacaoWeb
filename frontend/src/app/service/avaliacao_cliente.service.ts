import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AvaliacaoClienteService {
  // avaliação que o cliente faz sobre o serviço e o prestador
  private baseApiUrl = 'http://localhost:8080';
  private apiUrl = `${this.baseApiUrl}/avaliacaoSobrePrestador`

  constructor(private http: HttpClient) {}

  criarAvaliacao(clienteId: number, prestadorId: number,
    comentario: string,
    nota: number,
  ): Observable<any> {
    const body = {
      cliente: { id: clienteId },
      prestador: { id: prestadorId },
      comentario,
      nota
    };
    return this.http.post(`${this.apiUrl}/novaAvaliacaoSobrePrestador`, body);
  }

  listarPorPrestador(prestadorId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseApiUrl}/avaliacoes_por_prestador/${prestadorId}`);
  }
}
