import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { HistoricoService } from '../../service/historico.service';

@Component({
  selector: 'app-historico',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './historico.component.html',
  styleUrl: './historico.component.css'
})
export class HistoricoComponent {

  servicos: any[] = [];

  constructor(private router: Router, private historicoService: HistoricoService) { }

  ngOnInit(): void {
    this.carregarHistorico();
  }

  carregarHistorico(){
    this.historicoService.findAll().subscribe((data) => {
      if(data){
        this.servicos = data;
        console.log(this.servicos);
      }
    });
  }

  contratarServico(){
    this.router.navigate(['contratacaoServico']);
  }

}
