// src/components/Register.jsx
import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

export default function Register() {
  const [nome, setNome] = useState('');
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [telefone, setTelefone] = useState('');
  const [rua, setRua] = useState('');
  const [bairro, setBairro] = useState('');
  const [cidade, setCidade] = useState('');
  const [estado, setEstado] = useState('');
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const { register } = useAuth();

  async function handleSubmit(e) {
    e.preventDefault();
    setError(null);

    // Monta o objeto conforme o backend espera
    const endereco = { rua, bairro, cidade, estado };

    try {
      // Chama a função de registro do AuthContext
      await register({ nome, email, senha, telefone, endereco });
      // Se der certo, redireciona para a tela de login
      navigate('/login');
    } catch (err) {
      console.error(err);
      setError('Falha ao cadastrar. Verifique os dados e tente novamente.');
    }
  }

  return (
    <div
      style={{
        maxWidth: 500,
        margin: '2rem auto',
        padding: '1rem',
        border: '1px solid #ccc',
        borderRadius: 8,
      }}
    >
      <h2 style={{ textAlign: 'center' }}>Cadastre-se</h2>
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: '1rem' }}>
          <label htmlFor="nome">Nome</label>
          <input
            type="text"
            id="nome"
            value={nome}
            onChange={(e) => setNome(e.target.value)}
            required
            style={{ width: '100%', padding: '0.5rem' }}
            placeholder="Seu nome completo"
          />
        </div>

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

        <div style={{ marginBottom: '1rem' }}>
          <label htmlFor="telefone">Telefone</label>
          <input
            type="tel"
            id="telefone"
            value={telefone}
            onChange={(e) => setTelefone(e.target.value)}
            required
            style={{ width: '100%', padding: '0.5rem' }}
            placeholder="(XX) XXXXX-XXXX"
          />
        </div>

        <fieldset style={{ marginBottom: '1rem', border: '1px solid #aaa', padding: '0.75rem' }}>
          <legend>Endereço</legend>

          <div style={{ marginBottom: '0.75rem' }}>
            <label htmlFor="rua">Rua</label>
            <input
              type="text"
              id="rua"
              value={rua}
              onChange={(e) => setRua(e.target.value)}
              required
              style={{ width: '100%', padding: '0.5rem' }}
              placeholder="Rua, número e complemento"
            />
          </div>

          <div style={{ marginBottom: '0.75rem' }}>
            <label htmlFor="bairro">Bairro</label>
            <input
              type="text"
              id="bairro"
              value={bairro}
              onChange={(e) => setBairro(e.target.value)}
              required
              style={{ width: '100%', padding: '0.5rem' }}
              placeholder="Bairro"
            />
          </div>

          <div style={{ display: 'flex', gap: '1rem', marginBottom: '0.75rem' }}>
            <div style={{ flex: 1 }}>
              <label htmlFor="cidade">Cidade</label>
              <input
                type="text"
                id="cidade"
                value={cidade}
                onChange={(e) => setCidade(e.target.value)}
                required
                style={{ width: '100%', padding: '0.5rem' }}
                placeholder="Cidade"
              />
            </div>
            <div style={{ width: '80px' }}>
              <label htmlFor="estado">Estado</label>
              <input
                type="text"
                id="estado"
                value={estado}
                onChange={(e) => setEstado(e.target.value)}
                required
                style={{ width: '100%', padding: '0.5rem' }}
                placeholder="UF"
              />
            </div>
          </div>
        </fieldset>

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
            background: '#28a745',
            color: '#fff',
            border: 'none',
            borderRadius: 4,
          }}
        >
          Cadastrar
        </button>
      </form>

      <p style={{ marginTop: '1rem', textAlign: 'center' }}>
        Já tem conta?{' '}
        <Link to="/login" style={{ color: '#007bff', textDecoration: 'none' }}>
          Faça login
        </Link>
      </p>
    </div>
  );
}
