import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ClienteService } from '../../service/ClienteService';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-cadastro',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.css']
})
export class CadastroComponent {
  nome = '';
  sobrenome = '';
  email = '';
  senha = '';
  telefone = '';
  endereco = '';
  bairro = '';
  cidade = '';
  estado = '';

  constructor(
    private clienteService: ClienteService,
    private router: Router
  ) {}

  onSubmit() {
    const cliente = {
      nome: this.nome + ' ' + this.sobrenome,
      email: this.email,
      senha: this.senha,
      telefone: this.telefone,
      endereco: {
        rua: this.endereco,
        bairro: this.bairro,
        cidade: this.cidade,
        estado: this.estado
      }
    };

    this.clienteService.cadastrar(cliente).subscribe({
      next: () => this.router.navigate(['/home']),
      error: err => console.error('Erro ao cadastrar', err)
    });
  }
}
