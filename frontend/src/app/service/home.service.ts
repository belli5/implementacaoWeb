import { Injectable } from '@angular/core';
import { environment } from '../../enviroments/enviroment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  private baseApiUrl = environment.baseApiUrl || 'http://localhost:8080';
  private ofereceUrl = `${this.baseApiUrl}/oferece`;

  constructor(private http: HttpClient) { }

  getOferecimentos(): Observable<any[]> {
    return this.http.get<any[]>(this.ofereceUrl);
  }
}
