// src/contexts/AuthContext.js
import React, { createContext, useContext, useEffect, useState } from 'react';
import api from '../services/api';

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [basicToken, setBasicToken] = useState(() => localStorage.getItem('basicToken'));
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function loadUser() {
      if (!basicToken) {
        setUser(null);
        setLoading(false);
        return;
      }

      try {
        const resp = await api.get('/api/clientes/me');
        const texto = resp.data.mensagem || '';
        const nome = texto.replace('Usuário autenticado: ', '');
        setUser({ nome });
      } catch (err) {
        console.error('[AuthContext] loadUser falhou:', err);
        localStorage.removeItem('basicToken');
        setBasicToken(null);
        setUser(null);
      } finally {
        setLoading(false);
      }
    }

    loadUser();
  }, [basicToken]);

  async function login({ email, senha }) {
    const raw = `${email}:${senha}`;
    const base64 = btoa(raw);
    const header = `Basic ${base64}`;

    try {
      localStorage.setItem('basicToken', header);
      setBasicToken(header);

      const resp = await api.get('/api/clientes/me');
      const texto = resp.data.mensagem || '';
      const nome = texto.replace('Usuário autenticado: ', '');
      setUser({ nome });
    } catch (err) {
      console.error('[AuthContext] login falhou:', err);
      localStorage.removeItem('basicToken');
      setBasicToken(null);
      setUser(null);
      throw new Error('Credenciais inválidas');
    }
  }

  async function register({ nome, email, senha, telefone, endereco }) {
    // Chamando POST /api/clientes conforme o backend espera
    await api.post('/api/clientes', { nome, email, senha, telefone, endereco });
  }

  function logout() {
    localStorage.removeItem('basicToken');
    setBasicToken(null);
    setUser(null);
  }

  return (
    <AuthContext.Provider value={{ user, loading, login, register, logout }}>
      {loading ? <div>Carregando...</div> : children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}
