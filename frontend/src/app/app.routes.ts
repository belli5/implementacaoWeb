import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { FavoritosComponent } from './components/favoritos/favoritos.component';
import { PerfilPrestadorComponent } from './components/perfil-prestador/perfil-prestador.component';
import { HistoricoComponent } from './components/historico/historico.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'favoritos', component: FavoritosComponent },
    { path: 'perfilPrestador', component: PerfilPrestadorComponent },
    { path: 'historico', component: HistoricoComponent },
];
