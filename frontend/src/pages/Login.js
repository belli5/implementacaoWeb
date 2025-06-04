import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

export default function Login() {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const { login } = useAuth();

  async function handleSubmit(e) {
    e.preventDefault();
    setError(null);

    try {
      await login({ email, senha });
      navigate('/dashboard');
    } catch (err) {
      console.error(err);
      setError('Email ou senha inválidos.');
    }
  }

  return (
    <div
      style={{
        maxWidth: 400,
        margin: '2rem auto',
        padding: '1rem',
        border: '1px solid #ccc',
        borderRadius: 8,
      }}
    >
      <h2 style={{ textAlign: 'center' }}>Login</h2>
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: '1rem' }}>
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            style={{ width: '100%', padding: '0.5rem' }}
            placeholder="seuemail@exemplo.com"
          />
        </div>

        <div style={{ marginBottom: '1rem' }}>
          <label htmlFor="senha">Senha</label>
          <input
            type="password"
            id="senha"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
            required
            style={{ width: '100%', padding: '0.5rem' }}
            placeholder="********"
          />
        </div>

        {error && (
          <div
            style={{
              marginBottom: '1rem',
              color: 'red',
              fontSize: '0.9rem',
            }}
          >
            {error}
          </div>
        )}

        <button
          type="submit"
          style={{
            width: '100%',
            padding: '0.75rem',
            background: '#007bff',
            color: '#fff',
            border: 'none',
            borderRadius: 4,
          }}
        >
          Entrar
        </button>
      </form>

      <p style={{ marginTop: '1rem', textAlign: 'center' }}>
        Ainda não tem conta?{' '}
        <Link to="/register" style={{ color: '#007bff', textDecoration: 'none' }}>
          Cadastre-se
        </Link>
      </p>
    </div>
  );
}
