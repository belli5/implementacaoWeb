import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

export default function Register() {
  const [nome, setNome] = useState('');
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [confirmSenha, setConfirmSenha] = useState('');
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

    if (senha !== confirmSenha) {
      setError('As senhas não conferem.');
      return;
    }

    try {
      const endereco = { rua, bairro, cidade, estado };
      await register({ nome, email, senha, telefone, endereco });
      navigate('/login');
    } catch (err) {
      console.error(err);
      setError('Não foi possível cadastrar. Verifique os dados.');
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
      <h2 style={{ textAlign: 'center' }}>Cadastro de Cliente</h2>
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: '1rem' }}>
          <label htmlFor="nome">Nome completo</label>
          <input
            type="text"
            id="nome"
            value={nome}
            onChange={(e) => setNome(e.target.value)}
            required
            style={{ width: '100%', padding: '0.5rem' }}
            placeholder="Seu nome"
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
          <label htmlFor="confirmSenha">Confirmar Senha</label>
          <input
            type="password"
            id="confirmSenha"
            value={confirmSenha}
            onChange={(e) => setConfirmSenha(e.target.value)}
            required
            style={{ width: '100%', padding: '0.5rem' }}
            placeholder="********"
          />
        </div>

        <div style={{ marginBottom: '1rem' }}>
          <label htmlFor="telefone">Telefone</label>
          <input
            type="text"
            id="telefone"
            value={telefone}
            onChange={(e) => setTelefone(e.target.value)}
            style={{ width: '100%', padding: '0.5rem' }}
            placeholder="(XX) XXXXX-XXXX"
          />
        </div>

        <fieldset style={{ marginBottom: '1rem', padding: '0.5rem' }}>
          <legend>Endereço</legend>

          <div style={{ marginBottom: '0.75rem' }}>
            <label htmlFor="rua">Rua</label>
            <input
              type="text"
              id="rua"
              value={rua}
              onChange={(e) => setRua(e.target.value)}
              style={{ width: '100%', padding: '0.5rem' }}
              placeholder="Rua das Flores, 123"
            />
          </div>

          <div style={{ marginBottom: '0.75rem' }}>
            <label htmlFor="bairro">Bairro</label>
            <input
              type="text"
              id="bairro"
              value={bairro}
              onChange={(e) => setBairro(e.target.value)}
              style={{ width: '100%', padding: '0.5rem' }}
              placeholder="Centro"
            />
          </div>

          <div style={{ marginBottom: '0.75rem' }}>
            <label htmlFor="cidade">Cidade</label>
            <input
              type="text"
              id="cidade"
              value={cidade}
              onChange={(e) => setCidade(e.target.value)}
              style={{ width: '100%', padding: '0.5rem' }}
              placeholder="Recife"
            />
          </div>

          <div style={{ marginBottom: '0.75rem' }}>
            <label htmlFor="estado">Estado</label>
            <input
              type="text"
              id="estado"
              value={estado}
              onChange={(e) => setEstado(e.target.value)}
              style={{ width: '100%', padding: '0.5rem' }}
              placeholder="PE"
            />
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
          Entrar
        </Link>
      </p>
    </div>
  );
}
