import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { AvaliacaoClienteService } from '../../service/avaliacao_cliente.service';

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

  avaliacoes: Avaliacao[] = [];
  usuarioPrestador = false;

  constructor(private router: Router, private avaliacaoService: AvaliacaoClienteService,) {}

  ngOnInit(){
    this.todasAvaliacoes();
  }

  // função pra identificar later

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

  cancelar() {
    this.toggleEditarPerfil();
  }

  todasAvaliacoes(){
    this.avaliacaoService.listarPorPrestador(1).subscribe((avaliacao: any) => {
      this.avaliacoes = avaliacao;
      console.log(this.avaliacoes);
    })
  }
}

interface Avaliacao {
  nota: number;
  comentario: string;
  cliente: {
    id: number;
    nome: string;
    email?: string;
    telefone?: string;
    senha?: string;
  };
  prestador?: any;
}
