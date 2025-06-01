import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router, RouterModule } from '@angular/router';
import { PopupAvaliacaoComponent } from '../shared/popup-avaliacao/popup-avaliacao.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  categoriaSelecionada: string | null = null;

  constructor(private router: Router, private dialog: MatDialog) {}

  categorias = [
    { imagem: 'marceneiro', servico: 'Marceneiro' },
    { imagem: 'eletricista', servico: 'Eletricista' },
    { imagem: 'fazTudo', servico: 'Faz Tudo' },
    { imagem: 'encanador', servico: 'Encanador' },
    { imagem: 'pedreiro', servico: 'Pedreiro' },
  ];

  profissionais = [
    { nome: 'João Silva', profissao: 'Eletricista', avatar: 'J', servico: 'eletricista' },
    { nome: 'Maria Santos', profissao: 'Encanador', avatar: 'M', servico: 'encanador' },
    { nome: 'Carlos Pinto', profissao: 'Faz Tudo', avatar: 'C', servico: 'fazTudo' },
    { nome: 'Lúcia Rocha', profissao: 'Encanador', avatar: 'L',  servico: 'encanador' },
    { nome: 'Alberto Junior', profissao: 'Pedreiro', avatar: 'A',  servico: 'pedreiro' },
  ];

  get profissionaisFiltrados() {
    if (!this.categoriaSelecionada) return this.profissionais;
    return this.profissionais.filter(p => p.servico === this.categoriaSelecionada);
  }

  selecionarCategoria(categoria: string) {
    this.categoriaSelecionada = categoria;
  }

  limparFiltro() {
    this.categoriaSelecionada = null;
  }

  verPerfil() {
    this.router.navigate(['perfilPrestador']);
  }

  finalizarServico() {
    const dialogRef = this.dialog.open(PopupAvaliacaoComponent, {
      width: '500px',
      data: { nome: 'João Silva' } // ou o nome do prestador dinâmico
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log(`Avaliação: ${result.nota} estrelas`);
        console.log(`Comentário: ${result.comentario}`);
        // Enviar para backend
      }
    });
  }

}
