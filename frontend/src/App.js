import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Header from './components/Header';
import Navigation from './components/common/Navigation';
import BuscarServicos from './components/cliente/BuscarServico';
import Historico from './components/cliente/Historico';
import MinhasAvaliacoes from './components/cliente/MinhasAvaliacoes';
import useFavorites from './hooks/useFavorites';
import Favoritos from './components/cliente/Favoritos';
import PerfilCliente from './components/cliente/PerfilCliente';
import PerfilPrestador from './components/cliente/PerfilPrestador';
import theme from './theme';

import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline'; // aplica estilos base do MUI


const App = () => {
  const [currentUser, setCurrentUser] = useState({ 
    id: 1, 
    type: 'cliente', // ou 'prestador'
    name: 'João Silva' 
  });

  const { favorites, toggleFavorite } = useFavorites();

  const [prestadores] = useState([
    // ... dados dos prestadores
  ]);

  const contractService = (prestador) => {
    alert(`Serviço contratado com ${prestador.name}!`);
  };

  return (
    <ThemeProvider theme={theme}>
      <Router>
        <div className="min-h-screen bg-gray-50">
          <Header currentUser={currentUser} setCurrentUser={setCurrentUser} />
          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
            <Navigation userType={currentUser.type} />
            <main>
              <Routes>
                {currentUser.type === 'cliente' ? (
                  <>
                    <Route path="/" element={<Navigate to="/buscar" />} />
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
                      element={
                        <Favoritos 
                        prestadores={prestadores} 
                        favorites={favorites} 
                        />
                      } 
                      />
                    <Route path="/historico" element={<Historico />} />
                    <Route path="/perfilCliente" element={<PerfilCliente />} />
                  </>
                ) : (
                  <>
                    {/* <Route path="/" element={<Navigate to="/perfil" />} /> */}
                    <Route path="/perfilPrestador" element={<PerfilPrestador />} />
                    <Route path="/avaliacoes" element={<MinhasAvaliacoes />} />
                  </>
                )}
              </Routes>
            </main>
          </div>
        </div>
      </Router>
    </ThemeProvider>
  );
};

export default App;
