import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-perfil-prestador',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './perfil-prestador.component.html',
  styleUrl: './perfil-prestador.component.css'
})
export class PerfilPrestadorComponent {
  mostrarAvaliacoes = false;

  listaAvaliacoes = [
    {
      cliente: 'Maria Souza',
      nota: 5,
      comentario: 'Excelente profissional!'
    },
    {
      cliente: 'Carlos Lima',
      nota: 4,
      comentario: 'Bom serviço, mas chegou com atraso.'
    }
  ];

  constructor(private router: Router) {}

  toggleAvaliacoes() {
    this.mostrarAvaliacoes = !this.mostrarAvaliacoes;
  }

  addFavoritos(){
    this.router.navigate(['favoritos']);
  }

  contratarServico(){
    this.router.navigate(['contratacaoServico']);
  }
}
