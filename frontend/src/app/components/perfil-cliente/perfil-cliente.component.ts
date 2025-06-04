import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PerfilClienteService } from '../../service/perfil-cliente.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-perfil-cliente',
  imports: [MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    FormsModule],
  templateUrl: './perfil-cliente.component.html',
  styleUrl: './perfil-cliente.component.css'
})
export class PerfilClienteComponent {

  cliente: any = {};
  mostrarEdicao = false;

  constructor(private router: Router, private clienteService: PerfilClienteService){}

  ngOnInit(): void {
    const clienteId = Number(localStorage.getItem('clienteId'));
    this.clienteService.buscarPorId(clienteId).subscribe({
      next: (data) => this.cliente = data,
      error: (err) => console.error('Erro ao carregar cliente', err)
    });
  }

  abrirHistorico() {
    this.router.navigate(['historico']);
    console.log('Abrir histórico acionado');
    // navegação ou dialog
  }

  editarPerfil() {
    this.mostrarEdicao = true;
  }

  salvarEdicao() {
    const clienteId = this.cliente.id;
    this.clienteService.atualizar(1, this.cliente).subscribe({
      next: () => {
        alert('Perfil atualizado com sucesso!');
        this.mostrarEdicao = false;
      },
      error: (err) => console.error('Erro ao atualizar perfil', err)
    });
  }

  cancelarEdicao() {
    this.mostrarEdicao = false;
  }
}

