import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-popup-avaliacao',
  standalone: true,
  imports: [MatFormFieldModule],
  templateUrl: './popup-avaliacao.component.html',
  styleUrl: './popup-avaliacao.component.css'
})
export class PopupAvaliacaoComponent {

  avaliacao: number = 0;
  comentario: string = '';

  constructor(
    public dialogRef: MatDialogRef<PopupAvaliacaoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { nome: string }
  ) {}

  selecionarEstrela(n: number) {
    this.avaliacao = n;
  }

  fechar(){
    this.dialogRef.close();
  }

  enviarAvaliacao(): void {
  this.dialogRef.close({
    nota: this.avaliacao,
    comentario: this.comentario
  });
}

}
