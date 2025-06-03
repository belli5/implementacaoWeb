import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HistoricoService {
  
  private baseApiUrl = 'http://localhost:8080';
  private apiUrl = `${this.baseApiUrl}/servicos`;

  constructor(private http: HttpClient) { }

  findAll(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }
}
