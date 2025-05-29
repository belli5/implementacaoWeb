import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { FavoritosComponent } from './components/favoritos/favoritos.component';
import { PerfilPrestadorComponent } from './components/perfil-prestador/perfil-prestador.component';
import { HistoricoComponent } from './components/historico/historico.component';
import { ContratacaoServicoComponent } from './components/shared/contratacao-servico/contratacao-servico.component';
import { LoginComponent } from './components/login/login.component';
import { CadastroComponent } from './components/cadastro/cadastro.component';
import { PerfilClienteComponent } from './components/perfil-cliente/perfil-cliente.component';

export const routes: Routes = [
    { path: '', component: LoginComponent },
    { path: 'home', component: HomeComponent },
    { path: 'cadastro', component: CadastroComponent },
    { path: 'favoritos', component: FavoritosComponent },
    { path: 'perfilPrestador', component: PerfilPrestadorComponent },
    { path: 'perfilCliente', component: PerfilClienteComponent },
    { path: 'historico', component: HistoricoComponent },
    { path: 'contratacaoServico', component: ContratacaoServicoComponent },
];
