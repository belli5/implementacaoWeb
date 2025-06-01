import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-historico',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './historico.component.html',
  styleUrl: './historico.component.css'
})
export class HistoricoComponent {

  constructor(private router: Router){}

  contratarServico(){
    this.router.navigate(['contratacaoServico']);
  }

}
