// src/App.js
import React, { useState } from 'react';
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
  useLocation
} from 'react-router-dom';

import { AuthProvider } from './contexts/AuthContext';

import Header from './components/Header';
import Navigation from './components/common/Navigation';

import Login from './components/Login';
import Register from './components/Register'; // ← importamos o novo componente de cadastro

import BuscarServicos from './components/cliente/BuscarServico';
import Historico from './components/cliente/Historico';
import MinhasAvaliacoes from './components/cliente/MinhasAvaliacoes';
import Favoritos from './components/cliente/Favoritos';
import PerfilCliente from './components/cliente/PerfilCliente';
import PerfilPrestador from './components/cliente/PerfilPrestador';

import useFavorites from './hooks/useFavorites';

import theme from './theme';

import { ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';

/**
 * AppLayout verifica a rota atual. Se for "/login" ou "/register", renderiza só o children.
 * Caso contrário, renderiza Header + Navigation + children.
 */
function AppLayout({ currentUser, setCurrentUser, children }) {
  const location = useLocation();

  // Se a rota for "/login" OU "/register", exibimos apenas o componente de dentro (Login ou Register)
  if (location.pathname === '/login' || location.pathname === '/register') {
    return <>{children}</>;
  }

  // Em qualquer outra rota, renderiza Header + Navigation + conteúdo
  return (
    <>
      <Header currentUser={currentUser} setCurrentUser={setCurrentUser} />
      <Navigation userType={currentUser ? currentUser.type : null} />
      {children}
    </>
  );
}

const App = () => {
  // Inicialmente, mantemos um usuário "fake" para mostrar o menu;
  // depois do login/registro você pode atualizar currentUser via AuthContext ou setCurrentUser.
  const [currentUser, setCurrentUser] = useState({
    id: 1,
    type: 'cliente',
    name: 'João Silva'
  });

  const { favorites, toggleFavorite } = useFavorites();

  const [prestadores] = useState([
    // ... dados de prestadores (exemplo)
    { id: 1, name: 'Prestador A', service: 'Serviço A' },
    { id: 2, name: 'Prestador B', service: 'Serviço B' },
  ]);

  const contractService = (prestador) => {
    alert(`Serviço contratado com ${prestador.name}!`);
  };

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />

      <AuthProvider>
        <Router>
          <AppLayout currentUser={currentUser} setCurrentUser={setCurrentUser}>
            <Routes>
              {/* Rota de Login */}
              <Route path="/login" element={<Login />} />

              {/* Rota de Registro (cadastro) */}
              <Route path="/register" element={<Register />} />

              {currentUser && currentUser.type === 'cliente' ? (
                <>
                  {/* Redireciona "/" para "/buscar" */}
                  <Route path="/" element={<Navigate to="/buscar" replace />} />

                  <Route
                    path="/buscar"
                    element={
                      <BuscarServicos
                        prestadores={prestadores}
                        favorites={favorites}
                        toggleFavorite={toggleFavorite}
                        contractService={contractService}
                      />
                    }
                  />

                  <Route
                    path="/favoritos"
                    element={<Favoritos prestadores={prestadores} favorites={favorites} />}
                  />

                  <Route path="/historico" element={<Historico />} />
                  <Route path="/perfilCliente" element={<PerfilCliente />} />
                </>
              ) : (
                <>
                  {/* Rotas de prestador */}
                  <Route path="/perfilPrestador" element={<PerfilPrestador />} />
                  <Route path="/avaliacoes" element={<MinhasAvaliacoes />} />
                </>
              )}
            </Routes>
          </AppLayout>
        </Router>
      </AuthProvider>
    </ThemeProvider>
  );
};

export default App;
