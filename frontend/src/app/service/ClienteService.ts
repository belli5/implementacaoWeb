// src/app/services/cliente.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../enviroments/enviroment';

@Injectable({ providedIn: 'root' })
export class ClienteService {
  private baseApiUrl = environment.baseApiUrl;
  private apiUrl = `${this.baseApiUrl}/api/clientes`

  constructor(private http: HttpClient) {}

  cadastrar(cliente: any) {
    return this.http.post(this.apiUrl, cliente);
  }
}
