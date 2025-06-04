import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

export default function Home() {
  const { user } = useAuth();

  return (
    <div style={{ padding: '2rem' }}>
      <h1>Bem-vindo ao ContrateJá!</h1>
      {user ? (
        -       <p>
          -         Olá, <strong>{user.nome}</strong>! <Link to="/dashboard">Dashboard</Link>
          -       </p>
        +       <div>
          +         <p>
          +           Olá, <strong>{user.nome}</strong>!
          +         </p>
          +         <p style={{ marginTop: '1rem' }}>
          +           <Link to="/dashboard" style={{ marginRight: '1rem' }}>
          +             Dashboard
          +           </Link>
          +           {/* Botão / link para editar perfil */}
          +           <Link to="/editar-perfil" style={{ color: '#007bff' }}>
          +             Editar Perfil
          +           </Link>
          +         </p>
          +       </div>
      ) : (
        <p>
          <Link to="/login">Entrar</Link> ou <Link to="/register">Cadastre-se</Link>
        </p>
      )}
    </div>
  );
}
