import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router, RouterModule } from '@angular/router';
import { PopupAvaliacaoComponent } from '../shared/popup-avaliacao/popup-avaliacao.component';
import { HomeService } from '../../service/home.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  categoriaSelecionada: string | null = null;
  prestadoresComServicos: any[] = [];

  categorias = [
    { imagem: 'cabelo', servico: 'Cabelos' },
    { imagem: 'uva', servico: 'Frutas' }
    // Adicione mais conforme sua base de dados
  ];

  constructor(
    private router: Router,
    private dialog: MatDialog,
    private homeService: HomeService
  ) {}

  ngOnInit(): void {
    this.homeService.getOferecimentos().subscribe(data => {
      this.prestadoresComServicos = data;
    });
  }

  get profissionaisFiltrados() {
    if (!this.categoriaSelecionada) return this.prestadoresComServicos;
    return this.prestadoresComServicos.filter(o =>
      o.servico.categoria.toLowerCase() === this.categoriaSelecionada?.toLowerCase()
    );
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
      data: { nome: 'João Silva' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log(`Avaliação: ${result.nota} estrelas`);
        console.log(`Comentário: ${result.comentario}`);
      }
    });
  }
}
