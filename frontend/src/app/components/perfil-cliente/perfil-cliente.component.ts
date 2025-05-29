import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-perfil-cliente',
  imports: [],
  templateUrl: './perfil-cliente.component.html',
  styleUrl: './perfil-cliente.component.css'
})
export class PerfilClienteComponent {

  constructor(private router: Router){}

  abrirHistorico() {
    this.router.navigate(['historico']);
    console.log('Abrir histórico acionado');
    // navegação ou dialog
  }
}
