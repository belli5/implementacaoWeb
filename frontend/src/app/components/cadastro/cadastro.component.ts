import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cadastro',
  imports: [],
  templateUrl: './cadastro.component.html',
  styleUrl: './cadastro.component.css'
})
export class CadastroComponent {
  nome = '';
  sobrenome = '';
  email = '';
  endereco = '';
  senha = '';

  constructor(private router: Router){}

  onSubmit(){
    this.router.navigate(['home'])
  }

}
