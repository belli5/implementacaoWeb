import { Injectable } from '@angular/core';
import { environment } from '../../enviroments/enviroment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  private baseApiUrl = 'http://localhost:8080';
  private apiUrl = `${this.baseApiUrl}/api/prestador`

  constructor(private http: HttpClient) { }

  getPrestadores(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }
}
