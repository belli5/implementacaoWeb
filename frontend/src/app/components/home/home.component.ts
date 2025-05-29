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

  constructor(private router: Router, private dialog: MatDialog) {}

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
