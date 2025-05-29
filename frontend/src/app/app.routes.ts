import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { FavoritosComponent } from './components/favoritos/favoritos.component';
import { PerfilPrestadorComponent } from './components/perfil-prestador/perfil-prestador.component';
import { HistoricoComponent } from './components/historico/historico.component';
import { ContratacaoServicoComponent } from './components/shared/contratacao-servico/contratacao-servico.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'favoritos', component: FavoritosComponent },
    { path: 'perfilPrestador', component: PerfilPrestadorComponent },
    { path: 'historico', component: HistoricoComponent },
    { path: 'contratacaoServico', component: ContratacaoServicoComponent },
];
