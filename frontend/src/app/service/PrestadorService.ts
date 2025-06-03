// prestador.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class PrestadorService {
  private apiUrl = 'http://localhost:8080/api/prestadores';

  constructor(private http: HttpClient) {}

  listarTodos() {
    return this.http.get<any[]>(this.apiUrl);
  }
}
