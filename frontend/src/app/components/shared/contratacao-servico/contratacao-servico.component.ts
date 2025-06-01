import { Component } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatNativeDateModule } from '@angular/material/core';
import { FormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-contratacao-servico',
  standalone: true,
  imports: [MatFormFieldModule, MatDatepickerModule, 
    MatInputModule, MatNativeDateModule, FormsModule, 
    MatSelectModule, MatOptionModule, RouterModule],
  templateUrl: './contratacao-servico.component.html',
  styleUrl: './contratacao-servico.component.css'
})
export class ContratacaoServicoComponent {

  dataSelecionada: Date | null = null;
  prestadorSelecionado: string = '';
  servicoSelecionado: string = '';

  prestadores = ['João Silva', 'Maria Santos'];
  servicos = ['Eletricista', 'Encanador'];

  constructor(private router: Router) {}

  onSubmit(){
    this.router.navigate(['home']);
  }

}
