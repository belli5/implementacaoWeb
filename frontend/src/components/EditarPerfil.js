// src/components/EditarPerfil.jsx
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import api from '../services/api';

export default function EditarPerfil() {
  const { logout } = useAuth();
  const navigate = useNavigate();

  // estados para os campos
  const [clienteId, setClienteId] = useState(null);
  const [nome, setNome] = useState('');
  const [email, setEmail] = useState('');
  const [telefone, setTelefone] = useState('');
  const [rua, setRua] = useState('');
  const [bairro, setBairro] = useState('');
  const [cidade, setCidade] = useState('');
  const [estado, setEstado] = useState('');
  const [error, setError] = useState(null);

  useEffect(() => {
    async function carregarDados() {
      try {
        // 1) Chama GET /api/clientes/me para descobrir o ID e alguns dados iniciais
        const respMe = await api.get('/api/clientes/me');
        const clienteMe = respMe.data;
        // clienteMe = { id, nome, email, telefone, endereco: {…} }

        if (!clienteMe || !clienteMe.id) {
          // se não vier ID, algo deu errado. Desloga e retorna ao login
          logout();
          navigate('/login');
          return;
        }

        // guarda o ID para usar no PUT
        setClienteId(clienteMe.id);

        // Agora, opcionalmente, você pode chamar GET /api/clientes/{id}
        // para garantir que tem todos os campos mais atualizados do banco:
        const respById = await api.get(`/api/clientes/${clienteMe.id}`);
        const data = respById.data;

        setNome(data.nome || '');
        setEmail(data.email || '');
        setTelefone(data.telefone || '');
        setRua((data.endereco && data.endereco.rua) || '');
        setBairro((data.endereco && data.endereco.bairro) || '');
        setCidade((data.endereco && data.endereco.cidade) || '');
        setEstado((data.endereco && data.endereco.estado) || '');
      } catch (err) {
        console.error('[EditarPerfil] falha ao carregar dados:', err);
        logout();
        navigate('/login');
      }
    }

    carregarDados();
  }, [logout, navigate]);

  async function handleSubmit(e) {
    e.preventDefault();
    setError(null);

    if (!clienteId) {
      setError('ID do cliente não encontrado.');
      return;
    }

    const payload = {
      nome: nome || '',
      email: email || '',
      telefone: telefone || '',
      endereco: {
        rua: rua || '',
        bairro: bairro || '',
        cidade: cidade || '',
        estado: estado || '',
      },
    };

    try {
      // 2) Chama PUT /api/clientes/{id} usando o ID que já guardamos
      await api.put(`/api/clientes/${clienteId}`, payload);
      navigate('/');
    } catch (err) {
      console.error('[EditarPerfil] erro ao atualizar:', err);
      setError('Falha ao atualizar perfil. Verifique os dados e tente novamente.');
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
      <h2 style={{ textAlign: 'center' }}>Editar Perfil</h2>
      <form onSubmit={handleSubmit}>
        {/* Nome */}
        <div style={{ marginBottom: '1rem' }}>
          <label htmlFor="nome">Nome</label>
          <input
            type="text"
            id="nome"
            value={nome}
            onChange={(e) => setNome(e.target.value)}
            required
            style={{ width: '100%', padding: '0.5rem' }}
          />
        </div>

        {/* Email */}
        <div style={{ marginBottom: '1rem' }}>
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            style={{ width: '100%', padding: '0.5rem' }}
          />
        </div>

        {/* Telefone */}
        <div style={{ marginBottom: '1rem' }}>
          <label htmlFor="telefone">Telefone</label>
          <input
            type="tel"
            id="telefone"
            value={telefone}
            onChange={(e) => setTelefone(e.target.value)}
            style={{ width: '100%', padding: '0.5rem' }}
          />
        </div>

        {/* Endereço */}
        <fieldset
          style={{
            marginBottom: '1rem',
            border: '1px solid #aaa',
            padding: '0.75rem',
          }}
        >
          <legend>Endereço</legend>

          <div style={{ marginBottom: '0.75rem' }}>
            <label htmlFor="rua">Rua</label>
            <input
              type="text"
              id="rua"
              value={rua}
              onChange={(e) => setRua(e.target.value)}
              style={{ width: '100%', padding: '0.5rem' }}
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
            />
          </div>

          <div
            style={{
              display: 'flex',
              gap: '1rem',
              marginBottom: '0.75rem',
            }}
          >
            <div style={{ flex: 1 }}>
              <label htmlFor="cidade">Cidade</label>
              <input
                type="text"
                id="cidade"
                value={cidade}
                onChange={(e) => setCidade(e.target.value)}
                style={{ width: '100%', padding: '0.5rem' }}
              />
            </div>
            <div style={{ width: '80px' }}>
              <label htmlFor="estado">Estado</label>
              <input
                type="text"
                id="estado"
                value={estado}
                onChange={(e) => setEstado(e.target.value)}
                style={{ width: '100%', padding: '0.5rem' }}
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
            background: '#007bff',
            color: '#fff',
            border: 'none',
            borderRadius: 4,
          }}
        >
          Salvar Alterações
        </button>
      </form>
    </div>
  );
}
