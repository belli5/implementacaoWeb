import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';

@Component({
  standalone: true,
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  imports: [FormsModule, HttpClientModule]
})
export class LoginComponent {
  email = '';
  senha = '';
  erroLogin = false;

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit() {
    const base64 = btoa(`${this.email}:${this.senha}`);
    const headers = new HttpHeaders({
      'Authorization': `Basic ${base64}`
    });

    console.log('Tentando logar com', this.email, this.senha);

    this.http.get('http://localhost:8080/api/clientes/me', { headers })
      .subscribe({
        next: (res) => {
          console.log('Login bem-sucedido!', res);
          this.router.navigate(['home']);
        },
        error: (err) => {
          console.error('Erro no login', err);
          this.erroLogin = true;
        }
      });
  }

}
