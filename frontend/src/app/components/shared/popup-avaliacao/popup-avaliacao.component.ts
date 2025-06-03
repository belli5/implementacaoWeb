import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-popup-avaliacao',
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatButtonModule, FormsModule],
  templateUrl: './popup-avaliacao.component.html',
  styleUrl: './popup-avaliacao.component.css'
})
export class PopupAvaliacaoComponent {

  avaliacao: number = 0;
  comentario: string = '';
  listPrestadores = [];

  constructor(
    public dialogRef: MatDialogRef<PopupAvaliacaoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { nome: string, prestadores: any },
  ) {
    this.listPrestadores = data.prestadores;
    console.log(this.listPrestadores)
  }

  selecionarEstrela(n: number) {
    this.avaliacao = n;
  }

  fechar() {
    this.dialogRef.close();
  }

  enviarAvaliacao(): void {
    console.log('Avaliação enviada:', this.avaliacao, this.comentario);
    this.dialogRef.close({
      nota: this.avaliacao,
      comentario: this.comentario
    });
  }
}

