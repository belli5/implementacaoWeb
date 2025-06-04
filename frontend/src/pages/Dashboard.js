import React from 'react';
import { useAuth } from '../contexts/AuthContext';
import { useNavigate } from 'react-router-dom';

export default function Dashboard() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  function handleLogout() {
    logout();
    navigate('/');
  }

  return (
    <div style={{ padding: '2rem' }}>
      <h1>Dashboard</h1>
      <p>
        Bem-vindo, <strong>{user.nome}</strong>!
      </p>
      <button
        onClick={handleLogout}
        style={{
          padding: '0.5rem 1rem',
          background: '#dc3545',
          color: '#fff',
          border: 'none',
          borderRadius: 4,
        }}
      >
        Sair
      </button>
    </div>
  );
}
