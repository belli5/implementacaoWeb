import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-perfil-prestador',
  standalone: true,
  imports: [RouterModule, MatFormFieldModule, MatInputModule, MatButtonModule, FormsModule],
  templateUrl: './perfil-prestador.component.html',
  styleUrl: './perfil-prestador.component.css'
})
export class PerfilPrestadorComponent {
  mostrarAvaliacoes = false;
  mostrarEdicaoPerfil = false;
  nome = 'João Silva';
  ocupacao = 'Eletricista';
  descricao = 'O Lorem Ipsum é um texto modelo da indústria tipográfica...';

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

  // função pra identificar later
  usuarioPrestador = true;

  toggleAvaliacoes() {
    this.mostrarAvaliacoes = !this.mostrarAvaliacoes;
    this.mostrarEdicaoPerfil = false;
  }

  toggleEditarPerfil() {
    this.mostrarEdicaoPerfil = !this.mostrarEdicaoPerfil;
    this.mostrarAvaliacoes = false;
  }

  addFavoritos(){
    this.router.navigate(['favoritos']);
  }

  contratarServico(){
    this.router.navigate(['contratacaoServico']);
  }

  salvar() {
    console.log('Perfil salvo:', this.nome, this.ocupacao, this.descricao);
    this.toggleEditarPerfil();
  }
}
