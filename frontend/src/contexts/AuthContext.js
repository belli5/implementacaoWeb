// src/contexts/AuthContext.js (ou .jsx, conforme esteja usando)
import React, { createContext, useContext, useEffect, useState } from 'react';
import api from '../services/api';

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [basicToken, setBasicToken] = useState(localStorage.getItem('basicToken'));
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function loadUser() {
      console.log('[AuthContext] loadUser: basicToken=', basicToken);
      if (!basicToken) {
        setUser(null);
        setLoading(false);
        return;
      }
      try {
        const resp = await api.get('/api/clientes/me');
        console.log('[AuthContext] resposta /me:', resp.data);
        const texto = resp.data.mensagem || '';
        const prefixo = 'Usuário autenticado: ';
        const nome = texto.startsWith(prefixo) ? texto.slice(prefixo.length) : texto;
        setUser({ nome });
      } catch (err) {
        console.error('[AuthContext] falha ao validar Basic Auth:', err);
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
    console.log('[AuthContext] login:', email, senha);
    const raw = `${email}:${senha}`;
    const base64 = btoa(raw);
    const header = `Basic ${base64}`;
    console.log('[AuthContext] header gerado:', header);

    try {
      localStorage.setItem('basicToken', header);
      setBasicToken(header);
      const resp = await api.get('/api/clientes/me');
      console.log('[AuthContext] sucesso /me:', resp.data);
      const texto = resp.data.mensagem || '';
      const prefixo = 'Usuário autenticado: ';
      const nome = texto.startsWith(prefixo) ? texto.slice(prefixo.length) : texto;
      setUser({ nome });
    } catch (err) {
      console.error('[AuthContext] erro no /me:', err);
      localStorage.removeItem('basicToken');
      setBasicToken(null);
      setUser(null);
      throw new Error('Credenciais inválidas');
    }
  }

  async function register({ nome, email, senha, telefone, endereco }) {
    console.log('[AuthContext] register:', { nome, email });
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
